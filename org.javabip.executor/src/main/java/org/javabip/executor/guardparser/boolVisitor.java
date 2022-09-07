// Generated from /Users/lsafina/Projects/javabip-core/org.javabip.executor/src/main/java/org/javabip/executor/guardparser/bool.g4 by ANTLR 4.9.2
package org.javabip.executor.guardparser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link boolParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface boolVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link boolParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(boolParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link boolParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(boolParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link boolParser#manom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitManom(boolParser.ManomContext ctx);
	/**
	 * Visit a parse tree produced by {@link boolParser#expr_inner}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_inner(boolParser.Expr_innerContext ctx);
	/**
	 * Visit a parse tree produced by {@link boolParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(boolParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link boolParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(boolParser.FormulaContext ctx);
}