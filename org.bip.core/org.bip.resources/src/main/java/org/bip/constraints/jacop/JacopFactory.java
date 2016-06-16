package org.bip.constraints.jacop;

import java.util.ArrayList;

import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.PlaceVariable;
import org.bip.constraint.VariableExpression;
import org.jacop.constraints.And;
import org.jacop.constraints.Not;
import org.jacop.constraints.Or;
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

	@Override
	public VariableExpression createAddition(VariableExpression v1, VariableExpression v2) {
		IntVar res = new IntVar(store, 0, 1000);
		vars.add(res);
		store.impose(new XplusYeqZ(v1.jVar(), v2.jVar(), res));
		return new JacopPlaceVariable(res);
	}

	@Override
	public VariableExpression createSubtraction(VariableExpression v1, VariableExpression v2) {
		IntVar res = new IntVar(store, 0, 1000);
		vars.add(res);
		store.impose(new XplusYeqZ(res, v2.jVar(), v1.jVar()));
		return new JacopPlaceVariable(res);
	}

	@Override
	public VariableExpression createMultiplication(VariableExpression v1, VariableExpression v2) {
		IntVar res = new IntVar(store, 0, 1000);
		vars.add(res);
		store.impose(new XmulYeqZ(v1.jVar(), v2.jVar(), res));
		return new JacopPlaceVariable(res);
	}

	@Override
	public VariableExpression createDivision(VariableExpression v1, VariableExpression v2) {
		IntVar res = new IntVar(store);
		vars.add(res);
		store.impose(new XdivYeqZ(v1.jVar(), v2.jVar(), res));
		return new JacopPlaceVariable(res);
	}

	@Override
	public DnetConstraint createGreater(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XgtY(v1.jVar(), v2.jVar()));
	}

	@Override
	public DnetConstraint createGreaterOrEqual(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XgteqY(v1.jVar(), v2.jVar()));
	}

	@Override
	public DnetConstraint createLess(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XltY(v1.jVar(), v2.jVar()));
	}

	@Override
	public DnetConstraint createLessOrEqual(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XlteqY(v1.jVar(), v2.jVar()));
	}

	@Override
	public DnetConstraint createEqual(VariableExpression v1, VariableExpression v2) {
		return new JacopConstraint(new XeqY(v1.jVar(), v2.jVar()));
	}

	@Override
	public DnetConstraint and(DnetConstraint v1, DnetConstraint v2) {
		return new JacopConstraint(new And(v1.cstr(), v2.cstr()));
	}

	@Override
	public DnetConstraint or(DnetConstraint v1, DnetConstraint v2) {
		return new JacopConstraint(new Or(v1.cstr(), v2.cstr()));
	}

	@Override
	public DnetConstraint not(DnetConstraint v) {
		return new JacopConstraint(new Not(v.cstr()));
	}

	@Override
	public VariableExpression createNumber(String data) {
		int num = Integer.parseInt(data);
		IntVar res = new IntVar(store, data, num, num);
		//vars.add(res);
		store.impose(new XeqC(res, num));
		return new JacopPlaceVariable(res);
	}

	@Override
	public VariableExpression sumTokens(ArrayList<PlaceVariable> placeTokens) {

		if (placeTokens.size() == 1) {
			JacopPlaceVariable var = (JacopPlaceVariable) placeTokens.get(0);
			return var;
		}

		//JacopPlaceVariable var = (JacopPlaceVariable) placeTokens.get(0);
//		IntVar var =  placeTokens.get(0).jVar();
//		IntVar res = new IntVar(store, 0, 1000);
//		for (int i = 1; i < placeTokens.size(); i++) {
//
//			vars.add(res);
//			
//			JacopPlaceVariable nextVar = (JacopPlaceVariable) placeTokens.get(i);
//			store.impose(new XplusYeqZ(var, nextVar.jVar(), res));
//			var = res;
//		}

		ArrayList<IntVar> sumVars = new ArrayList<IntVar>();
		for (int i = 0; i < placeTokens.size(); i++) {
			JacopPlaceVariable nextVar = (JacopPlaceVariable) placeTokens.get(i);
			sumVars.add(nextVar.jVar());
		}
		IntVar sum = new IntVar(store, "tokensum", 0, 1000);
		vars.add(sum);
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

}
