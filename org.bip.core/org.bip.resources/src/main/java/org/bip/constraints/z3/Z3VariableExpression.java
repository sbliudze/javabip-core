package org.bip.constraints.z3;

import org.bip.constraint.VariableExpression;
import org.jacop.core.IntVar;

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

	@Override
	public ArithExpr aExpr() {
		return aExpr;
	}
	
	public String toString() {
		if (aExpr==null)
		return expr.toString();
		return aExpr.toString();
	}

	@Override
	public Expr expr() {
		return expr;
	}

	@Override
	public IntVar jVar() {
		// TODO Auto-generated method stub
		return null;
	}

}
