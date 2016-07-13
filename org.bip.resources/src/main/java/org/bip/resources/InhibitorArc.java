package org.bip.resources;

import java.util.ArrayList;
import java.util.Map;

public class InhibitorArc {

	private Place sourcePlace;
	private Transition targetTransition;

	private ArrayList<Transition> refersTo;
	private ArrayList<String> refersToS;

	public InhibitorArc(Place place, Transition transition, ArrayList<String> refers) {
		this.sourcePlace = place;
		this.targetTransition = transition;
		this.refersToS = refers;
		this.refersTo = new ArrayList<Transition>();
	}

	public void configureTrReference(Map<String, Transition> nameToTransition) throws DNetException {
		for (String refTransition : refersToS) {
			if (nameToTransition.containsKey(refTransition)) {
				refersTo.add(nameToTransition.get(refTransition));
			} else {
				throw new DNetException("No transition found for inhibitor to transition " + targetTransition.name() + " referring to tokens from transition "
						+ refTransition + " in place " + sourcePlace + ".");
			}
		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Inhibitor arc=(");
		result.append(" FROM ");
		result.append(sourcePlace + " ");
		result.append(" TO ");
		result.append(targetTransition.name() + " ");
		result.append(" REFERS TO ");
		for (String inh : refersToS) {
			result.append(inh + " ");
		}
		result.append(")");
		return result.toString();
	}

	public Place place() {
		return sourcePlace;
	}
	
	public ArrayList<Transition> referredTransitions() {
		return refersTo;
	}
}
