package org.bip.constraints.jacop;

import org.bip.constraint.DnetConstraint;
import org.jacop.constraints.Or;
import org.jacop.constraints.PrimitiveConstraint;

import com.microsoft.z3.BoolExpr;

public class JacopConstraint implements DnetConstraint {

	private PrimitiveConstraint cstr;
	
	public JacopConstraint(PrimitiveConstraint constraint) {
		this.cstr = constraint;
	}

	@Override
	public BoolExpr z3expr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public PrimitiveConstraint cstr() {
		return cstr;
	}

}
