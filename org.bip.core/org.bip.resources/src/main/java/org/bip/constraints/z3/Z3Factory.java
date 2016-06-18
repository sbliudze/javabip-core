package org.bip.constraints.z3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.PlaceVariable;
import org.bip.constraint.VariableExpression;
import org.bip.constraints.jacop.JacopConstraint;
import org.bip.constraints.jacop.JacopPlaceVariable;
import org.bip.exceptions.BIPException;
import org.bip.resources.ConstraintNode;
import org.bip.resources.ContextProvider;
import org.bip.resources.DNetException;
import org.jacop.constraints.PrimitiveConstraint;
import org.jacop.core.IntVar;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

public class Z3Factory implements ExpressionCreator {

	private ContextProvider provider;

	public Z3Factory(ContextProvider ctxProvider) {
		provider = ctxProvider;
	}

	private ArithExpr getArithExpr(VariableExpression variable) {
		if (variable instanceof Z3PlaceVariable) {
			return ((Z3PlaceVariable) variable).arithExpr();
		}
		if (variable instanceof Z3VariableExpression) {
			return ((Z3VariableExpression) variable).arithExpr();
		}
		throw new BIPException("The variable " + variable + " does not belong to z3 solver, it belongs to " + variable.getClass());
	}

	private BoolExpr getBoolExpr(DnetConstraint constraint) {
		if (constraint instanceof Z3BooleanConstraint) {
			return ((Z3BooleanConstraint) constraint).z3expr();
		}
		throw new BIPException("The constraint " + constraint + " does not belong to z3 solver, it belongs to " + constraint.getClass());
	}

	@Override
	public VariableExpression createAddition(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkAdd(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public VariableExpression createSubtraction(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkSub(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public VariableExpression createMultiplication(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkMul(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public VariableExpression createDivision(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3VariableExpression(ctx.mkDiv(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public DnetConstraint createGreater(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint(ctx.mkGt(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public DnetConstraint createGreaterOrEqual(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint(ctx.mkGe(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public DnetConstraint createLess(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint(ctx.mkLt(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public DnetConstraint createLessOrEqual(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint(ctx.mkLe(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public DnetConstraint createEqual(VariableExpression v1, VariableExpression v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint(ctx.mkEq(getArithExpr(v1), getArithExpr(v2)));
	}

	@Override
	public DnetConstraint and(DnetConstraint v1, DnetConstraint v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint(ctx.mkAnd(getBoolExpr(v1), getBoolExpr(v2)));
	}

	@Override
	public DnetConstraint or(DnetConstraint v1, DnetConstraint v2) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint(ctx.mkOr(getBoolExpr(v1), getBoolExpr(v2)));
	}

	@Override
	public DnetConstraint not(DnetConstraint v) {
		Context ctx = provider.getContext();
		return new Z3BooleanConstraint(ctx.mkNot(getBoolExpr(v)));
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

	public VariableExpression sumTokens(ArrayList<PlaceVariable> placeTokens) {
		ArithExpr placeSum = getArithExpr(placeTokens.get(0));
		Context ctx = provider.getContext();
		for (int i = 1; i < placeTokens.size(); i++) {
			placeSum = ctx.mkAdd(placeSum, getArithExpr(placeTokens.get(i)));
		}
		return new Z3VariableExpression(placeSum);
	}

	@Override
	public PlaceVariable createCostVariable(String resourceName) {
		// cost functionality is not implemented for z3
		return null;
	}

	@Override
	public PlaceVariable createUtilityVariable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DnetConstraint createUtilityConstraint(VariableExpression uVar, HashMap<Integer, ConstraintNode> utility,
			Map<String, VariableExpression> nameToVariable) throws DNetException {
		// TODO Auto-generated method stub
		return null;
	}

}
