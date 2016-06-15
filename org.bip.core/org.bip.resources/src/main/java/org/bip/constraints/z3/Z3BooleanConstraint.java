package org.bip.constraints.z3;

import org.bip.constraint.DnetConstraint;
import org.jacop.constraints.PrimitiveConstraint;

import com.microsoft.z3.BoolExpr;

public class Z3BooleanConstraint implements DnetConstraint {
	
	BoolExpr constraint;
	
	public Z3BooleanConstraint() {
		
	}

	public Z3BooleanConstraint(BoolExpr mkGe) {
		this.constraint = mkGe;
	}

	@Override
	public BoolExpr z3expr() {
		return constraint;
	}

	@Override
	public PrimitiveConstraint cstr() {
		return null;
	}

}
