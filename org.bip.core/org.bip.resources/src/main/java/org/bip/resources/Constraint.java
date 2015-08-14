package org.bip.resources;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;

public class Constraint {

	ConstraintNode constraintNode;
	private ContextProvider contextprovider;

	public Constraint(ConstraintNode constraintNode, ContextProvider contextprovider) {
		this.constraintNode = constraintNode;
		this.contextprovider = contextprovider;
//		if (context != null) {
//			this.constraintNode.addContext(context);
//		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Constraint=" + constraintNode);
		try {
			//Solver s = contextprovider.getContext().mkSolver();
			//s.add(constraintNode.evaluate(null));
			//s.check();
			//result.append(" evaluates to " + s.check());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}

	public void addContext(Context ctx) {
//		this.context = ctx;
//		if (this.constraintNode != null) {
//			this.constraintNode.addContext(ctx);
//		}
	}
}
