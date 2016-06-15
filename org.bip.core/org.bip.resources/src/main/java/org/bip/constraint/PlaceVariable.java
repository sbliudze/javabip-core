package org.bip.constraint;

public interface PlaceVariable extends VariableExpression {

	public void create(String name);
	public Object value();
	public String variableName();
	
	public DnetConstraint domainConstraint();
}
