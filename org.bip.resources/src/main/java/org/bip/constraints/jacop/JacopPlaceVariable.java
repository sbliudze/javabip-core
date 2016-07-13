package org.bip.constraints.jacop;

import org.bip.constraint.DnetConstraint;
import org.bip.constraint.PlaceVariable;
import org.jacop.core.IntDomain;
import org.jacop.core.IntVar;
import org.jacop.core.Store;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Expr;

public class JacopPlaceVariable implements PlaceVariable {

	private IntVar variable; 
	JacopFactory factory;
	private Store store;
	private String name;
	
	public JacopPlaceVariable (Store store) {
		this.store = store;
	}
	
	public JacopPlaceVariable (IntVar var) {
		this.variable = var;
		name = var.id;
	}

	@Override
	public void create(String name) {
		this.name = name;
		variable = new IntVar(store, name, 0, 1000);

	}

	@Override
	public String variableName() {
		return name;
	}

	@Override
	public DnetConstraint domainConstraint() {
		// variable is already defined with its domain, hence we do not need to add a new constraint
		return null;
	}

	public IntVar intVar() {
		return variable;
	}

}
