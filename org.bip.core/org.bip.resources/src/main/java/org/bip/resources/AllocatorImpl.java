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

@Ports({ @Port(name = "request", type = PortType.enforceable), @Port(name = "release", type = PortType.spontaneous) })
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
	//a map: request string <-> model provided by the solver. is used in order not to solve the thing twice during the guard evaluation and the actual allocation
	private HashMap<String, Model> requestToModel;

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

	void setContext(Context ctx) {
		this.context = ctx;
		solver = context.mkSolver();
	}

	// if an allocator received a dnet and no context, it created a context, parses the dnet
	public AllocatorImpl(String dNetPath) throws IOException, RecognitionException, DNetException {
		this();

		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		setContext(ctx);

		parseAndInitializeDNet(dNetPath, ctx);
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

	private void initializeDNet(Context ctx) {
		this.dnet.setContext(ctx);
		placeVariables.clear();
		for (Place place : dnet.places()) {
			placeTokens.put(place, new ArrayList<Transition>());
			placeVariables.put(place, new ArrayList<IntExpr>());
		}
		if (!resources.isEmpty()) {
			resourceToCost.clear();
			// TODO this should be done automatically by each resource? a heartbeat?
			for (ResourceProvider resource : resources) {
				specifyCost(resource.name(), resource.cost());
			}
		}
	}

	private void reContext() {
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		setContext(ctx);
		initializeDNet(ctx);
	}

	@org.bip.annotations.Transition(name = "request", source = "0", target = "1", guard = "canAllocate")
	public void specifyRequest(@Data(name = "request") String requestString) throws DNetException {

		if (!requestToModel.containsKey(requestString)) {
			throw new BIPException("The request given as data parameter for transition has not been accepted before as data given for the guard.");
		}
		Model model = requestToModel.get(requestString);
		for (Place place : placeVariables.keySet()) {
			if (placeVariables.get(place).size() > 0) {
				for (int i = 0; i < placeVariables.get(place).size(); i++) {
					Expr res = model.evaluate(placeVariables.get(place).get(i), false);
					if (res.isIntNum()) {
						placeToResource.get(place).decreaseCost(res.toString());
						// TODO this line makes it possible only for one item of each resource to be allocated which is maybe not what we want
						resourceNameToGivenValue.put(place.name(), res);
					}

				}
			}
		}
	}

	@Guard(name = "canAllocate")
	public boolean canAllocate(@Data(name = "request") String requestString) throws DNetException {
		this.reContext();
		logger.debug("Allocator specifying request " + requestString);
		constraintLexer lexer = new constraintLexer(new ANTLRInputStream(requestString));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		constraintParser parser = new constraintParser(tokens);
		parser.constraint();
		ConstraintNode request = parser.constraint;
		request.setContextProvider(this);

		ArrayList<String> resourcesRequested = request.resourceInConstraint(request);
		logger.debug("The resources requested are " + resourcesRequested);
		Map<String, ArithExpr> stringToConstraintVar = new HashMap<String, ArithExpr>();
		for (String placeName : resourcesRequested) {
			if (dnet.nameToPlace.containsKey(placeName)) {
				Place place = dnet.nameToPlace.get(placeName);
				place.addToken(new InitialTransition()); // probably we don't need this line
				// add a new initial token
				placeTokens.get(place).add(new InitialTransition());
				// add a new variable
				String variableName = createVariableName(place, "*");
				IntExpr var = createIntVariable(context, variableName);
				placeVariables.get(place).add(var);
				stringToConstraintVar.put(placeName, var);
			} else {
				throw new DNetException("The resource " + placeName + " does not belond to the space of resources described by places of the DNet.");
			}
		}
		solver.add(request.evaluate(stringToConstraintVar));
		System.err.println("Request: " + resourcesRequested);
		System.err.println("Tokens: " + placeTokens);
		ArrayList<BoolExpr> dNetConstraints = dnet.run(placeVariables, placeTokens);
		for (BoolExpr constr : dNetConstraints) {
			solver.add(constr);
		}
		addCost();
		if (solver.check() != Status.SATISFIABLE) {
			return false;
		}
		Model model = solver.getModel();
		// if (!requestToModel.containsKey(requestString)) {
		// TODO the problem here is that the model is not replaced. i guess it should. what if there is a same request from different components
		requestToModel.put(requestString, model);
		// }
		return true;
	}

	@org.bip.annotations.Transition(name = "release", source = "1", target = "0", guard = "")
	public void releaseResource(@Data(name = "resourceUnit") String unitName) throws DNetException {
		// get the expression for the allocated amount for this resource
		Expr res = resourceNameToGivenValue.get(unitName);
		// update cost with returning the value

		logger.debug("Releasing resource: " + unitName + ", the amount allocated was " + res + ", the resource provider is "
				+ placeNameToResource.get(unitName));
		placeNameToResource.get(unitName).augmentCost(res.toString());
	}

	// cost: each resource has its cost
	// cost is updated at every cycle and sent to the allocator

	public void addCost() throws DNetException {
		for (Place place : placeVariables.keySet()) {
			Map<String, ArithExpr> stringtoConstraintVar = new HashMap<String, ArithExpr>();
			// if there are already variables, we need to make a sum
			if (placeVariables.get(place).size() > 0) {

				ArithExpr placeSum = placeVariables.get(place).get(0);

				for (int i = 1; i < placeVariables.get(place).size(); i++) {
					placeSum = getContext().mkAdd(placeSum, placeVariables.get(place).get(i));
				}
				stringtoConstraintVar.put(place.name(), placeSum);

				System.err.println(place.name() + " " + resourceToCost + " " + stringtoConstraintVar);
				BoolExpr costExpr = resourceToCost.get(place.name()).evaluate(stringtoConstraintVar);
				System.err.println("COST: " + costExpr);
				solver.add(costExpr);

			}
		}

	}

	private String createVariableName(Place place, String transitionName) {
		return place.name() + "-" + transitionName;
	}

	private IntExpr createIntVariable(Context ctx, String name) {
		return (IntExpr) ctx.mkConst(ctx.mkSymbol(name), ctx.getIntSort());
	}

	@Override
	public Context getContext() {
		return context;
	}

	private void specifyCost(String resource, String costString) {
		constraintLexer lexer = new constraintLexer(new ANTLRInputStream(costString));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		constraintParser parser = new constraintParser(tokens);
		parser.constraint();
		ConstraintNode cost = parser.constraint;
		cost.setContextProvider(this);
		resourceToCost.put(resource, cost);
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
}
