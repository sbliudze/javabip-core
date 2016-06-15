package org.bip.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bip.constraint.DnetConstraint;
import org.bip.constraint.ExpressionCreator;
import org.bip.constraint.VariableExpression;

public class ConstraintNode {

	// if there is only one child, by convention it is added only as a right child
	public ConstraintNode rightChild;
	public ConstraintNode leftChild;
	public ConstraintNode parent;
	public String data;
	
	private final String nameMatch = "[a-zA-Z][a-zA-Z0-9]*";
	private ExpressionCreator creator;
	
	public ConstraintNode(String value) {
		this.data = value;
		if (parent != null)
			this.attachToRight(parent);
	}
	
	public ConstraintNode(String value, ExpressionCreator factory) {
		this.data = value;
		this.creator = factory;
		if (parent != null)
			this.attachToRight(parent);
	}

	public void addFactory(ExpressionCreator factory) {
		this.creator = factory;
		if (rightChild != null) {
			rightChild.addFactory(factory);
		}
		if (leftChild != null) {
			leftChild.addFactory(factory);
		}

	}
	
	public ConstraintNode(String value, ConstraintNode parent, ExpressionCreator factory) {
		this.creator = factory;
		this.parent = parent;
		this.data = value;
		if (parent != null)
			this.attachToRight(parent);
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

	public DnetConstraint evaluateN(Map<String, VariableExpression> stringToConstraintVar) throws DNetException {
		// conjunction
		if (this.data.equals("&"))
			return creator.and(this.rightChild.evaluateN(stringToConstraintVar), this.leftChild.evaluateN(stringToConstraintVar));
		// disjunction
		else if (this.data.equals("|"))
			return creator.or(this.rightChild.evaluateN(stringToConstraintVar), this.leftChild.evaluateN(stringToConstraintVar));
		// negation
		else if (this.data.equals("!")) {
			return creator.not(this.rightChild.evaluateN(stringToConstraintVar));
		} else if (this.data.equals("<")) {
			return creator.createLess(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		} else if (this.data.equals(">")) {
			return creator.createGreater(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		} else if (this.data.equals(">=")) {
			return creator.createGreaterOrEqual(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		} else if (this.data.equals("<=")) {
			return creator.createLessOrEqual(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		} else if (this.data.equals("=")) {
			return creator.createEqual(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		} else
			throw new DNetException("Unknown element in constraint " + this.data);
	}
	
	//all the constraints must use variables for which the value domain has been already defined.
	//either it is the same variable used in the value domain restriction, or it should participate in there.
	//as part of the sum
	//there must be a map between the variable and its domain value: map string, IntExpr (from z3)
	
	//so far suppose there are only int variables

	// This method evaluates boolean (&, |, !) and equality (=, <, >, <=, >=) operators
	/*public BoolExpr evaluate(Map<String, ArithExpr> stringToConstraintVar) throws DNetException {

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
			return getContext().mkLt(this.rightChild.evaluateExpr(stringToConstraintVar), this.leftChild.evaluateExpr(stringToConstraintVar));
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
	}*/
	
	public VariableExpression evaluateExprN(Map<String, VariableExpression> stringToConstraintVar) throws DNetException {
		if (this.data.equals("+"))
			return creator.createAddition(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		else if (this.data.equals("-"))
			return creator.createSubstraction(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		else if (this.data.equals("*"))
			return creator.createMultiplication(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		else if (this.data.equals("/"))
			return creator.createDivision(this.rightChild.evaluateExprN(stringToConstraintVar), this.leftChild.evaluateExprN(stringToConstraintVar));
		else if (this.data.matches("\\d+")) {
			return creator.createNumber(this.data);
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
