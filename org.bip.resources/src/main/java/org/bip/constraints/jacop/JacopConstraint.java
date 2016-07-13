package org.bip.constraints.jacop;

import org.bip.constraint.DnetConstraint;
import org.jacop.constraints.PrimitiveConstraint;

public class JacopConstraint implements DnetConstraint {

	private PrimitiveConstraint cstr;

	public JacopConstraint(PrimitiveConstraint constraint) {
		this.cstr = constraint;
	}

	public PrimitiveConstraint cstr() {
		return cstr;
	}
	
	public String toString() {
		return cstr.toString();
	}

}
