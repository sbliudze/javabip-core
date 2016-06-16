package org.bip.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;
import org.bip.api.ResourceProvider;
import org.bip.constraint.ConstraintSolver;
import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.PlaceVariable;
import org.bip.constraint.ResourceAllocation;
import org.bip.constraint.VariableExpression;
import org.bip.exceptions.BIPException;
import org.bip.resources.grammar.constraintLexer;
import org.bip.resources.grammar.constraintParser;
import org.bip.resources.grammar.dNetLexer;
import org.bip.resources.grammar.dNetParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "request", type = PortType.enforceable), @Port(name = "release", type = PortType.enforceable),
		@Port(name = "provideResource", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.resources.AllocatorImpl")
public class AllocatorImpl implements Allocator {

	private Logger logger = LoggerFactory.getLogger(AllocatorImpl.class);

	private DNet dnet;
	private ConstraintSolver solver;
	private ExpressionCreator factory;
	// The list of all available resources
	// So far the resource types are not used. If they are introduced, the list might have to be changed into hashmap
	private ArrayList<ResourceProvider> resources;
	// a map: resource (place) name <-> resource provider
	private HashMap<String, ResourceProvider> placeNameToResource;
	// a map: dnet place <-> list of tokens contained in that place (=list of transition that have put tokens in there)
	public HashMap<Place, ArrayList<Transition>> placeTokens;
	// a map: dnet place <-> list of variables contained in that place (should be equal in size to the list of tokens)
	public HashMap<Place, ArrayList<PlaceVariable>> placeVariables;
	// a map: resource name <-> resource constraint (as provided by the resource and then parsed)
	private HashMap<String, ConstraintNode> resourceToCost;
	// a map: allocation number <-> allocation produced by the solver (is used to find out how much we should release upon spontaneous release event)
	private HashMap<Integer, ResourceAllocation> allocations;
	// a map: request string <-> model provided by the solver.
	// is used in order not to solve the thing twice during the guard evaluation and the actual allocation
	private HashMap<String, ResourceAllocation> requestToModel;
	/**
	 * The map containing the requested resources in pairs: the label of requested resource <-> the id of the provided resource proxy.
	 */
	private Hashtable<String, String> resourceLableToID;
	/**
	 * The map containing the requested resources in pairs: the label of requested resource <-> the allocated amount of the resource.
	 */
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
		placeVariables = new HashMap<Place, ArrayList<PlaceVariable>>();
		resourceToCost = new HashMap<String, ConstraintNode>();
		resources = new ArrayList<ResourceProvider>();
		placeNameToResource = new HashMap<String, ResourceProvider>();
		allocations = new HashMap<Integer, ResourceAllocation>();
		requestToModel = new HashMap<String, ResourceAllocation>();
		resourceLableToID = new Hashtable<String, String>();
		resourceLableToAmount = new Hashtable<String, Integer>();
		allocationID = 0;
	}

	// if an allocator received a dnet and no context, it creates a context and parses the dnet
	public AllocatorImpl(String dNetPath, ConstraintSolver solver) throws IOException, RecognitionException, DNetException {
		this();
		this.solver = solver;
		factory = solver.expressionCreator();
		parseAndInitializeDNet(dNetPath, factory);
	}

	private void parseAndInitializeDNet(String dNetPath, ExpressionCreator factory) throws FileNotFoundException, IOException, DNetException {
		FileInputStream stream = new FileInputStream(dNetPath);
		dNetLexer lexer = new dNetLexer(new ANTLRInputStream(stream));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		dNetParser parser = new dNetParser(tokens);
		parser.net();
		this.dnet = parser.net;
		this.dnet.setFactory(factory);
		initializeDNet();
	}

	// at the initialization phase of the dnet:
	// the context of the dnet is specified,
	// the variables and tokens are cleared and initialized with empty lists
	// in case of reinitialization (list of resources is not empty), the resource cost is stored in a local map
	// TODO the cost must be resend by resource, not asked by allocator
	// however, in this case it might be ok since it is the allocator that reinitialises itself
	private void initializeDNet() {
		this.dnet.reInit();
		placeVariables.clear();
		placeTokens.clear();
		for (Place place : dnet.places()) {
			placeTokens.put(place, new ArrayList<Transition>());
			placeVariables.put(place, new ArrayList<PlaceVariable>());
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



	/**************** End of Initializing functions *****************/

	/************************ Transitions ***************************/

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	@Guard(name = "canAllocate")
	public boolean canAllocate(@Data(name = "request-id") ArrayList<String> ddd) throws DNetException {
	//public boolean canAllocate(@Data(name = "request") String requestString, @Data(name = "id") String componentID) throws DNetException {
		solver.newCycle(); initializeDNet();
		String requestString = ddd.get(0);
		String componentID = ddd.get(1);
		logger.debug("Allocator checking resource availabilities for request " + requestString);
		ConstraintNode request = parseRequest(requestString);
		ArrayList<String> resourcesRequested = request.resourceInConstraint(request);
		checkRequestedResourcesExist(resourcesRequested, requestString);
		logger.debug("The resources requested are " + resourcesRequested);

		Map<String, VariableExpression> nameToExpr = createTokenVariables(resourcesRequested);
		
		//DnetConstraint dnc = request.evaluateN(nameToExpr);
		//System.out.println("REQUEST " + dnc);
		solver.addConstraint(request.evaluateN(nameToExpr)); // add the request constraint
		logger.debug("Tokens of the dnet at initialisation are: " + placeTokens);
		ArrayList<DnetConstraint> dNetConstraints = dnet.run(placeVariables, placeTokens);
		logger.debug("For component " + componentID +" The dnet constraints are: " + dNetConstraints);
		//System.err.println("################### " + requestString);
		for (DnetConstraint constr : dNetConstraints) {
			solver.addConstraint(constr);
		//	System.out.println(constr);
		}
		//System.err.println("################### ");
		addCost();
		if (!solver.isSolvable()) { 
			return false;
		}
		ResourceAllocation model = solver.getAllocation();
		// there is no optimisation in not putting the same model several times, since the context and the Expressions change for each execution,
		// and they should be coherent with the model
		// we save the model so that we can use it during the allocation phase
		requestToModel.put(componentID+requestString, model);
		return true;
	}

	/**
	 * We assume that every resource required in the request has the same name as the places of the DNet.
	 * If this is not the case, a BIPException is thrown
	 * @param resourcesRequested an array of resources present in the request
	 * @param requestString the request provided by a component which is being checked
	 */
	private void checkRequestedResourcesExist(ArrayList<String> resourcesRequested, String requestString) {
		for (String resource: resourcesRequested) {
			if (!placeNameToResource.containsKey(resource))
				throw new BIPException("Resource " + resource + " asked in request " + requestString + " cannot be found among the resources of the system.");
		}
		
	}

	// creates initial tokens in places corresponding to requested resources
	private Map<String, VariableExpression> createTokenVariables(ArrayList<String> resourcesRequested) throws DNetException {
		Map<String, VariableExpression> nameToExpr = new HashMap<String, VariableExpression>();
		for (String requestedResourceName : resourcesRequested) {
			if (dnet.nameToPlace.containsKey(requestedResourceName)) {
				Place place = dnet.nameToPlace.get(requestedResourceName);
				place.addToken(new InitialTransition()); // probably we don't need this line (noone cares for places, we go through the map placeTokens)
				placeTokens.get(place).add(new InitialTransition());
				PlaceVariable var = factory.createVariable(place.name() + "-*");
				//createIntVariable(context, createVariableName(place, "*"));
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
		request.addFactory(factory);
		//request.setContextProvider(this);
		return request;
	}
	
	//TODO there is no sense in guarding place variables as they are different for different runs, and not necessarily
	// the last run will be the one that was chosen
	
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
		ResourceAllocation model = requestToModel.get(componentID+requestString);
		System.out.println(model.resourceAmounts());
		resourceLableToID.clear();
		resourceLableToAmount.clear();
		//HashMap<String, Expr> resourceNameToGivenValue = new HashMap<String, Expr>();
		System.err.println("----------");
		//System.out.println(placeVariables);
		for (String str : model.resourceAmounts().keySet()) {
			String allocAmount = model.resourceAmount(str);
			if (Integer.parseInt(allocAmount)!=0)	{System.err.println(str + "--" + allocAmount);}
			placeNameToResource.get(str).decreaseCost(allocAmount);	
			resourceLableToID.put(str, placeNameToResource.get(str).providedResourceID());
			resourceLableToAmount.put(str, Integer.parseInt(allocAmount));
		}
		System.err.println("----------");
		allocations.put(allocationID, model);
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
//		System.out.println("resources released " + ": " + allocID + " " + allocations.get(allocId));
//		for (ResourceProvider provider: resources) {
//			System.out.println(provider.name() + " " + provider.cost());
//		}
	}

	private void recursiveRelease(int allocID, String unit) {
		if (dnet.placeNameToPostplacesNames.get(unit)==null) return;
		for (String placeName : dnet.placeNameToPostplacesNames.get(unit)) {
			logger.debug("Releasing resource: " + placeName + " dependent on " + unit);
			// TODO check the amount (token) only having come from the place released
			// if the resource was allocated, release it
			if (allocations.get(allocID).hasResource(unit)) {
				releaseResource(placeName, allocID);
				recursiveRelease(allocID, placeName);
			}
		}
	}

	private void releaseResource(String resourceName, int allocID) {
		//System.out.println(placeNameToResource + ": " + allocID + " " + resourceName); 
		String res = allocations.get(allocID).resourceAmount(resourceName);
		// update cost with returning the value
		logger.info("Releasing resource: " + resourceName + ", the amount allocated was " + res + ", the resource provider is " + placeNameToResource.get(resourceName));
		//TODO remove a corresponding token as well? - why, no, there are no tokens saved between runs
		
		// update the cost after deallocating the resource
		placeNameToResource.get(resourceName).augmentCost(res);
	}
	
	@Guard(name = "canRelease") //, @Data(name = "allocID") int allocID
	public boolean canRelease(@Data(name = "resourceUnit") ArrayList<String> unitNames) throws DNetException {
		String alloc = unitNames.get(unitNames.size()-1);
		int allocID = Integer.parseInt(alloc);
		if (allocations.get(allocID)==null) return false;
		//System.out.println(resourceNameToGivenValueInAllocation + ": " + allocID + " " + unitNames);
		for (int i=0; i< unitNames.size()-1; i++) {
			String unit = unitNames.get(i);
			// TODO what if there are several items of the same resource, then we should also know the name - rather the id - of component?
			// get the expression for the allocated amount for this resource
			String res = allocations.get(allocID).resourceAmount(unit);
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
			Map<String, VariableExpression> stringtoConstraintVar = new HashMap<String, VariableExpression>();
			if (placeVariables.get(place).size() > 0) {
				//ArithExpr placeSum = placeVariables.get(place).get(0).variable();
				// if there are several variables, we need to make a sum
				// TODO TEST sum of the variables
				VariableExpression sum = factory.sumTokens(placeVariables.get(place));
//				for (int i = 1; i < placeVariables.get(place).size(); i++) {
//					placeSum = getContext().mkAdd(placeSum, placeVariables.get(place).get(i).variable());
//				}
				stringtoConstraintVar.put(place.name(), sum);
				logger.debug("For place " + place.name() + " the token variable names are " + stringtoConstraintVar + " and the constraint is "
						+ resourceToCost.get(place.name()));
				DnetConstraint costExpr = resourceToCost.get(place.name()).evaluateN(stringtoConstraintVar);
				//System.err.println(place.name() +" "+ costExpr);
				solver.addConstraint(costExpr);
			}
		}
		//System.err.println("################### ");
	}

	/**************** End of Transitions *****************/

	/**************** End of Helper functions *****************/

	/**************** Interface functions *****************/


	@Override
	public void request() {
		// TODO Auto-generated method stub
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

	
	/**
	 * We assume that each resource provider has a place in the DNet with the same name.
	 * We also assume that the DNet is provided to the Allocator before the resource providers are specified.
	 */
	@Override
	public void addResource(ResourceProvider resource) {
		resources.add(resource);
		placeNameToResource.put(resource.name(), resource);
		this.specifyCost(resource.name(), resource.cost());
		if (!this.dnet.nameToPlace.keySet().contains(resource.name())) {
			throw new BIPException("The resource provider " + resource + " does not have a corresponding place in the DNet.");
		}
	}
	
	/****************End of  Interface functions *****************/

}
