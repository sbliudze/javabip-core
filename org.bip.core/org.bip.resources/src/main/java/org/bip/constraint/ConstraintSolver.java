package org.bip.constraint;

public interface ConstraintSolver {

	public void addConstraint(DnetConstraint constraint);

	public boolean isSolvable();

	public ResourceAllocation getAllocation();

	public ExpressionCreator expressionCreator();

	public void newCycle();

}
