// Generated from dNet.g by ANTLR 4.0

package org.bip.resources.grammar;
import java.util.*;
import java.util.*;

import org.bip.resources.Constraint;
import org.bip.resources.ConstraintNode;
import org.bip.resources.DNet;
import org.bip.resources.InhibitorArc;
import org.bip.resources.Place;
import org.bip.resources.Transition;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ErrorNode;

public class dNetBaseListener implements dNetListener {
	@Override public void enterIdent1(dNetParser.Ident1Context ctx) { }
	@Override public void exitIdent1(dNetParser.Ident1Context ctx) { }

	@Override public void enterMult(dNetParser.MultContext ctx) { }
	@Override public void exitMult(dNetParser.MultContext ctx) { }

	@Override public void enterInh_tr(dNetParser.Inh_trContext ctx) { }
	@Override public void exitInh_tr(dNetParser.Inh_trContext ctx) { }

	@Override public void enterInhibitors(dNetParser.InhibitorsContext ctx) { }
	@Override public void exitInhibitors(dNetParser.InhibitorsContext ctx) { }

	@Override public void enterDisj(dNetParser.DisjContext ctx) { }
	@Override public void exitDisj(dNetParser.DisjContext ctx) { }

	@Override public void enterConj(dNetParser.ConjContext ctx) { }
	@Override public void exitConj(dNetParser.ConjContext ctx) { }

	@Override public void enterSum(dNetParser.SumContext ctx) { }
	@Override public void exitSum(dNetParser.SumContext ctx) { }

	@Override public void enterExpr(dNetParser.ExprContext ctx) { }
	@Override public void exitExpr(dNetParser.ExprContext ctx) { }

	@Override public void enterBoolExpr(dNetParser.BoolExprContext ctx) { }
	@Override public void exitBoolExpr(dNetParser.BoolExprContext ctx) { }

	@Override public void enterNet(dNetParser.NetContext ctx) { }
	@Override public void exitNet(dNetParser.NetContext ctx) { }

	@Override public void enterCosts(dNetParser.CostsContext ctx) { }
	@Override public void exitCosts(dNetParser.CostsContext ctx) { }

	@Override public void enterId(dNetParser.IdContext ctx) { }
	@Override public void exitId(dNetParser.IdContext ctx) { }

	@Override public void enterIdent(dNetParser.IdentContext ctx) { }
	@Override public void exitIdent(dNetParser.IdentContext ctx) { }

	@Override public void enterRequest(dNetParser.RequestContext ctx) { }
	@Override public void exitRequest(dNetParser.RequestContext ctx) { }

	@Override public void enterPlace(dNetParser.PlaceContext ctx) { }
	@Override public void exitPlace(dNetParser.PlaceContext ctx) { }

	@Override public void enterTransition(dNetParser.TransitionContext ctx) { }
	@Override public void exitTransition(dNetParser.TransitionContext ctx) { }

	@Override public void enterPlaces(dNetParser.PlacesContext ctx) { }
	@Override public void exitPlaces(dNetParser.PlacesContext ctx) { }

	@Override public void enterCost(dNetParser.CostContext ctx) { }
	@Override public void exitCost(dNetParser.CostContext ctx) { }

	@Override public void enterOut_place(dNetParser.Out_placeContext ctx) { }
	@Override public void exitOut_place(dNetParser.Out_placeContext ctx) { }

	@Override public void enterTransitions(dNetParser.TransitionsContext ctx) { }
	@Override public void exitTransitions(dNetParser.TransitionsContext ctx) { }

	@Override public void enterTerm(dNetParser.TermContext ctx) { }
	@Override public void exitTerm(dNetParser.TermContext ctx) { }

	@Override public void enterInhibitor(dNetParser.InhibitorContext ctx) { }
	@Override public void exitInhibitor(dNetParser.InhibitorContext ctx) { }

	@Override public void enterCstr(dNetParser.CstrContext ctx) { }
	@Override public void exitCstr(dNetParser.CstrContext ctx) { }

	@Override public void enterIn_place(dNetParser.In_placeContext ctx) { }
	@Override public void exitIn_place(dNetParser.In_placeContext ctx) { }

	@Override public void enterEq(dNetParser.EqContext ctx) { }
	@Override public void exitEq(dNetParser.EqContext ctx) { }

	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	@Override public void visitTerminal(TerminalNode node) { }
	@Override public void visitErrorNode(ErrorNode node) { }
}