package org.bip.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.api.Allocator;
import org.bip.api.PortType;
import org.bip.api.ResourceProvider;
import org.bip.exceptions.BIPException;
import org.bip.resources.grammar.constraintLexer;
import org.bip.resources.grammar.constraintParser;
import org.bip.resources.grammar.dNetLexer;
import org.bip.resources.grammar.dNetParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

@Ports({ @Port(name = "request", type = PortType.enforceable), @Port(name = "release", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.resources.AllocatorImpl")
public class AllocatorImpl implements ContextProvider, Allocator {

	private Logger logger = LoggerFactory.getLogger(AllocatorImpl.class);

	private DNet dnet;
	private Context context;
	private Solver solver;
	// TODO later change to hashmap for resources of the same type?
	// The list of all available resources
	private ArrayList<ResourceProvider> resources;
	// a map: resource name <-> resource provider
	private HashMap<String, ResourceProvider> nameToResource;
	// a map: dnet place corresponding to a resource <-> resource provider
	private HashMap<Place, ResourceProvider> placeToResource;
	// TODO a priori resource names and place names should be the same. what if not? CHECK THINK
	private HashMap<String, ResourceProvider> placeNameToResource;

	// a map: dnet place <-> list of tokens contained in that place (=list of transition that have put tokens in there)
	public HashMap<Place, ArrayList<Transition>> placeTokens;
	// a map: dnet place <-> list of variables contained in that place (should be equal in size to the list of tokens)
	public HashMap<Place, ArrayList<IntExpr>> placeVariables;
	// a map: resource name <-> resource constraint (as provided by the resource and then parsed)
	private HashMap<String, ConstraintNode> resourceToCost;
	// a map: resource name <-> value produced by the solver (is used to find out how much we should release upon spontaneous release event)
	// TODO it won't work this way if we have several components asking for the same resource - even if it is done in several times. or will I erase it? CHECK
	private HashMap<String, Expr> resourceNameToGivenValue;
	// a map: request string <-> model provided by the solver. is used in order not to solve the thing twice during the guard evaluation and the actual
	// allocation
	private HashMap<String, Model> requestToModel;

	/**************** Constructors *****************/

	public AllocatorImpl() {
		placeTokens = new HashMap<Place, ArrayList<Transition>>();
		placeVariables = new HashMap<Place, ArrayList<IntExpr>>();
		resourceToCost = new HashMap<String, ConstraintNode>();
		resources = new ArrayList<ResourceProvider>();
		nameToResource = new HashMap<String, ResourceProvider>();
		placeToResource = new HashMap<Place, ResourceProvider>();
		placeNameToResource = new HashMap<String, ResourceProvider>();
		resourceNameToGivenValue = new HashMap<String, Expr>();
		requestToModel = new HashMap<String, Model>();
	}

	// if an allocator received a dnet and no context, it creates a context and parses the dnet
	public AllocatorImpl(String dNetPath) throws IOException, RecognitionException, DNetException {
		this();

		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		setContext(ctx);
		parseAndInitializeDNet(dNetPath, ctx);
	}

	public AllocatorImpl(Context ctx, String dNetPath) throws IOException, RecognitionException, DNetException {
		this();
		setContext(ctx);
		parseAndInitializeDNet(dNetPath, ctx);
	}

	public AllocatorImpl(Context ctx, DNet net) {
		this();
		setContext(ctx);
		this.dnet = net;
		initializeDNet(ctx);
	}

	/******************* End of Constructors *****************/

	/**************** Initializing functions *****************/

	void setContext(Context ctx) {
		this.context = ctx;
		solver = context.mkSolver();
	}

	private void parseAndInitializeDNet(String dNetPath, Context ctx) throws FileNotFoundException, IOException, DNetException {
		FileInputStream stream = new FileInputStream(dNetPath);
		dNetLexer lexer = new dNetLexer(new ANTLRInputStream(stream));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		dNetParser parser = new dNetParser(tokens);
		parser.net();
		this.dnet = parser.net;
		initializeDNet(ctx);
	}

	// at the initialization phase of the dnet:
	// the context of the dnet is specified,
	// the variables and tokens are cleared and initialized with empty lists
	// in case of reinitialization (list of resources is not empty), the resource cost is stored in a local map
	// TODO the cost must be resend by resource, not asked by allocator
	// however, in this case it might be ok since it is the allocator that reinitialises itself
	private void initializeDNet(Context ctx) {
		this.dnet.setContext(ctx);
		this.dnet.reInit();
		placeVariables.clear();
		placeTokens.clear();
		for (Place place : dnet.places()) {
			placeTokens.put(place, new ArrayList<Transition>());
			placeVariables.put(place, new ArrayList<IntExpr>());
		}
		if (!resources.isEmpty()) {
			resourceToCost.clear();
			for (ResourceProvider resource : resources) {
				specifyCost(resource.name(), resource.cost());
			}
		}
	}
	
	private void specifyCost(String resource, String costString) {
		ConstraintNode cost = parseRequest(costString);
		resourceToCost.put(resource, cost);
	}

	// new context is created in the dnet
	// TODO find how to clear context so that not to recreate it every time.
	private void reContext() {
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		setContext(ctx);
		initializeDNet(ctx);
	}

	/**************** End of Initializing functions *****************/

	/************************ Transitions ***************************/

	@Guard(name = "canAllocate")
	public boolean canAllocate(@Data(name = "request") String requestString) throws DNetException {
		this.reContext();
		logger.debug("Allocator checking resource availabilities for request " + requestString);
		ConstraintNode request = parseRequest(requestString);
		ArrayList<String> resourcesRequested = request.resourceInConstraint(request);
		logger.debug("The resources requested are " + resourcesRequested);

		Map<String, ArithExpr> nameToExpr = createTokenVariables(resourcesRequested);
		solver.add(request.evaluate(nameToExpr)); // add the request constraint
		logger.debug("Tokens of the dnet at initialisation are: " + placeTokens);
		ArrayList<BoolExpr> dNetConstraints = dnet.run(placeVariables, placeTokens);
		logger.debug("The dnet constraints are: " + dNetConstraints);
		for (BoolExpr constr : dNetConstraints) {
			solver.add(constr);
		}
		addCost();
		if (solver.check() != Status.SATISFIABLE) {
			return false;
		}
		Model model = solver.getModel();
		// there is no optimisation in not putting the same model several times, since the context and the Expressions change for each execution,
		// and they should be coherent with the model
		// we save the model so that we can use it during the allocation phase
		requestToModel.put(requestString, model);
		return true;
	}

	// creates initial tokens in places corresponding to requested resources
	private Map<String, ArithExpr> createTokenVariables(ArrayList<String> resourcesRequested) throws DNetException {
		Map<String, ArithExpr> nameToExpr = new HashMap<String, ArithExpr>();
		for (String requestedResourceName : resourcesRequested) {
			if (dnet.nameToPlace.containsKey(requestedResourceName)) {
				Place place = dnet.nameToPlace.get(requestedResourceName);
				place.addToken(new InitialTransition()); // probably we don't need this line (noone cares for places, we go through the map placeTokens)
				placeTokens.get(place).add(new InitialTransition());
				IntExpr var = createIntVariable(context, createVariableName(place, "*"));
				placeVariables.get(place).add(var);
				nameToExpr.put(requestedResourceName, var);
			} else {
				throw new DNetException("The resource " + requestedResourceName
						+ " does not belond to the space of resources described by the places of the DNet.");
			}
		}
		return nameToExpr;
	}

	private ConstraintNode parseRequest(String requestString) {
		constraintLexer lexer = new constraintLexer(new ANTLRInputStream(requestString));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		constraintParser parser = new constraintParser(tokens);
		parser.constraint();
		ConstraintNode request = parser.constraint;
		request.setContextProvider(this);
		return request;
	}

	@org.bip.annotations.Transition(name = "request", source = "0", target = "0", guard = "canAllocate")
	public void specifyRequest(@Data(name = "request") String requestString) throws DNetException {
		if (!requestToModel.containsKey(requestString)) {
			throw new BIPException("The request " + requestString
					+ " given as data parameter for transition has not been accepted before as data given for the guard.");
		}
		Model model = requestToModel.get(requestString);
		// TODO bus is not in placeVariables, as it is added in the dnet but not in the allocator
		for (Place place : placeVariables.keySet()) {
			if (placeVariables.get(place).size() > 0) {

				for (int i = 0; i < placeVariables.get(place).size(); i++) {
					Expr res = model.evaluate(placeVariables.get(place).get(i), false);
					if (res.isIntNum()) {
						// update the cost after allocating the resource!
						// TODO the resource must be somehow dispatched back to the requesting component
						placeToResource.get(place).decreaseCost(res.toString());
						// TODO this line makes it possible only for one item of each resource to be allocated which is maybe not what we want
						resourceNameToGivenValue.put(place.name(), res);
						logger.debug("Resource " + place.name() + " allocated: " + res.toString() + " units");
					}
				}
			}
		}
		requestToModel.remove(requestString);
	}

	@org.bip.annotations.Transition(name = "release", source = "0", target = "0", guard = "canRelease")
	public void releaseResource(@Data(name = "resourceUnit") ArrayList<String> unitNames) throws DNetException {
		logger.debug("Releasing resources: " + unitNames);
		for (String unit : unitNames) {
			// release the amount allocated for the given resource
			releaseResource(unit);

			// for each place reachable from this place
			if (!dnet.placeNameToPostplacesNames.containsKey(unit)) {
				return;
			}
			for (String placeName : dnet.placeNameToPostplacesNames.get(unit)) {
				logger.debug("Releasing resource : " + placeName + " dependent on " + unit);
				// TODO check the amount (token) only having come from the place released
				// if the resource was allocated, release it
				if (resourceNameToGivenValue.containsKey(unit)) {
					releaseResource(placeName);
				}
//				for (IntExpr e : placeVariables.get(dnet.getPlace(placeName))) {
//					// if e has come from a transition which has unit as i
//				}
			}
		}
//		if (dnet.resourceDependencies.containsKey(unitNames)) {
//			// TODO it will not work because the lists are not the same
//			ArrayList<String> dependentResource = dnet.resourceDependencies.get(unitNames);
//			for (String r : dependentResource) {
//				releaseResource(r);
//			}
//		}
	}

	private void releaseResource(String resourceName) {
		Expr res = resourceNameToGivenValue.get(resourceName);
		// update cost with returning the value
		logger.debug("Releasing resource: " + resourceName + ", the amount allocated was " + res + ", the resource provider is " + placeNameToResource.get(resourceName));
		//TODO remove a corresponding token as well?
		
		// update the cost after deallocating the resource
		placeNameToResource.get(resourceName).augmentCost(res.toString());
	}
	
	@Guard(name = "canRelease")
	public boolean canRelease(@Data(name = "resourceUnit") ArrayList<String> unitNames) throws DNetException {
		for (String unit : unitNames) {
			// TODO what if there are several items of the same resource, then we should also know the name - rather the id - of component?
			// get the expression for the allocated amount for this resource
			Expr res = resourceNameToGivenValue.get(unit);
			if (res == null) {
				return false;
			}
		}
		return true;

	}

	// for each place which has tokens, add its cost - or should it be for each place in general (the case of !=0) ??
	public void addCost() throws DNetException {
		for (Place place : placeVariables.keySet()) {
			Map<String, ArithExpr> stringtoConstraintVar = new HashMap<String, ArithExpr>();
			if (placeVariables.get(place).size() > 0) {
				ArithExpr placeSum = placeVariables.get(place).get(0);
				// if there are several variables, we need to make a sum
				// TODO TEST
				for (int i = 1; i < placeVariables.get(place).size(); i++) {
					placeSum = getContext().mkAdd(placeSum, placeVariables.get(place).get(i));
				}
				stringtoConstraintVar.put(place.name(), placeSum);
				logger.debug("For place " + place.name() + " the token variable names are " + stringtoConstraintVar + " and the constraint is "
						+ resourceToCost);
				BoolExpr costExpr = resourceToCost.get(place.name()).evaluate(stringtoConstraintVar);
				solver.add(costExpr);
			}
		}
	}

	/**************** End of Transitions *****************/

	/**************** Helper functions *****************/
	private String createVariableName(Place place, String transitionName) {
		return place.name() + "-" + transitionName;
	}

	private IntExpr createIntVariable(Context ctx, String name) {
		return (IntExpr) ctx.mkConst(ctx.mkSymbol(name), ctx.getIntSort());
	}

	/**************** End of Helper functions *****************/
	
	/**************** Interface functions *****************/
	

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public void request() {
		// TODO Auto-generated method stub
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addResource(ResourceProvider resource) {
		resources.add(resource);
		nameToResource.put(resource.name(), resource);
		for (Place place : placeVariables.keySet()) {
			if (place.name().equals(resource.name())) {
				placeToResource.put(place, resource);
				placeNameToResource.put(place.name(), resource);
				break;
			}
		}
		this.specifyCost(resource.name(), resource.cost());
	}

	/****************End of  Interface functions *****************/

}
