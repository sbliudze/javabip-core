package org.bip.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.print.attribute.standard.Finishings;

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
import org.bip.api.DataOut.AccessType;
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
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

@Ports({ @Port(name = "request", type = PortType.enforceable), @Port(name = "release", type = PortType.enforceable), 
	 @Port(name = "provideResource", type = PortType.enforceable)})
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
	// upd: this should be redundant. this map uses place.name(), the above map uses resource.name().
	// therefore, TODO: check that for every resource there is a place and for every place there is a resource
	// what if we have several resources for the same place? they should have still one provider, right, or?.. (see the very first todo line 47)
	private HashMap<String, ResourceProvider> placeNameToResource;

	// a map: dnet place <-> list of tokens contained in that place (=list of transition that have put tokens in there)
	public HashMap<Place, ArrayList<Transition>> placeTokens;
	// a map: dnet place <-> list of variables contained in that place (should be equal in size to the list of tokens)
	public HashMap<Place, ArrayList<IntExpr>> placeVariables;
	// a map: resource name <-> resource constraint (as provided by the resource and then parsed)
	private HashMap<String, ConstraintNode> resourceToCost;
	// a map: resource name <-> value produced by the solver (is used to find out how much we should release upon spontaneous release event)
	// TODO it won't work this way if we have several components asking for the same resource - even if it is done in several times. or will I erase it? CHECK
	//private HashMap<String, Expr> resourceNameToGivenValue;
	private HashMap<Integer, HashMap<String, Expr>> resourceNameToGivenValueInAllocation;
	// a map: request string <-> model provided by the solver. is used in order not to solve the thing twice during the guard evaluation and the actual
	// allocation
	private HashMap<String, Model> requestToModel;
	/**
	 * The id of the resource proxy that was allocated to an asking component. Must be updated at each allocation cycle.
	 */
	//private String currentResourceID;
	/**
	 * The map containing the requested resources in pairs: the lable of requested resource <-> the id of the provided resource proxy.
	 */
	private Hashtable<String, String> resourceLableToID;
	private Hashtable<String, Integer> resourceLableToAmount;
	
	/**
	 * An id which is unique for each new allocation (each new specifyRequest())
	 * This id is used to determine which amount of resources is released 
	 * when there are multiple allocations of the same resource
	 */
	private int allocationID;

	/**************** Constructors *****************/

	public AllocatorImpl() {
		placeTokens = new HashMap<Place, ArrayList<Transition>>();
		placeVariables = new HashMap<Place, ArrayList<IntExpr>>();
		resourceToCost = new HashMap<String, ConstraintNode>();
		resources = new ArrayList<ResourceProvider>();
		nameToResource = new HashMap<String, ResourceProvider>();
		placeToResource = new HashMap<Place, ResourceProvider>();
		placeNameToResource = new HashMap<String, ResourceProvider>();
		resourceNameToGivenValueInAllocation = new HashMap<Integer, HashMap<String,Expr>>(); //new HashMap<String, Expr>();
		requestToModel = new HashMap<String, Model>();
		requestToTokens = new HashMap<String, HashMap<Place,ArrayList<IntExpr>>>();
		resourceLableToID = new Hashtable<String, String>();
		resourceLableToAmount = new Hashtable<String, Integer>();
		allocationID = 0;
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

	//private String componentID = "";
	private  HashMap<String, HashMap<Place, ArrayList<IntExpr>>> requestToTokens; //placeVariables
	
	@Guard(name = "canAllocate")
	public boolean canAllocate(@Data(name = "request-id") ArrayList<String> ddd) throws DNetException {
	//public boolean canAllocate(@Data(name = "request") String requestString, @Data(name = "id") String componentID) throws DNetException {
		this.reContext();
		String requestString = ddd.get(0);
		String componentID = ddd.get(1);
		logger.debug("Allocator checking resource availabilities for request " + requestString);
		ConstraintNode request = parseRequest(requestString);
		ArrayList<String> resourcesRequested = request.resourceInConstraint(request);
		logger.debug("The resources requested are " + resourcesRequested);

		Map<String, ArithExpr> nameToExpr = createTokenVariables(resourcesRequested);
		solver.add(request.evaluate(nameToExpr)); // add the request constraint
		logger.debug("Tokens of the dnet at initialisation are: " + placeTokens);
		ArrayList<BoolExpr> dNetConstraints = dnet.run(placeVariables, placeTokens);
		logger.debug("For component " + componentID +" The dnet constraints are: " + dNetConstraints);
		for (BoolExpr constr : dNetConstraints) {
			solver.add(constr);
		}
		addCost();
		if (solver.check() != Status.SATISFIABLE) {
			//System.err.println("Allocation for " + requestString + " in component " + componentID + " IS NOT POSSIBLE.");
			//System.err.println("Constraints are " + solver);
			return false;
		}
		//System.err.println("Allocation for " + requestString + " OK.");
		Model model = solver.getModel();
		//System.err.println("Allocation for " + requestString + " of " + componentID +  ": "+ model);
		// there is no optimisation in not putting the same model several times, since the context and the Expressions change for each execution,
		// and they should be coherent with the model
		// we save the model so that we can use it during the allocation phase
		requestToModel.put(componentID+requestString, model);
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
	
	// TODO rename to allocate?
	@org.bip.annotations.Transition(name = "request", source = "0", target = "1", guard = "canAllocate")
	//public void specifyRequest(@Data(name = "request") String requestString, @Data(name = "id") String componentID) throws DNetException {
	public void specifyRequest(@Data(name = "request-id") ArrayList<String> ddd) throws DNetException {
		String requestString = ddd.get(0);
		String componentID = ddd.get(1);
		if (!requestToModel.containsKey(componentID+requestString)) {
			throw new BIPException("The request " + requestString + " of component " + componentID
					+ " given as data parameter for transition has not been accepted before as data given for the guard.");
		}
		Model model = requestToModel.get(componentID+requestString);
		resourceLableToID.clear();
		resourceLableToAmount.clear();
		//System.out.println(model.toString());
		// TODO bus is not in placeVariables, as it is added in the dnet but not in the allocator
		HashMap<String, Expr> resourceNameToGivenValue = new HashMap<String, Expr>();
		FuncDecl [] delc = model.getConstDecls();
		//TODO can there be a case that there are not only the constants, but also the other more complex functions?
		// then getConstDecls is not enough, and we should also use model.getFuncDecls() 
		System.err.println("----------");
		for (FuncDecl func : model.getConstDecls()) {
			//System.err.println(func.getName() + " - "  + model.getConstInterp(func));
			String tokenName = func.getName().toString();
			String placeName = tokenName.substring(0, tokenName.indexOf('-'));
			Place place = dnet.nameToPlace.get(placeName);
			String allocAmount = model.getConstInterp(func).toString();
			int i_r = Integer.parseInt(allocAmount);
			if (i_r != 0) {
				System.err.println(place.name() + ": " + i_r);
			}
			placeToResource.get(place).decreaseCost(allocAmount);
			//currentResourceID = placeToResource.get(place).resourceID();
			//TODO the label is not necessarily place.name()
			resourceLableToID.put(place.name(), placeToResource.get(place).providedResourceID());
			// TODO this line makes it possible only for one item of each resource to be allocated which is maybe not what we want
			if (!resourceNameToGivenValue.containsKey(placeName)) {
				resourceNameToGivenValue.put(placeName, model.getConstInterp(func));
				resourceLableToAmount.put(place.name(), i_r);
			}
			else {
				//TODO put a sum of two expressions here
				resourceNameToGivenValue.put(place.name(), model.getConstInterp(func));
				int prevValue = resourceLableToAmount.get(placeName);
				resourceLableToAmount.put(placeName, i_r+prevValue);
			}
			logger.info("Resource " + place.name() +  ", variable " + tokenName +" allocated: " + allocAmount + " units");
		}
		System.err.println("----------");
//		for (Place place : placeVariables.keySet()) {
//			if (placeVariables.get(place).size() > 0) {
//				//System.err.println(allocationID + " " + placeVariables.get(place));
//				for (int i = 0; i < placeVariables.get(place).size(); i++) {
//					Expr varName = placeVariables.get(place).get(i);
//					Expr res = model.evaluate(varName, false);
//					System.err.println(allocationID + " " + varName + " " + model.getConstInterp(varName));
//					if (res.isIntNum()) {
//						int i_r = Integer.parseInt(res.toString());
//						if (i_r != 0) {
//							System.err.println(place.name() + ": " + i_r);
//						}
//						// update the cost after allocating the resource!
//						placeToResource.get(place).decreaseCost(res.toString());
//						//currentResourceID = placeToResource.get(place).resourceID();
//						//TODO the label is not necessarily place.name()
//						resourceLableToID.put(place.name(), placeToResource.get(place).providedResourceID());
//						// TODO this line makes it possible only for one item of each resource to be allocated which is maybe not what we want
//						resourceNameToGivenValue.put(place.name(), res);
//						resourceLableToAmount.put(place.name(), i_r);
//						logger.info("Resource " + place.name() +  ", variable " + varName.toString() +" allocated: " + res.toString() + " units");
//					}
//				}
//			}
//		}
		resourceNameToGivenValueInAllocation.put(allocationID, resourceNameToGivenValue);
		//System.err.println(allocationID + " " + resourceNameToGivenValueInAllocation);
		allocationID++;
	
		requestToModel.remove(componentID+requestString);
	}
	
	@org.bip.annotations.Transition(name = "provideResource", source = "1", target = "0", guard = "")
	public void provideResource() throws DNetException {
		//? no meaningful things to do
	}
	

	@Data(name = "resources", accessTypePort = AccessType.allowed, ports = { "provideResource" })
	public Hashtable<String, String> resources() {
		return resourceLableToID;
		// TODO send only resources >0?
	}
	
	@Data(name = "amounts", accessTypePort = AccessType.allowed, ports = { "provideResource" })
	public Hashtable<String, Integer> amounts() {
		return resourceLableToAmount;
	}
	
	@Data(name = "allocID", accessTypePort = AccessType.allowed, ports = { "provideResource" })
	public int allocID() {
		return allocationID-1;
	}
	
	@org.bip.annotations.Transition(name = "release", source = "0", target = "0", guard = "canRelease")
	public void releaseResource(@Data(name = "resourceUnit") ArrayList<String> unitNames, @Data(name = "allocID") int allocID) throws DNetException {
		logger.debug("Releasing resources: " + unitNames);
		String alloc = unitNames.get(unitNames.size()-1);
		int allocId = Integer.parseInt(alloc);
		for (int i=0; i< unitNames.size()-1; i++) {
			String unit = unitNames.get(i);
			// release the amount allocated for the given resource
			releaseResource(unit, allocId);

			// for each place reachable from this place
			if (!dnet.placeNameToPostplacesNames.containsKey(unit)) {
				break;
			}
			recursiveRelease(allocId, unit);
		}
		//System.out.println("resources released " + ": " + allocID + " " + unitNames);
	}

	private void recursiveRelease(int allocID, String unit) {
		if (dnet.placeNameToPostplacesNames.get(unit)==null) return;
		for (String placeName : dnet.placeNameToPostplacesNames.get(unit)) {
			logger.debug("Releasing resource: " + placeName + " dependent on " + unit);
			// TODO check the amount (token) only having come from the place released
			// if the resource was allocated, release it
			if (resourceNameToGivenValueInAllocation.get(allocID).containsKey(unit)) {
				releaseResource(placeName, allocID);
				recursiveRelease(allocID, placeName);
			}
		}
	}

	private void releaseResource(String resourceName, int allocID) {
		//System.out.println(placeNameToResource + ": " + allocID + " " + resourceName);
		Expr res = resourceNameToGivenValueInAllocation.get(allocID).get(resourceName);
		// update cost with returning the value
		logger.info("Releasing resource: " + resourceName + ", the amount allocated was " + res + ", the resource provider is " + placeNameToResource.get(resourceName));
		//TODO remove a corresponding token as well? - why, no, there are no tokens saved between runs
		
		// update the cost after deallocating the resource
		placeNameToResource.get(resourceName).augmentCost(res.toString());
	}
	
	@Guard(name = "canRelease") //, @Data(name = "allocID") int allocID
	public boolean canRelease(@Data(name = "resourceUnit") ArrayList<String> unitNames) throws DNetException {
		String alloc = unitNames.get(unitNames.size()-1);
		int allocID = Integer.parseInt(alloc);
		if (resourceNameToGivenValueInAllocation.get(allocID)==null) return false;
		//System.out.println(resourceNameToGivenValueInAllocation + ": " + allocID + " " + unitNames);
		for (int i=0; i< unitNames.size()-1; i++) {
			String unit = unitNames.get(i);
			// TODO what if there are several items of the same resource, then we should also know the name - rather the id - of component?
			// get the expression for the allocated amount for this resource
			Expr res = resourceNameToGivenValueInAllocation.get(allocID).get(unit);
			if (res == null) {
				System.out.println("No release: " + allocID + " " + unit);
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
				// TODO TEST sum of the variables
				for (int i = 1; i < placeVariables.get(place).size(); i++) {
					placeSum = getContext().mkAdd(placeSum, placeVariables.get(place).get(i));
				}
				stringtoConstraintVar.put(place.name(), placeSum);
				logger.debug("For place " + place.name() + " the token variable names are " + stringtoConstraintVar + " and the constraint is "
						+ resourceToCost.get(place.name()));
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
