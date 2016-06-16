package org.bip.constraint;

public interface PlaceVariable extends VariableExpression {

	public void create(String name);
	public String variableName();
	
	public DnetConstraint domainConstraint();
}
