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
	private HashMap<String, ConstraintNode> resourceToConstraint;
	private HashMap<String, Utility> resourceToCost;
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
	
	/**
	 * Variable specifying whether we use only constraints trying to satisfy them all,
	 * or whether we also try to maximize [global utility = comp utility - cost sum]
	 */
	private boolean hasUtility = false;
	private PlaceVariable uVar;


	/**************** Constructors *****************/

	public AllocatorImpl() {
		placeTokens = new HashMap<Place, ArrayList<Transition>>();
		placeVariables = new HashMap<Place, ArrayList<PlaceVariable>>();
		resourceToConstraint = new HashMap<String, ConstraintNode>();
		resources = new ArrayList<ResourceProvider>();
		placeNameToResource = new HashMap<String, ResourceProvider>();
		allocations = new HashMap<Integer, ResourceAllocation>();
		requestToModel = new HashMap<String, ResourceAllocation>();
		resourceLableToID = new Hashtable<String, String>();
		resourceLableToAmount = new Hashtable<String, Integer>();
		resourceToCost = new HashMap<String, Utility>();
		allocationID = 0;
	}

	public AllocatorImpl(String dNetPath, ConstraintSolver solver) throws IOException, RecognitionException, DNetException {
		this();
		this.solver = solver;
		this.factory = solver.expressionCreator();
		parseAndInitializeDNet(dNetPath, factory);
	}
	
	public AllocatorImpl(String dNetPath, ConstraintSolver solver, boolean hasUtility) throws IOException, RecognitionException, DNetException {
		this();
		this.solver = solver;
		this.factory = solver.expressionCreator();
		this.hasUtility = hasUtility;
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

	private void initializeDNet() throws DNetException {
		/*
		 * At the initialization of the dnet, 
		 * the variables and tokens are cleared and initialized with empty lists.
		 * In case of reinitialisation (list of resources is not empty), the resource cost is stored in a local map.
		 * TODO: optimize so that the resourceToCost is not regenerated every time, but updates only the values that were used.
		 * Maybe do it by reversing the direction: the resource informs the allocator, but not the allocator asks the resource.
		 */
		this.dnet.reInit();
		placeVariables.clear();
		placeTokens.clear();
		for (Place place : dnet.places()) {
			placeTokens.put(place, new ArrayList<Transition>());
			placeVariables.put(place, new ArrayList<PlaceVariable>());
		}
		if (!resources.isEmpty()) {
			resourceToConstraint.clear();
			resourceToCost.clear();
			for (ResourceProvider resource : resources) {
				if (!hasUtility) {
				specifyCost(resource.name(), resource.constraint());
				} else {
					specifyCost(resource.name(), resource.cost());
				}
			}
		}
	}

	private void specifyCost(String resource, String costString) throws DNetException {
		if (!hasUtility) {
			ConstraintNode resourceConstraint = parseConstraint(costString);
			resourceToConstraint.put(resource, resourceConstraint);
		} else {
			Utility cost = parseCostOrUtility(costString);
			resourceToCost.put(resource, cost);
		}
	}

	/**************** End of Initializing functions *****************/

	/************************ Transitions ***************************/
	
	@Guard(name = "canAllocate")
	public boolean canAllocate(@Data(name = "request-id") ArrayList<String> tempComponentData) throws DNetException {
		solver.newCycle();
		initializeDNet();
		
		// This part should be gone once the Allocator is remodelled as Resource Coordinator.
		String requestString = tempComponentData.get(0);
		String componentID = tempComponentData.get(1);

		logger.debug("Allocator checking resource availabilities for request " + requestString);
		addRequest(requestString);

		ArrayList<DnetConstraint> dNetConstraints = dnet.run(placeVariables, placeTokens);
		logger.debug("For component " + componentID + " The dnet constraints are: " + dNetConstraints);
		for (DnetConstraint constr : dNetConstraints) {
			solver.addConstraint(constr);
		}

		addCost();

		if (!solver.isSolvable(hasUtility)) {
			return false;
		}

		ResourceAllocation model = solver.getAllocation();
		/* There is no possibility to optimize by putting the model to be used several times.
		 * This is because the inner structure of the solver changes for each execution,
		 * The model should be consistent with the solver, therefore, we save it for the round.
		 */
		requestToModel.put(componentID + requestString, model);
		return true;
	}

	private void addRequest(String requestString) throws DNetException {
		ConstraintNode request = null;
		Utility u = null;
		
		if (!hasUtility) {
			request = parseConstraint(requestString);
		} else {
			// TODO create its own parser to move it to constraint parser instead of dnet parser
			u = parseCostOrUtility(requestString);
			// to get the request from the utility, we take the fist value of constraint, 
			// assumption: for every utility value, all the resources are present
			request = u.utility().values().iterator().next();
		}
		ArrayList<String> resourcesRequested = request.resourceInConstraint(request);
		checkRequestedResourcesExist(resourcesRequested, requestString);
		logger.debug("The resources requested are " + resourcesRequested);

		Map<String, VariableExpression> nameToVariable = createInitialTokenVariables(resourcesRequested);
		
		logger.debug("Tokens of the dnet at initialisation are: " + placeTokens);
		
		if (hasUtility) {
			uVar = factory.createUtilityVariable();
			solver.addConstraint(factory.createUtilityConstraint(uVar, u.utility(), nameToVariable));
		}
		else {
			solver.addConstraint(request.evaluateN(nameToVariable));
		}
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

	/**
	 * Creates initial tokens in places corresponding to requested resources
	 * @param resourcesRequested the list of resources requested by a component
	 * @return a map resource name <-> created initial variable
	 * @throws DNetException when there is no DNet place corresponding to the resource name
	 */
	private Map<String, VariableExpression> createInitialTokenVariables(ArrayList<String> resourcesRequested) throws DNetException {
		Map<String, VariableExpression> nameToVariable = new HashMap<String, VariableExpression>();
		for (String requestedResourceName : resourcesRequested) {
			if (dnet.nameToPlace.containsKey(requestedResourceName)) {
				Place place = dnet.nameToPlace.get(requestedResourceName);
				placeTokens.get(place).add(new InitialTransition());
				PlaceVariable initialVariable = factory.createVariable(place.name() + "-*");
				placeVariables.get(place).add(initialVariable);
				nameToVariable.put(requestedResourceName, initialVariable);
			} else {
				throw new DNetException("The resource " + requestedResourceName
						+ " does not belond to the space of resources described by the places of the DNet.");
			}
		}
		return nameToVariable;
	}

	private ConstraintNode parseConstraint(String requestString) throws DNetException {
		if (!hasUtility) {
			constraintLexer lexer = new constraintLexer(new ANTLRInputStream(requestString));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			constraintParser parser = new constraintParser(tokens);
			parser.constraint();
			ConstraintNode request = parser.constraint;
			request.addFactory(factory);
			return request;
		}
		throw new DNetException("The parseConstraint method was called for presumably a utility or cost string");
	}

	private Utility parseCostOrUtility(String costString) throws DNetException {
		if (hasUtility) {
			// TODO create its own parser or move it to constraint parser instead of dnet parser
			dNetLexer lexer = new dNetLexer(new ANTLRInputStream(costString));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			dNetParser parser = new dNetParser(tokens);
			parser.utility();
			Utility u = parser.utility;
			return u;
		}
		throw new DNetException("The parseCostOrUtility method was called for presumably a constraint string");
	}
	
	@org.bip.annotations.Transition(name = "request", source = "0", target = "1", guard = "canAllocate")
	public void specifyRequest(@Data(name = "request-id") ArrayList<String> tempComponentData) throws DNetException {
		String requestString = tempComponentData.get(0);
		String componentID = tempComponentData.get(1);
		
		if (!requestToModel.containsKey(componentID + requestString)) {
			throw new BIPException("The request " + requestString + " of component " + componentID
					+ " given as data parameter for transition has not been accepted before as data given for the guard.");
		}
		
		ResourceAllocation model = requestToModel.get(componentID + requestString);
		resourceLableToID.clear();
		resourceLableToAmount.clear();
		System.err.println("----------");
		for (String resourceName : model.resourceAmounts().keySet()) {
			String amountString = model.resourceAmount(resourceName);
			if (Integer.parseInt(amountString) != 0) {
				System.err.println(resourceName + "--" + amountString);
			}
			placeNameToResource.get(resourceName).decreaseCost(amountString);
			resourceLableToID.put(resourceName, placeNameToResource.get(resourceName).providedResourceID());
			resourceLableToAmount.put(resourceName, Integer.parseInt(amountString));
		}
		System.err.println("----------");
		
		allocations.put(allocationID, model);
		allocationID++;
		requestToModel.remove(componentID+requestString);
	}
	
	@org.bip.annotations.Transition(name = "provideResource", source = "1", target = "0", guard = "")
	public void provideResource() throws DNetException {
		//? no meaningful things to do - just provide the data
	}
	

	@Data(name = "resources", accessTypePort = AccessType.allowed, ports = { "provideResource" })
	public Hashtable<String, String> resources() {
		return resourceLableToID;
		// Think if we should send all the resources or only those whose amounts are greater than 0
	}
	
	@Data(name = "amounts", accessTypePort = AccessType.allowed, ports = { "provideResource" })
	public Hashtable<String, Integer> amounts() {
		return resourceLableToAmount;
		//TODO add the return of the cost/utility to the demanding component
	}
	
	@Data(name = "allocID", accessTypePort = AccessType.allowed, ports = { "provideResource" })
	public int allocID() {
		return allocationID - 1;
	}
	
	@org.bip.annotations.Transition(name = "release", source = "0", target = "0", guard = "canRelease")
	public void releaseResource(@Data(name = "resourceUnit") ArrayList<String> unitNames) throws DNetException {
		logger.debug("Releasing resources: " + unitNames);
		// The id of the allocation is passed as the last element of the unitNames
		// This will be fixed once we have a Resource Coordinator.
		String allocIdString = unitNames.get(unitNames.size() - 1);
		int allocId = Integer.parseInt(allocIdString);
		for (int i = 0; i < unitNames.size() - 1; i++) {
			String unit = unitNames.get(i);
			// release the amount allocated for the given resource
			releaseResource(unit, allocId);

			// for each place reachable from this place, release those resources as well
			if (dnet.placeNameToPostplacesNames.containsKey(unit)) {
				recursiveRelease(allocId, unit);
			}
		}
	}

	private void recursiveRelease(int allocID, String resourceName) {
		if (dnet.placeNameToPostplacesNames.get(resourceName) == null) {
			return;
		}
		for (String placeName : dnet.placeNameToPostplacesNames.get(resourceName)) {
			logger.debug("Releasing resource: " + placeName + " dependent on " + resourceName);
			// TODO check the amount (token) only having come from the place released.
			if (allocations.get(allocID).hasResource(resourceName)) {
				releaseResource(placeName, allocID);
				recursiveRelease(allocID, placeName);
			}
		}
	}

	private void releaseResource(String resourceName, int allocID) {
		String amountToRelease = allocations.get(allocID).resourceAmount(resourceName);
		logger.info("Releasing resource: " + resourceName + ", the amount allocated was " + amountToRelease + ", the resource provider is " + placeNameToResource.get(resourceName));
		// update the cost after deallocating the resource
		placeNameToResource.get(resourceName).augmentCost(amountToRelease);
	}
	
	@Guard(name = "canRelease") 
	public boolean canRelease(@Data(name = "resourceUnit") ArrayList<String> unitNames) throws DNetException {
		String allocIdString = unitNames.get(unitNames.size() - 1);
		int allocID = Integer.parseInt(allocIdString);
		if (allocations.get(allocID) == null)
			return false;
		for (int i = 0; i < unitNames.size() - 1; i++) {
			String resourceToRelease = unitNames.get(i);
			// TODO if there are several items of resource allocated, we should remember which amount was given to which component
			String resourceAmoune = allocations.get(allocID).resourceAmount(resourceToRelease);
			if (resourceAmoune == null) {
				System.out.println("No release possible for allocation " + allocID + " and resource " + resourceToRelease);
				return false;
			}
		}
		return true;
	}

	/**
	 * We assume that by default 0 amount of the resource is allocated.
	 * For each place that has tokens, we add its cost to the solver constraints
	 */
	public void addCost() throws DNetException {
		
		// this array containing cost variables is needed only if we have utility to maximize 
		ArrayList<PlaceVariable> costs = new ArrayList<PlaceVariable>();
		for (Place place : placeVariables.keySet()) {
			Map<String, VariableExpression> stringtoConstraintVar = new HashMap<String, VariableExpression>();
			if (placeVariables.get(place).size() > 0) {
				VariableExpression sumVariable = factory.sumTokens(placeVariables.get(place));
				stringtoConstraintVar.put(place.name(), sumVariable);
				logger.debug("For place " + place.name() + " the token variable names are " + stringtoConstraintVar
						+ " and the constraint is " + resourceToConstraint.get(place.name()));
				if (!hasUtility) {
					DnetConstraint costConstraint = resourceToConstraint.get(place.name()).evaluateN(stringtoConstraintVar);
					solver.addConstraint(costConstraint);
				} else {
					Utility cost = resourceToCost.get(place.name());
					PlaceVariable costVar = factory.createCostVariable(place.name());
					costs.add(costVar);
					solver.addConstraint(factory.createUtilityConstraint(costVar, cost.utility(), stringtoConstraintVar));
				}
			}
		}
		if (hasUtility) {
			VariableExpression sumCost = factory.sumTokens(costs);
			PlaceVariable bigCost = factory.createCostVariable("global");
			solver.addCostConstraint(bigCost, sumCost, uVar);
		}
	}

	/**************** End of Transitions *****************/


	/**************** Interface functions *****************/

	/**
	 * We assume that each resource provider has a place in the DNet with the same name.
	 * We also assume that the DNet is provided to the Allocator before the resource providers are specified.
	 * @throws DNetException 
	 */
	@Override
	public void addResource(ResourceProvider resource) {
		resources.add(resource);
		placeNameToResource.put(resource.name(), resource);
		try {
			if (!hasUtility) {
				this.specifyCost(resource.name(), resource.constraint());
			} else {
				this.specifyCost(resource.name(), resource.cost());
			}
		} catch (DNetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!this.dnet.nameToPlace.keySet().contains(resource.name())) {
			throw new BIPException("The resource provider " + resource + " does not have a corresponding place in the DNet.");
		}
	}
	
	/****************End of  Interface functions *****************/

}
