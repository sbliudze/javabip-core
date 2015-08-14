package org.bip.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

public class ConstraintNode {

	// if there is only one child, by convention it is added only as a right child
	public ConstraintNode rightChild;
	public ConstraintNode leftChild;
	public ConstraintNode parent;
	public String data;
	
	//private HashMap<String, IntExpr> stringToConstraintVar;
	
	private ContextProvider ctxProvider;

	private final String nameMatch = "[a-zA-Z][a-zA-Z0-9]*";
	
	
	public ConstraintNode(String value) {
		this.data = value;
		if (parent != null)
			this.attachToRight(parent);
	}
	
	public void setContextProvider(ContextProvider ctxProvider) {
		this.ctxProvider = ctxProvider;
		if (rightChild != null) {
			rightChild.setContextProvider(ctxProvider);
		}
		if (leftChild != null) {
			leftChild.setContextProvider(ctxProvider);
		}
	}
	
	public ConstraintNode(String value, ConstraintNode parent, ContextProvider ctxProvider) {
		this.ctxProvider = ctxProvider;
		this.parent = parent;
		this.data = value;
		if (parent != null)
			this.attachToRight(parent);
	}

	//the string name of the variable must start with a letter and can contain letters and symbols. 
	public ConstraintNode(String value, ContextProvider ctxProvider) {
		this.ctxProvider = ctxProvider;
		this.parent = null;
		this.data = value;
	}

	public Boolean attachToRight(ConstraintNode parent) {
		if (parent != null) {
			this.parent = parent;
			this.parent.rightChild = this;
			return true;
		}
		return false;
	}

	public Boolean attachToLeft(ConstraintNode parent) {
		if (parent != null) {
			this.parent = parent;
			this.parent.leftChild = this;
			return true;
		}
		return false;
	}

	//all the constraints must use variables for which the value domain has been already defined.
	//either it is the same variable used in the value domain restriction, or it should participate in there.
	//as part of the sum
	//there must be a map between the variable and its domain value: map string, IntExpr (from z3)
	
	//so far suppose there are only int variables

	// This method evaluates boolean (&, |, !) and equality (=, <, >, <=, >=) operators
	public BoolExpr evaluate(Map<String, ArithExpr> stringToConstraintVar) throws DNetException {

		// conjunction
		if (this.data.equals("&"))
			return getContext().mkAnd(this.rightChild.evaluate(stringToConstraintVar), this.leftChild.evaluate(stringToConstraintVar));
		// disjunction
		else if (this.data.equals("|"))
			return getContext().mkOr(this.rightChild.evaluate(stringToConstraintVar), this.leftChild.evaluate(stringToConstraintVar));
		// negation
		else if (this.data.equals("!")) {
			return getContext().mkNot(this.rightChild.evaluate(stringToConstraintVar));
		} else if (this.data.equals("<")) {
			return getContext().mkLe(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		} else if (this.data.equals(">")) {
			return getContext().mkGt(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		} else if (this.data.equals(">=")) {
			return getContext().mkGe(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		} else if (this.data.equals("<=")) {
			return getContext().mkLe(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		} else if (this.data.equals("=")) {
			return getContext().mkEq(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		} else
			throw new DNetException("Unknown element in constraint " + this.data);
	}


	//return arithmetic expr
	public ArithExpr evaluateExpr(Map<String, ArithExpr> stringToConstraintVar) throws DNetException {
		if (this.data.equals("+"))
			return getContext().mkAdd(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		else if (this.data.equals("-"))
			return getContext().mkSub(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		else if (this.data.equals("*"))
			return getContext().mkMul(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		else if (this.data.equals("/"))
			return getContext().mkDiv(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
		else if (this.data.matches("\\d+")) {
			return getContext().mkInt(Integer.parseInt(this.data));
		} else if (this.data.matches(nameMatch)) {
			return (stringToConstraintVar.get(this.data));
		} else
			throw new DNetException("Unknown element in constraint " + this.data);
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (rightChild != null) {
			result.append(rightChild.toString());
		}
		result.append(data);
		if (leftChild != null) {
			result.append(leftChild.toString());
		}
		return result.toString();
	}

	private Context getContext() {
		return ctxProvider.getContext();
	}

	public void addChildren(ConstraintNode right, ConstraintNode left) {
		this.rightChild = right;
		this.leftChild = left;
		right.parent = this;
		left.parent = this;
	}

	public void print() {
		System.out.println(this.toString());
	}

	//Given the constraint node and the map names<->places,
	//returns all the places that are used in the given constraint
	public ArrayList<Place> resourceInConstraint(ConstraintNode node, HashMap<String, Place> nameToPlace) throws DNetException {
		ArrayList<Place> result = new ArrayList<Place>();
		if (node.data.matches(nameMatch)) {
			if (nameToPlace.containsKey(node.data)) {
				result.add(nameToPlace.get(node.data));
			} else {
				throw new DNetException("The resource " + node.data + " does not belond to the space of resources described by places of the DNet.");
			}
		} else {
			if (node.rightChild != null) {
				result.addAll(resourceInConstraint(node.rightChild, nameToPlace));
			}
			if (node.leftChild != null) {
				result.addAll(resourceInConstraint(node.leftChild, nameToPlace));
			}
		}
		return result;
	}
	
	public ArrayList<String> resourceInConstraint(ConstraintNode node) throws DNetException {
		ArrayList<String> result = new ArrayList<String>();
		if (node.data.matches(nameMatch)) {
			result.add(node.data);
		} else {
			if (node.rightChild != null) {
				result.addAll(resourceInConstraint(node.rightChild));
			}
			if (node.leftChild != null) {
				result.addAll(resourceInConstraint(node.leftChild));
			}
		}
		return result;
	}
}
