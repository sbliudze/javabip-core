package org.bip.constraints.jacop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.PlaceVariable;
import org.bip.constraint.VariableExpression;
import org.bip.exceptions.BIPException;
import org.bip.resources.ConstraintNode;
import org.bip.resources.DNetException;
import org.jacop.constraints.And;
import org.jacop.constraints.Not;
import org.jacop.constraints.Or;
import org.jacop.constraints.PrimitiveConstraint;
import org.jacop.constraints.SumInt;
import org.jacop.constraints.XdivYeqZ;
import org.jacop.constraints.XeqC;
import org.jacop.constraints.XeqY;
import org.jacop.constraints.XgtY;
import org.jacop.constraints.XgteqY;
import org.jacop.constraints.XltY;
import org.jacop.constraints.XlteqY;
import org.jacop.constraints.XmulYeqZ;
import org.jacop.constraints.XplusYeqZ;
import org.jacop.core.IntVar;
import org.jacop.core.Store;

public class JacopFactory implements ExpressionCreator {

	Store store;
	ArrayList<IntVar> vars;

	public JacopFactory(Store store, ArrayList<IntVar> vars) {
		this.store = store;
		this.vars = vars;
	}

	private IntVar getIntVar(VariableExpression variable) {
		if (variable instanceof JacopPlaceVariable) {
			return ((JacopPlaceVariable) variable).intVar();
		}
		throw new BIPException("The variable " + variable + " does not belong to Jacop solver, it belongs to " + variable.getClass());
	}

	private PrimitiveConstraint getPrimitiveConstraint(DnetConstraint constraint) {
		if (constraint instanceof JacopConstraint) {
			return ((JacopConstraint) constraint).cstr();
		}
		throw new BIPException("The constraint " + constraint + " does not belong to Jacop solver, it belongs to " + constraint.getClass());
	}

	@Override
	public VariableExpression createAddition(VariableExpression v1, VariableExpression v2) {
		IntVar res = new IntVar(store, 0, 1000);
		vars.add(res);
		store.impose(new XplusYeqZ(getIntVar(v1), getIntVar(v2), res));
		return new JacopPlaceVariable(res);
	}

	@Override
	public VariableExpression createSubtraction(VariableExpression v1, VariableExpression v2) {
		IntVar res = new IntVar(store, 0, 1000);
		vars.add(res);
		store.impose(new XplusYeqZ(res, getIntVar(v2), getIntVar(v1)));
		return new JacopPlaceVariable(res);
	}

	@Override
	public VariableExpression createMultiplication(VariableExpression v1, VariableExpression v2) {
		IntVar res = new IntVar(store, 0, 1000);
		vars.add(res);
		store.impose(new XmulYeqZ(getIntVar(v1), getIntVar(v2), res));
		return new JacopPlaceVariable(res);
	}

	@Override
	public VariableExpression createDivision(VariableExpression v1, VariableExpression v2) {
		IntVar res = new IntVar(store);
		vars.add(res);
		store.impose(new XdivYeqZ(getIntVar(v1), getIntVar(v2), res));
		return new JacopPlaceVariable(res);
	}

	@Override
	public DnetConstraint createGreater(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XgtY(getIntVar(v1), getIntVar(v2)));
	}

	@Override
	public DnetConstraint createGreaterOrEqual(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XgteqY(getIntVar(v1), getIntVar(v2)));
	}

	@Override
	public DnetConstraint createLess(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XltY(getIntVar(v1), getIntVar(v2)));
	}

	@Override
	public DnetConstraint createLessOrEqual(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XlteqY(getIntVar(v1), getIntVar(v2)));
	}

	@Override
	public DnetConstraint createEqual(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XeqY(getIntVar(v1), getIntVar(v2)));
	}

	@Override
	public DnetConstraint and(DnetConstraint v1, DnetConstraint v2) {
		return new JacopConstraint(new And(getPrimitiveConstraint(v1), getPrimitiveConstraint(v2)));
	}

	@Override
	public DnetConstraint or(DnetConstraint v1, DnetConstraint v2) {
		return new JacopConstraint(new Or(getPrimitiveConstraint(v1), getPrimitiveConstraint(v2)));
	}

	@Override
	public DnetConstraint not(DnetConstraint v) {
		return new JacopConstraint(new Not(getPrimitiveConstraint(v)));
	}

	@Override
	public VariableExpression createNumber(String data) {
		int num = Integer.parseInt(data);
		IntVar res = new IntVar(store, data, num, num);
		store.impose(new XeqC(res, num));
		return new JacopPlaceVariable(res);
	}

	@Override
	public VariableExpression sumTokens(ArrayList<PlaceVariable> placeTokens) {

		if (placeTokens.size() == 1) {
			JacopPlaceVariable var = (JacopPlaceVariable) placeTokens.get(0);
			return var;
		}

		ArrayList<IntVar> sumVars = new ArrayList<IntVar>();
		for (int i = 0; i < placeTokens.size(); i++) {
			sumVars.add(getIntVar(placeTokens.get(i)));
		}
		IntVar sum = new IntVar(store, "tokensum", 0, 1000);
		//vars.add(sum);
		store.impose(new SumInt(store, sumVars, "==", sum));
		return new JacopPlaceVariable(sum);
	}

	@Override
	public PlaceVariable createVariable(String variableName) {
		IntVar res = new IntVar(store, variableName, 0, 1000);
		vars.add(res);
		return new JacopPlaceVariable(res);
	}

	public void reinit(Store store2, ArrayList<IntVar> vars2) {
		this.store = store2;
		this.vars = vars2;

	}

	@Override
	public PlaceVariable createCostVariable(String resourceName) {
		IntVar costVar = new IntVar(store, resourceName + "cost", 0, 1000);
		vars.add(costVar);
		return new JacopPlaceVariable(costVar);
	}

	@Override
	public PlaceVariable createUtilityVariable() {
		IntVar uVar = new IntVar(store, "utility", 0, 1000);
		vars.add(uVar);
		return new JacopPlaceVariable(uVar);
	}

	@Override
	public DnetConstraint createUtilityConstraint(VariableExpression uVar, HashMap<Integer, ConstraintNode> utility,
			Map<String, VariableExpression> nameToVariable) throws DNetException {
		PrimitiveConstraint previous = null;
		// we go through each expression of the utility and add it as an or-value
		JacopPlaceVariable var = (JacopPlaceVariable) uVar;
		for (Integer costValue : utility.keySet()) {
			PrimitiveConstraint costVarEqValue = new XeqC(var.intVar(), costValue);
			ConstraintNode valueConstraint = utility.get(costValue);
			valueConstraint.addFactory(this);
			PrimitiveConstraint valueAndItsConstraint = new And(costVarEqValue, ((JacopConstraint) valueConstraint.evaluateN(nameToVariable)).cstr());
			if (previous == null) {
				previous = valueAndItsConstraint;
				continue;
			}
			previous = new Or(previous, valueAndItsConstraint);
		}
		return new JacopConstraint(previous);
		
	}

}
