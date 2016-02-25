package org.bip.resources;

public interface ConstraintSolver {
	
	public void addConstraint();
	public boolean isSolvable();
	public Object getAllocation();

}
