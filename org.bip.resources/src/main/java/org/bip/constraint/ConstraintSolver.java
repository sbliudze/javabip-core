package org.bip.constraint;

public interface ConstraintSolver {

	public void addConstraint(DnetConstraint constraint);

	public boolean isSolvable(boolean hasUtility);

	public ResourceAllocation getAllocation();

	public ExpressionCreator expressionCreator();

	public void newCycle();

	public void addCostConstraint(PlaceVariable bigCost, VariableExpression sumCost, PlaceVariable uVar);

}
