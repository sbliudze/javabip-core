package org.bip.constraints.z3;

import org.bip.constraint.DnetConstraint;
import org.bip.constraint.PlaceVariable;
import org.bip.resources.ContextProvider;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;

public class Z3PlaceVariable implements PlaceVariable {

	private ContextProvider ctxProvider;
	private IntExpr variable;
	private String name;
	Z3Factory factory;

	public Z3PlaceVariable(Context ctx, String name, ContextProvider ctxProvider) {
		this.factory = new Z3Factory(ctxProvider);
		this.name = name;
		this.ctxProvider = ctxProvider;
		this.variable = (IntExpr) ctxProvider.getContext().mkConst(ctx.mkSymbol(name),
				ctx.getIntSort());
	}
	
	public Z3PlaceVariable(IntExpr expr, ContextProvider ctxProvider) {
		this.variable = expr;
		this.ctxProvider = ctxProvider;
	}

	private IntExpr createIntVariable(Context ctx, String name) {
		return (IntExpr) ctx.mkConst(ctx.mkSymbol(name), ctx.getIntSort());
	}

	@Override
	public String variableName() {
		return variable.getFuncDecl().getName().toString();
	}

	public String tokenName() {
		String tokenName = variable.getFuncDecl().getName().toString();
		return tokenName.substring(tokenName.indexOf("-"));
	}

	@Override
	public void create(String name) {
		variable = createIntVariable(ctxProvider.getContext(), name);
	}

	@Override
	public Object value() {
		return null;
	}

	@Override
	public DnetConstraint domainConstraint() {
		return factory.creatNaturalConstraint(variable);
	}

	@Override
	public ArithExpr aExpr() {
		return variable;
	}
	
	public String toString () {
		return variable.toString();
	}

	@Override
	public Expr expr() {
		return variable;
	}

}
