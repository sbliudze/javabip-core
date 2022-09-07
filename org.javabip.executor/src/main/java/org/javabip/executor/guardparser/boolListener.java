// Generated from /Users/lsafina/Projects/javabip-core/org.javabip.executor/src/main/java/org/javabip/executor/guardparser/bool.g4 by ANTLR 4.9.2
package org.javabip.executor.guardparser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link boolParser}.
 */
public interface boolListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link boolParser#ident}.
	 * @param ctx the parse tree
	 */
	void enterIdent(boolParser.IdentContext ctx);
	/**
	 * Exit a parse tree produced by {@link boolParser#ident}.
	 * @param ctx the parse tree
	 */
	void exitIdent(boolParser.IdentContext ctx);
	/**
	 * Enter a parse tree produced by {@link boolParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(boolParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link boolParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(boolParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link boolParser#manom}.
	 * @param ctx the parse tree
	 */
	void enterManom(boolParser.ManomContext ctx);
	/**
	 * Exit a parse tree produced by {@link boolParser#manom}.
	 * @param ctx the parse tree
	 */
	void exitManom(boolParser.ManomContext ctx);
	/**
	 * Enter a parse tree produced by {@link boolParser#expr_inner}.
	 * @param ctx the parse tree
	 */
	void enterExpr_inner(boolParser.Expr_innerContext ctx);
	/**
	 * Exit a parse tree produced by {@link boolParser#expr_inner}.
	 * @param ctx the parse tree
	 */
	void exitExpr_inner(boolParser.Expr_innerContext ctx);
	/**
	 * Enter a parse tree produced by {@link boolParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(boolParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link boolParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(boolParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link boolParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(boolParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link boolParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(boolParser.FormulaContext ctx);
}