package org.bip.resources;

import java.util.HashMap;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.bip.resources.grammar.constraintLexer;
import org.bip.resources.grammar.constraintParser;

public class Utility {

	private HashMap<Integer, ConstraintNode> utility;
	
	public Utility() {
		utility = new HashMap<Integer, ConstraintNode>();
	}
	
	public void addValue(String value, String constraint) {
		constraintLexer lexer = new constraintLexer(new ANTLRInputStream(constraint));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		constraintParser parser = new constraintParser(tokens);
		parser.constraint();
		int intValue = Integer.parseInt(value);
		ConstraintNode condition = parser.constraint;
		utility.put(intValue, condition);
	}
	
	public void addValue(String value, ConstraintNode constraint) {
		int intValue = Integer.parseInt(value);
		utility.put(intValue, constraint);
	}
	
	public void addValue(int intValue, ConstraintNode constraint) {
		utility.put(intValue, constraint);
	}
	
	public HashMap<Integer, ConstraintNode>  utility() {
		return utility;
	}
	
	public String toString() {
		return utility.toString();
	}
}
