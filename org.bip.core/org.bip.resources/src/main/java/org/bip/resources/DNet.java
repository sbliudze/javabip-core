package org.bip.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

public class DNet implements ContextProvider {

	private ArrayList<Place> places;
	private ArrayList<Transition> transitions;
	private HashMap<String, Transition> nameToTransition;
	public HashMap<String, Place> nameToPlace;
	private ArrayList<InhibitorArc> inhibitors;
	Context ctx;

	public ConstraintNode request;
	
	/* do something with the context */

	// ctx.dispose();

	public DNet() {
		places = new ArrayList<Place>();
		transitions = new ArrayList<Transition>();
		inhibitors = new ArrayList<InhibitorArc>();
		nameToPlace = new HashMap<String, Place>();
		nameToTransition = new HashMap<String, Transition>();
	}

	public void setContext(Context context) {
		this.ctx = context;
	}
	
	
	public void checkIntegrity() {
		// check that for all the places there is a resource with the same name
		// all inhibitor arcs refer to existing transitions
		// conflict-freedom
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
		for (Place place : inPlaces) {
			if (!places.contains(place)) {
				throw new DNetException("The prePlace \"" + place.name() + "\" of transition \"" + transitionName + "\" is not among the places of the DNet.");
			}
		}
		for (Place place : outPlaces) {
			if (!places.contains(place)) {
				throw new DNetException("The postPlace " + place.name() + " of transition " + transitionName + "is not among the places of the DNet.");
			}
		}

		Transition tr = new Transition(transitionName, inPlaces, outPlaces);
		this.transitions.add(tr);
		this.nameToTransition.put(transitionName, tr);
	}

	public void addTransition(String transitionName, ArrayList<Place> inPlaces, ArrayList<Place> outPlaces, Constraint constraint) {
		constraint.addContext(ctx);
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

	public ArrayList<BoolExpr> run(HashMap<Place, ArrayList<IntExpr>> placeVariables, HashMap<Place, ArrayList<Transition>> placeTokens) throws DNetException {
		ArrayList<BoolExpr> dependencyConstraints = new ArrayList<BoolExpr>();
		
		ArrayList<Transition> disabled = new ArrayList<Transition>();

		// TODO make terminating run
		return findEnabledAndFire(placeVariables, placeTokens, dependencyConstraints, disabled);
		//disabled.clear();
		//return dependencyConstraints;
	}

	//TODO check it works correctly
	private ArrayList<BoolExpr> findEnabledAndFire(HashMap<Place, ArrayList<IntExpr>> placeVariables, HashMap<Place, ArrayList<Transition>> placeTokens,
			ArrayList<BoolExpr> dependencyConstraints, ArrayList<Transition> disabled) throws DNetException {
		for (Transition transition : transitions) {
			if (!disabled.contains(transition)) {
				if (transition.enabled(placeTokens)) {
					System.out.println("Transition " + transition.name() + " enabled with tokens " + placeTokens);
					transition.disable();
					disabled.add(transition);
					Map<String, ArithExpr> stringtoConstraintVar = new HashMap<String, ArithExpr>();
					for (Place place : transition.postplaces()) {
						placeTokens.get(place).add(transition);

						// add a new token
						placeTokens.get(place).add(transition);

						// add a new variable
						String variableName = createVariableName(place, transition.name());

						IntExpr var = createIntVariable(ctx, variableName);
						placeVariables.get(place).add(var);

						stringtoConstraintVar.put(place.name(), var);

						// TODO if a postplace is in the preplaces -?
					}

					for (Place place : transition.preplaces()) {
						// if there is only one token variable
						if (placeVariables.get(place).size() < 1) {
							throw new DNetException("There are no place variables in place " + place.name() + ", however, transition " + transition.name()
									+ " has fired.");
						}
						ArithExpr placeSum = placeVariables.get(place).get(0);

						for (int i = 1; i < placeVariables.get(place).size(); i++) {
							placeSum = getContext().mkAdd(placeSum, placeVariables.get(place).get(i));
						}
						stringtoConstraintVar.put(place.name(), placeSum);
					}

					BoolExpr expr = transition.constraint(stringtoConstraintVar);
					dependencyConstraints.add(expr);
					System.out.println("After firing the tokens are: " + placeTokens);
					return findEnabledAndFire(placeVariables, placeTokens, dependencyConstraints, disabled);
				}
			}
		}
		return dependencyConstraints;
	}

	private String createVariableName(Place place, String transitionName) {
		return place.name() + "-" + transitionName;
	}

	private IntExpr createIntVariable(Context ctx, String name) {
		return (IntExpr) ctx.mkConst(ctx.mkSymbol(name), ctx.getIntSort());
	}
	
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

	@Override
	public Context getContext() {
		return ctx;
	}
	
	public ArrayList<Place> places() {
		return places;
	}
}
