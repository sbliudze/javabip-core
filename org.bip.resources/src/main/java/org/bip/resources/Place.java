package org.bip.resources;

import java.util.ArrayList;

public class Place {

	private String name;
	public ConstraintNode cost;
	private ArrayList<Transition> incomingTransitions;
	private ArrayList<Transition> outgoingTransitions;


	public Place(String placeName) {
		this.name = placeName;
		incomingTransitions = new ArrayList<Transition>();
		outgoingTransitions = new ArrayList<Transition>();
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Place=" + name);
		return result.toString();
	}

	public void setCost(ConstraintNode cost) {
		this.cost = cost;
	}

	public void addIncTransition(Transition transition) {
		incomingTransitions.add(transition);
	}

	public void addOutgTransition(Transition transition) {
		outgoingTransitions.add(transition);
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
