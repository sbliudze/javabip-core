package org.bip.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.VariableExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;

public class Transition {

	private String transitionName;
	private ArrayList<Place> inPlaces;
	private ArrayList<Place> outPlaces;
	private Constraint constraint;
//	private ConstraintNode constraintNode;
	private ArrayList<Place> inhibitorPlaces;
	private ArrayList<InhibitorArc> inhibitorArcs;
	private HashMap<Place, InhibitorArc> inhibitors;
	
	private boolean fired = false;
	
	private Logger logger = LoggerFactory.getLogger(Transition.class);

	public Transition() {
		this.inPlaces = new ArrayList<Place>();
		this.outPlaces = new ArrayList<Place>();
		inhibitorPlaces = new ArrayList<Place>();
		inhibitorArcs = new ArrayList<InhibitorArc>();
		inhibitors = new HashMap<Place, InhibitorArc>();
	}
	
	public Transition(String transitionName) {
		this();
		this.transitionName = transitionName;
	}

	public Transition(String transitionName, ArrayList<Place> inPlaces, ArrayList<Place> outPlaces) {
		this(transitionName);
		this.inPlaces = inPlaces;
		for (Place place : this.inPlaces) {
			place.addOutgTransition(this);
		}
		this.outPlaces = outPlaces;
		for (Place place : this.outPlaces) {
			place.addIncTransition(this);
		}
	}

	public Transition(String transitionName, ArrayList<Place> inPlaces, ArrayList<Place> outPlaces, Constraint constraint) {
		this(transitionName, inPlaces, outPlaces);
		this.constraint = constraint;
	}

	public void addInhibitor(InhibitorArc inhibitor) {
		inhibitorArcs.add(inhibitor);
		inhibitorPlaces.add(inhibitor.place());
		inhibitors.put(inhibitor.place(), inhibitor);
	}

//	public void addConstraint(ConstraintNode node) {
//		this.constraintNode = node;
//	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Transition=(" + transitionName);
		result.append(" FROM ");
		for (Place place : inPlaces) {
			result.append(place + " ");
		}
		result.append(" TO ");
		for (Place place : outPlaces) {
			result.append(place + " ");
		}
		if (constraint != null) {
			result.append(", with " + constraint.toString());
		}
		if (!inhibitorArcs.isEmpty())
		{
			result.append(", with inhibitor arcs  ");
			for (InhibitorArc inh : inhibitorArcs) {
				result.append(inh + " ");
			}
		}
		result.append(")");
		return result.toString();
	}

	public String name() {
		return this.transitionName;
	}
	
	public boolean enabled(HashMap<Place, ArrayList<Transition>> placeTokens) {
		if (fired) {
			return false;
		}
		for (Place place : inPlaces) {
			logger.debug(place + ": tokens - " + placeTokens + " \n" + placeTokens.get(place) + "\n");

			if (placeTokens.get(place).isEmpty()) {
				// if (!place.hasTokens()) {
				return false;
			}
		}
		for (Place inhPlace : inhibitorPlaces) {
			// if one of the inhibitor places has tokens
			if (!placeTokens.get(inhPlace).isEmpty()) {
				// check which tokens they are
				InhibitorArc arc = inhibitors.get(inhPlace);
				for (Transition tr : arc.referredTransitions()) {
					if (placeTokens.get(inhPlace).contains(tr)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void disable() {
		this.fired = true;
	}

	public ArrayList<Place> postplaces() {
		return outPlaces;
	}

	public ArrayList<Place> preplaces() {
		return inPlaces;
	}
	
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (!(o instanceof Transition)) {
			return false;
		}

		Transition compareTo = (Transition) o;

		if (!this.name().equals(compareTo.name()))
			return false;

		return true;
	}

	public DnetConstraint constraintN(Map<String, VariableExpression> stringToConstraintVar) throws DNetException {
		return this.constraint.constraintNode.evaluateN(stringToConstraintVar);
	}

	public void reInit() {
		this.fired = false;
	}

	public void setFactoryToConstraint(ExpressionCreator factory) {
		this.constraint.addFactory(factory);
		
	}
}
