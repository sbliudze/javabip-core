package org.bip.constraints.z3;

import org.bip.constraint.VariableExpression;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Expr;

public class Z3VariableExpression implements VariableExpression {

	private ArithExpr aExpr;
	private Expr expr;
	
	public Z3VariableExpression(ArithExpr placeSum) {
		this.aExpr = placeSum;
	}

	public Z3VariableExpression(Expr expr2) {
		this.expr = expr2;
	}

	protected ArithExpr arithExpr() {
		return aExpr;
	}
	
	public String toString() {
		if (aExpr == null)
			return expr.toString();
		return aExpr.toString();
	}

}
