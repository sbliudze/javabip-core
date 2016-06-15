package org.bip.constraints.z3;

import java.util.ArrayList;

import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.PlaceVariable;
import org.bip.constraint.VariableExpression;
import org.bip.resources.ContextProvider;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

public class Z3Factory implements ExpressionCreator {

	private ContextProvider provider;

	public Z3Factory(ContextProvider ctxProvider) {
		provider = ctxProvider;
	}

	@Override
	public VariableExpression createAddition(VariableExpression v1,
			VariableExpression v2) {Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkAdd(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public VariableExpression createSubtraction(VariableExpression v1,
			VariableExpression v2) {Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkSub(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public VariableExpression createMultiplication(VariableExpression v1,
			VariableExpression v2) {Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkMul(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public VariableExpression createDivision(VariableExpression v1,
			VariableExpression v2) {Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkDiv(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public DnetConstraint createGreater(VariableExpression v1,
			VariableExpression v2) {Context ctx = provider.getContext();
		return new Z3BooleanConstraint( ctx.mkGt(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public DnetConstraint createGreaterOrEqual(VariableExpression v1,
			VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint( ctx.mkGe(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public DnetConstraint createLess(VariableExpression v1,
			VariableExpression v2) {Context ctx = provider.getContext();
		return new Z3BooleanConstraint( ctx.mkLt(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public DnetConstraint createLessOrEqual(VariableExpression v1,
			VariableExpression v2) {Context ctx = provider.getContext();
		return new Z3BooleanConstraint( ctx.mkLe(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public DnetConstraint createEqual(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint( ctx.
				mkEq(v1.aExpr(), v2.aExpr()));
	}

	@Override
	public DnetConstraint and(DnetConstraint v1, DnetConstraint v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint( ctx.
				mkAnd(v1.z3expr(), v2.z3expr()));
	}

	@Override
	public DnetConstraint or(DnetConstraint v1, DnetConstraint v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint( ctx.
				mkOr(v1.z3expr(), v2.z3expr()));
	}

	@Override
	public DnetConstraint not(DnetConstraint v) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint( ctx.
				mkNot(v.z3expr()));
	}
	
	@Override
	public VariableExpression createNumber(String data) {
		Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkInt(Integer.parseInt(data)));
	}

	public PlaceVariable createVariable(String name) {
		return new Z3PlaceVariable(provider.getContext(), name, provider);
	}

	public DnetConstraint creatNaturalConstraint(IntExpr variable) {
		return new Z3BooleanConstraint(provider.getContext().mkGe(variable, provider.getContext().mkInt(0)));
	}

//	public PlaceVariable createInitialVariable(String placeName) {
//		Context ctx = provider.getContext();
//		String name = placeName + "-*";
//		IntExpr e = (IntExpr) ctx.mkConst(ctx.mkSymbol(name), ctx.getIntSort());
//		return new Z3PlaceVariable(e, provider);
//	}

	public VariableExpression sumTokens(ArrayList<PlaceVariable> placeTokens) {
		Z3PlaceVariable var = (Z3PlaceVariable)placeTokens.get(0);
		ArithExpr placeSum = var.aExpr();
		Context ctx = provider.getContext();
		for (int i = 1; i < placeTokens.size(); i++) {
			Z3PlaceVariable nextVar = (Z3PlaceVariable) placeTokens.get(i);
			placeSum = ctx.mkAdd(placeSum,
					nextVar.aExpr());
		}
		return new Z3VariableExpression(placeSum);
	}

	
}
