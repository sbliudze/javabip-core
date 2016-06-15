package org.bip.constraint;

import com.microsoft.z3.BoolExpr;

public interface DnetConstraint {

	BoolExpr z3expr();
}
