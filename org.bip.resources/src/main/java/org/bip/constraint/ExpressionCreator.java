package org.bip.constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bip.resources.ConstraintNode;
import org.bip.resources.DNetException;

public interface ExpressionCreator {
	VariableExpression createAddition(VariableExpression v1, VariableExpression v2);

	VariableExpression createSubtraction(VariableExpression v1, VariableExpression v2);

	VariableExpression createMultiplication(VariableExpression v1, VariableExpression v2);

	VariableExpression createDivision(VariableExpression v1, VariableExpression v2);

	DnetConstraint createGreater(VariableExpression v1, VariableExpression v2);

	DnetConstraint createGreaterOrEqual(VariableExpression v1, VariableExpression v2);

	DnetConstraint createLess(VariableExpression v1, VariableExpression v2);

	DnetConstraint createLessOrEqual(VariableExpression v1, VariableExpression v2);

	DnetConstraint createEqual(VariableExpression v1, VariableExpression v2);

	DnetConstraint and(DnetConstraint v1, DnetConstraint v2);

	DnetConstraint or(DnetConstraint v1, DnetConstraint v2);

	DnetConstraint not(DnetConstraint v);

	VariableExpression createNumber(String data);

	PlaceVariable createVariable(String variableName);

	VariableExpression sumTokens(ArrayList<PlaceVariable> arrayList);

	PlaceVariable createCostVariable(String resourceName);

	PlaceVariable createUtilityVariable();

	DnetConstraint createUtilityConstraint(VariableExpression uVar, HashMap<Integer, ConstraintNode> utility, Map<String, VariableExpression> nameToVariable) throws DNetException;
}
