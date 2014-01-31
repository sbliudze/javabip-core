// Generated from bool.g4 by ANTLR 4.0

package org.bip.executor.guardparser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface boolListener extends ParseTreeListener {
	void enterIdent(boolParser.IdentContext ctx);
	void exitIdent(boolParser.IdentContext ctx);

	void enterTerm(boolParser.TermContext ctx);
	void exitTerm(boolParser.TermContext ctx);

	void enterExpr(boolParser.ExprContext ctx);
	void exitExpr(boolParser.ExprContext ctx);

	void enterManom(boolParser.ManomContext ctx);
	void exitManom(boolParser.ManomContext ctx);

	void enterExpr_inner(boolParser.Expr_innerContext ctx);
	void exitExpr_inner(boolParser.Expr_innerContext ctx);

	void enterFormula(boolParser.FormulaContext ctx);
	void exitFormula(boolParser.FormulaContext ctx);
}