package org.bip.constraints.z3;

import org.bip.constraint.DnetConstraint;
import org.jacop.constraints.PrimitiveConstraint;

import com.microsoft.z3.BoolExpr;

public class Z3BooleanConstraint implements DnetConstraint {

	BoolExpr constraint;

	public Z3BooleanConstraint(BoolExpr boolExpr) {
		this.constraint = boolExpr;
	}

	protected BoolExpr z3expr() {
		return constraint;
	}

}
