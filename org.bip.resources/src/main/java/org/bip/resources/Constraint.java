package org.bip.resources;

import org.bip.constraint.ExpressionCreator;

public class Constraint {

	ConstraintNode constraintNode;

	public Constraint(ConstraintNode constraintNode) {
		this.constraintNode = constraintNode;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Constraint=" + constraintNode);
		return result.toString();
	}

	public void addFactory(ExpressionCreator factory) {
		if (this.constraintNode != null) {
			this.constraintNode.addFactory(factory);
		}

	}
}
