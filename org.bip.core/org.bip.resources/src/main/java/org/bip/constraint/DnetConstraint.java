package org.bip.constraint;

import org.jacop.constraints.PrimitiveConstraint;

import com.microsoft.z3.BoolExpr;

public interface DnetConstraint {

	BoolExpr z3expr();
	PrimitiveConstraint cstr() ;
}
