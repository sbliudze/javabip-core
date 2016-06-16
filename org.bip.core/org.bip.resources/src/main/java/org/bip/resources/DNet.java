package org.bip.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bip.constraint.ConstraintSolver;
import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.PlaceVariable;
import org.bip.constraint.VariableExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DNet {

	private Logger logger = LoggerFactory.getLogger(DNet.class);
	
	public HashMap<ArrayList<String>, ArrayList<String>> resourceDependencies;
	
	private ArrayList<Place> places;
	private ArrayList<Transition> transitions;
	private HashMap<String, Transition> nameToTransition;
	public HashMap<String, ArrayList<String>> placeNameToTransitionNames;
	public HashMap<String, ArrayList<String>> transitionNameToPostplacesNames;
	public HashMap<String, ArrayList<String>> placeNameToPostplacesNames;
	public HashMap<String, Place> nameToPlace;
	private ArrayList<InhibitorArc> inhibitors;
	private ArrayList<Transition> firedTransitions;
	ConstraintSolver solver;
	private ExpressionCreator factory;
	
	public ConstraintNode request;
	
	/**************** Constructors area *****************/

	public DNet() {
		places = new ArrayList<Place>();
		transitions = new ArrayList<Transition>();
		inhibitors = new ArrayList<InhibitorArc>();
		nameToPlace = new HashMap<String, Place>();
		nameToTransition = new HashMap<String, Transition>();
		resourceDependencies = new HashMap<ArrayList<String>, ArrayList<String>>();
		placeNameToTransitionNames = new HashMap<String, ArrayList<String>>();
		transitionNameToPostplacesNames = new HashMap<String, ArrayList<String>>();
		placeNameToPostplacesNames = new  HashMap<String, ArrayList<String>>();
		firedTransitions = new ArrayList<Transition>();
	}
	
	public void checkIntegrity() {
		// check that for all the places there is a resource with the same name
		// all inhibitor arcs refer to existing transitions
		// conflict-freedom
	}
	
	public void reInit() {
		for (Transition transition : transitions)
			transition.reInit();
	}
	
	/************************************************/
	
	/**************** Creation area *****************/

	public void setFactory(ExpressionCreator factory) {
		this.factory = factory;
		for (Transition t: transitions) {
			t.setFactoryToContraint(factory);
		}
	}
	
	public Place addPlace(String placeName) {
		if (!nameToPlace.containsKey(placeName)) {
			Place place = new Place(placeName);
			this.places.add(place);
			nameToPlace.put(placeName, place);
			return place;
		}
		return nameToPlace.get(placeName);
	}

	public void addTransition(String transitionName, ArrayList<Place> inPlaces, ArrayList<Place> outPlaces) throws DNetException {
		createHelperStructures(transitionName, inPlaces, outPlaces);
		Transition tr = new Transition(transitionName, inPlaces, outPlaces);
		this.transitions.add(tr);
		this.nameToTransition.put(transitionName, tr);
	}

	private void createHelperStructures(String transitionName, ArrayList<Place> inPlaces, ArrayList<Place> outPlaces) throws DNetException {
		ArrayList<String> ins = new ArrayList<String>();

		ArrayList<String> outs = new ArrayList<String>();
		for (Place place : outPlaces) {
			outs.add(place.name());
			if (!places.contains(place)) {
				throw new DNetException("The postPlace " + place.name() + " of transition " + transitionName + " is not among the places of the DNet.");
			}
		}
		for (Place place : inPlaces) {
			ins.add(place.name());
			if (!placeNameToPostplacesNames.containsKey(place.name())) {
				placeNameToPostplacesNames.put(place.name(), outs);
			} else {
				placeNameToPostplacesNames.get(place.name()).addAll(outs);
			}
			
			if (!places.contains(place)) {
				throw new DNetException("The prePlace \"" + place.name() + "\" of transition \"" + transitionName + "\" is not among the places of the DNet.");
			}
			
			if (!placeNameToTransitionNames.containsKey(place.name())) {
				placeNameToTransitionNames.put(place.name(), new ArrayList<String>(Arrays.asList(transitionName)));
			} else {
				placeNameToTransitionNames.get(place.name()).add(transitionName);
			}
		}
		resourceDependencies.put(ins, outs);
		transitionNameToPostplacesNames.put(transitionName, outs);
	}
	
	public void addTransition(String transitionName, ArrayList<Place> inPlaces, ArrayList<Place> outPlaces, Constraint constraint) throws DNetException {
		constraint.addFactory(factory);
		createHelperStructures(transitionName, inPlaces, outPlaces);
		Transition tr = new Transition(transitionName, inPlaces, outPlaces, constraint);
		this.transitions.add(tr);
		this.nameToTransition.put(transitionName, tr);
	}

	public void addInhibitor(String placeName, String transitionName, ArrayList<String> refers) {
		Transition tr = this.nameToTransition.get(transitionName);
		Place place = this.nameToPlace.get(placeName);
		InhibitorArc inh = new InhibitorArc(place, tr, refers);
		tr.addInhibitor(inh);
		inhibitors.add(inh);
	}
	
	
	/************************************************/
	
	/******************* Running *******************/

	public ArrayList<DnetConstraint> run(HashMap<Place, ArrayList<PlaceVariable>> placeVariables, HashMap<Place, ArrayList<Transition>> placeTokens) throws DNetException {
		firedTransitions.clear();
		ArrayList<Transition> disabled = new ArrayList<Transition>();
		findEnabledAndFireWithoutConstraints(placeVariables, placeTokens, disabled);
		return getConstraints(placeVariables, placeTokens);
	}
	
	private void findEnabledAndFireWithoutConstraints(HashMap<Place, ArrayList<PlaceVariable>> placeVariables, HashMap<Place, ArrayList<Transition>> placeTokens,
		 ArrayList<Transition> disabled) throws DNetException {
		for (Transition transition : transitions) {
			if (!disabled.contains(transition)) {
				if (transition.enabled(placeTokens)) {
					transition.disable();
					firedTransitions.add(transition);
					disabled.add(transition);
					for (Place place : transition.postplaces()) {
						// add a new token
						placeTokens.get(place).add(transition);

						// add a new variable
						String variableName = createVariableName(place, transition.name());

						PlaceVariable var = factory.createVariable(variableName);
						placeVariables.get(place).add(var);
					}
					logger.debug("After firing of " + transition.name() + " the tokens are: " + placeTokens);
					findEnabledAndFireWithoutConstraints(placeVariables, placeTokens,  disabled);
				}
			}
		}
	}
	
	private ArrayList<DnetConstraint> getConstraints(
			HashMap<Place, ArrayList<PlaceVariable>> placeVariables,
			HashMap<Place, ArrayList<Transition>> placeTokens)
			throws DNetException {
		ArrayList<DnetConstraint> dependencyConstraints = new ArrayList<DnetConstraint>();
		for (Transition transition : firedTransitions) {
			String tName = transition.name();
			Map<String, VariableExpression> stringtoConstraintVar = new HashMap<String, VariableExpression>();
			for (Place place : transition.postplaces()) {
				for (PlaceVariable var : placeVariables.get(place)) {
					String tokenName = var.variableName();
					if (tName.equals(tokenName.substring(tokenName.length()-tName.length()))) {
						stringtoConstraintVar.put(place.name(), var);
						break;
					}
				}
			}
			for (Place place : transition.preplaces()) {
				// if there is only one token variable
				if (placeVariables.get(place).size() < 1) {
					throw new DNetException(
							"There are no place variables in place "
									+ place.name() + ", however, transition "
									+ transition.name() + " has fired.");
				}
				
				VariableExpression  placeSum = factory.sumTokens(placeVariables.get(place));

				for (int i = 1; i < placeVariables.get(place).size(); i++) {

					// TODO think about constraints on token variables which
					// should be >=0
					// in general, this might not always be the case that each
					// variable is positive.
					
					// for z3 we add that it should be >0
					// for jacop, it is already implied by variable definition -> we add null 
					dependencyConstraints.add(placeVariables.get(place).get(i).domainConstraint());
				}
				stringtoConstraintVar.put(place.name(), placeSum);
			}
			DnetConstraint expr = transition.constraintN(stringtoConstraintVar);
			dependencyConstraints.add(expr);
		}
		return dependencyConstraints;
	}

	private String createVariableName(Place place, String transitionName) {
		return place.name() + "-" + transitionName;
	}

	
	/************************************************/

	/******************* Getters ********************/

	public ArrayList<Place> places() {
		return places;
	}

	public Place getPlace(String placeName) {
		return nameToPlace.get(placeName);
	}
	
	public ArrayList<String> getDependentResources(String resource) {
		ArrayList<String> result = new ArrayList<String>();
		for (ArrayList<String> inResources : resourceDependencies.keySet()) {
			if (inResources.contains(resource))
				result.addAll(resourceDependencies.get(inResources));
		}
		return result;
	}

	/************************************************/
	
	public void print() {
		System.out.println("DNet consists of: ");
		System.out.println(places.size() + " places: ");
		for (Place place : places) {
			System.out.print(place + " ");
		}
		System.out.println();
		System.out.println(transitions.size() + " transitions: ");
		for (Transition tr : transitions) {
			System.out.println(tr + " ");
		}
	}

}
