// Generated from dNet.g by ANTLR 4.0

package org.bip.resources.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface dNetListener extends ParseTreeListener {
	void enterIdent1(dNetParser.Ident1Context ctx);
	void exitIdent1(dNetParser.Ident1Context ctx);

	void enterMult(dNetParser.MultContext ctx);
	void exitMult(dNetParser.MultContext ctx);

	void enterInh_tr(dNetParser.Inh_trContext ctx);
	void exitInh_tr(dNetParser.Inh_trContext ctx);

	void enterInhibitors(dNetParser.InhibitorsContext ctx);
	void exitInhibitors(dNetParser.InhibitorsContext ctx);

	void enterDisj(dNetParser.DisjContext ctx);
	void exitDisj(dNetParser.DisjContext ctx);

	void enterConj(dNetParser.ConjContext ctx);
	void exitConj(dNetParser.ConjContext ctx);

	void enterSum(dNetParser.SumContext ctx);
	void exitSum(dNetParser.SumContext ctx);

	void enterExpr(dNetParser.ExprContext ctx);
	void exitExpr(dNetParser.ExprContext ctx);

	void enterBoolExpr(dNetParser.BoolExprContext ctx);
	void exitBoolExpr(dNetParser.BoolExprContext ctx);

	void enterNet(dNetParser.NetContext ctx);
	void exitNet(dNetParser.NetContext ctx);

	void enterCosts(dNetParser.CostsContext ctx);
	void exitCosts(dNetParser.CostsContext ctx);

	void enterId(dNetParser.IdContext ctx);
	void exitId(dNetParser.IdContext ctx);

	void enterIdent(dNetParser.IdentContext ctx);
	void exitIdent(dNetParser.IdentContext ctx);

	void enterRequest(dNetParser.RequestContext ctx);
	void exitRequest(dNetParser.RequestContext ctx);

	void enterPlace(dNetParser.PlaceContext ctx);
	void exitPlace(dNetParser.PlaceContext ctx);

	void enterTransition(dNetParser.TransitionContext ctx);
	void exitTransition(dNetParser.TransitionContext ctx);

	void enterPlaces(dNetParser.PlacesContext ctx);
	void exitPlaces(dNetParser.PlacesContext ctx);

	void enterSubUtil(dNetParser.SubUtilContext ctx);
	void exitSubUtil(dNetParser.SubUtilContext ctx);

	void enterCost(dNetParser.CostContext ctx);
	void exitCost(dNetParser.CostContext ctx);

	void enterOut_place(dNetParser.Out_placeContext ctx);
	void exitOut_place(dNetParser.Out_placeContext ctx);

	void enterUtility(dNetParser.UtilityContext ctx);
	void exitUtility(dNetParser.UtilityContext ctx);

	void enterTransitions(dNetParser.TransitionsContext ctx);
	void exitTransitions(dNetParser.TransitionsContext ctx);

	void enterTerm(dNetParser.TermContext ctx);
	void exitTerm(dNetParser.TermContext ctx);

	void enterInhibitor(dNetParser.InhibitorContext ctx);
	void exitInhibitor(dNetParser.InhibitorContext ctx);

	void enterCstr(dNetParser.CstrContext ctx);
	void exitCstr(dNetParser.CstrContext ctx);

	void enterIn_place(dNetParser.In_placeContext ctx);
	void exitIn_place(dNetParser.In_placeContext ctx);

	void enterEq(dNetParser.EqContext ctx);
	void exitEq(dNetParser.EqContext ctx);
}