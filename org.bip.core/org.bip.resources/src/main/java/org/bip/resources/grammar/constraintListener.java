// Generated from constraint.g by ANTLR 4.0

package org.bip.resources.grammar;
import java.util.*;
import java.util.*;

import org.bip.resources.Constraint;
import org.bip.resources.ConstraintNode;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface constraintListener extends ParseTreeListener {
	void enterIdent1(constraintParser.Ident1Context ctx);
	void exitIdent1(constraintParser.Ident1Context ctx);

	void enterMult(constraintParser.MultContext ctx);
	void exitMult(constraintParser.MultContext ctx);

	void enterDisj(constraintParser.DisjContext ctx);
	void exitDisj(constraintParser.DisjContext ctx);

	void enterConj(constraintParser.ConjContext ctx);
	void exitConj(constraintParser.ConjContext ctx);

	void enterConstraint(constraintParser.ConstraintContext ctx);
	void exitConstraint(constraintParser.ConstraintContext ctx);

	void enterSum(constraintParser.SumContext ctx);
	void exitSum(constraintParser.SumContext ctx);

	void enterExpr(constraintParser.ExprContext ctx);
	void exitExpr(constraintParser.ExprContext ctx);

	void enterBoolExpr(constraintParser.BoolExprContext ctx);
	void exitBoolExpr(constraintParser.BoolExprContext ctx);

	void enterId(constraintParser.IdContext ctx);
	void exitId(constraintParser.IdContext ctx);

	void enterIdent(constraintParser.IdentContext ctx);
	void exitIdent(constraintParser.IdentContext ctx);

	void enterTerm(constraintParser.TermContext ctx);
	void exitTerm(constraintParser.TermContext ctx);

	void enterCstr(constraintParser.CstrContext ctx);
	void exitCstr(constraintParser.CstrContext ctx);

	void enterEq(constraintParser.EqContext ctx);
	void exitEq(constraintParser.EqContext ctx);
}