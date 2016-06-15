package org.bip.constraint;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Expr;

public interface VariableExpression {

	public ArithExpr aExpr();
	public Expr expr();
}
