package org.bip.resources;

import java.util.ArrayList;

public class Place {

	private String name;
	public ConstraintNode cost;
	private ArrayList<Transition> incomingTr;
	private ArrayList<Transition> outgoingTr;

	private ArrayList<Transition> tokenVariables;

	public Place(String placeName) {
		this.name = placeName;
		incomingTr = new ArrayList<Transition>();
		outgoingTr = new ArrayList<Transition>();
		tokenVariables = new ArrayList<Transition>();
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Place=" + name);
		return result.toString();
	}

	public void setCost(ConstraintNode cost) {
		this.cost = cost;
		// cost.print();
	}

	public void addIncTransition(Transition transition) {
		incomingTr.add(transition);
	}

	public void addOutgTransition(Transition transition) {
		outgoingTr.add(transition);
	}

	public boolean hasTokens() {
		return !tokenVariables.isEmpty();
	}

	public void addToken(Transition transition) {
		tokenVariables.add(transition);
	}

	public ArrayList<Transition> tokens() {
		return tokenVariables;
	}

	public String name() {
		return name;
	}

	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (!(o instanceof Place)) {
			return false;
		}

		Place compareTo = (Place) o;

		if (!this.name().equals(compareTo.name()))
			return false;

		return true;
	}

	public int hashCode() {
		int result = name.hashCode();
		// result = 31 * result + specType.hashCode();
		// result = 31 * result + portType.hashCode();
		return result;
	}
}
