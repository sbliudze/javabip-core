// Generated from constraint.g by ANTLR 4.0

package org.bip.resources.grammar;
import java.util.*;
import org.bip.resources.ConstraintNode;
import org.bip.resources.Constraint;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ErrorNode;

public class constraintBaseListener implements constraintListener {
	@Override public void enterIdent1(constraintParser.Ident1Context ctx) { }
	@Override public void exitIdent1(constraintParser.Ident1Context ctx) { }

	@Override public void enterMult(constraintParser.MultContext ctx) { }
	@Override public void exitMult(constraintParser.MultContext ctx) { }

	@Override public void enterDisj(constraintParser.DisjContext ctx) { }
	@Override public void exitDisj(constraintParser.DisjContext ctx) { }

	@Override public void enterConj(constraintParser.ConjContext ctx) { }
	@Override public void exitConj(constraintParser.ConjContext ctx) { }

	@Override public void enterConstraint(constraintParser.ConstraintContext ctx) { }
	@Override public void exitConstraint(constraintParser.ConstraintContext ctx) { }

	@Override public void enterSum(constraintParser.SumContext ctx) { }
	@Override public void exitSum(constraintParser.SumContext ctx) { }

	@Override public void enterExpr(constraintParser.ExprContext ctx) { }
	@Override public void exitExpr(constraintParser.ExprContext ctx) { }

	@Override public void enterBoolExpr(constraintParser.BoolExprContext ctx) { }
	@Override public void exitBoolExpr(constraintParser.BoolExprContext ctx) { }

	@Override public void enterId(constraintParser.IdContext ctx) { }
	@Override public void exitId(constraintParser.IdContext ctx) { }

	@Override public void enterIdent(constraintParser.IdentContext ctx) { }
	@Override public void exitIdent(constraintParser.IdentContext ctx) { }

	@Override public void enterTerm(constraintParser.TermContext ctx) { }
	@Override public void exitTerm(constraintParser.TermContext ctx) { }

	@Override public void enterCstr(constraintParser.CstrContext ctx) { }
	@Override public void exitCstr(constraintParser.CstrContext ctx) { }

	@Override public void enterEq(constraintParser.EqContext ctx) { }
	@Override public void exitEq(constraintParser.EqContext ctx) { }

	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	@Override public void visitTerminal(TerminalNode node) { }
	@Override public void visitErrorNode(ErrorNode node) { }
}