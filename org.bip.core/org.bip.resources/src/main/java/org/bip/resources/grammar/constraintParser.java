// Generated from constraint.g by ANTLR 4.0

package org.bip.resources.grammar;
import java.util.*;
import java.util.*;
import org.bip.resources.ConstraintNode;
import org.bip.resources.Constraint;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class constraintParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LPAREN=1, RPAREN=2, ID=3, NUM=4, OR=5, AND=6, NEGATION=7, SEMICOLON=8, 
		COLON=9, COMMA=10, MULT=11, DIV=12, PLUS=13, MINUS=14, EQ=15, LESS=16, 
		MORE=17, GEQ=18, LEQ=19, WS=20;
	public static final String[] tokenNames = {
		"<INVALID>", "'('", "')'", "ID", "NUM", "'|'", "'&'", "'!'", "';'", "':'", 
		"','", "'*'", "'/'", "'+'", "'-'", "'='", "'<'", "'>'", "'>='", "'<='", 
		"WS"
	};
	public static final int
		RULE_constraint = 0, RULE_id = 1, RULE_ident = 2, RULE_term = 3, RULE_conj = 4, 
		RULE_disj = 5, RULE_boolExpr = 6, RULE_eq = 7, RULE_ident1 = 8, RULE_mult = 9, 
		RULE_sum = 10, RULE_expr = 11, RULE_cstr = 12;
	public static final String[] ruleNames = {
		"constraint", "id", "ident", "term", "conj", "disj", "boolExpr", "eq", 
		"ident1", "mult", "sum", "expr", "cstr"
	};

	@Override
	public String getGrammarFileName() { return "constraint.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }


		public ConstraintNode constraint;  
		
		public Stack<ConstraintNode> stack = new Stack<ConstraintNode>();

	  public void nwc(String s, ConstraintNode t){
	  	ConstraintNode n = new ConstraintNode(s);
	  	t.attachToRight(n);
	  	stack.push(n);
	  }
	  
	  public void nwc2(String s, ConstraintNode left, ConstraintNode right) {
		ConstraintNode n = new ConstraintNode(s);
		n.addChildren(right, left);
	  	stack.push(n);
	  }

	public constraintParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ConstraintContext extends ParserRuleContext {
		public ConstraintNode result;
		public CstrContext cstr() {
			return getRuleContext(CstrContext.class,0);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitConstraint(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_constraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26); cstr();
			constraint = stack.pop(); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public Token var;
		public TerminalNode ID() { return getToken(constraintParser.ID, 0); }
		public TerminalNode NUM() { return getToken(constraintParser.NUM, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitId(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_id);
		try {
			setState(33);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(29); ((IdContext)_localctx).var = match(ID);
				stack.push(new ConstraintNode((((IdContext)_localctx).var!=null?((IdContext)_localctx).var.getText():null)));
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(31); ((IdContext)_localctx).var = match(NUM);
				stack.push(new ConstraintNode((((IdContext)_localctx).var!=null?((IdContext)_localctx).var.getText():null)));
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentContext extends ParserRuleContext {
		public BoolExprContext ex;
		public TerminalNode RPAREN() { return getToken(constraintParser.RPAREN, 0); }
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(constraintParser.LPAREN, 0); }
		public EqContext eq() {
			return getRuleContext(EqContext.class,0);
		}
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterIdent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitIdent(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ident);
		try {
			setState(41);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(35); eq();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(36); match(LPAREN);
				setState(37); ((IdentContext)_localctx).ex = boolExpr();
				setState(38); match(RPAREN);

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public IdentContext i;
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode NEGATION() { return getToken(constraintParser.NEGATION, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_term);
		try {
			setState(50);
			switch (_input.LA(1)) {
			case LPAREN:
			case ID:
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(43); ((TermContext)_localctx).i = ident();

				}
				break;
			case NEGATION:
				enterOuterAlt(_localctx, 2);
				{
				setState(46); match(NEGATION);
				setState(47); ((TermContext)_localctx).i = ident();
				nwc("!", stack.pop()); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjContext extends ParserRuleContext {
		public TermContext t;
		public ConjContext c;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public ConjContext conj() {
			return getRuleContext(ConjContext.class,0);
		}
		public TerminalNode AND() { return getToken(constraintParser.AND, 0); }
		public ConjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conj; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterConj(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitConj(this);
		}
	}

	public final ConjContext conj() throws RecognitionException {
		ConjContext _localctx = new ConjContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_conj);
		try {
			setState(60);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(52); ((ConjContext)_localctx).t = term();

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(55); ((ConjContext)_localctx).t = term();
				setState(56); match(AND);
				setState(57); ((ConjContext)_localctx).c = conj();
				nwc2("&", stack.pop(), stack.pop());
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjContext extends ParserRuleContext {
		public ConjContext c;
		public DisjContext d;
		public DisjContext disj() {
			return getRuleContext(DisjContext.class,0);
		}
		public ConjContext conj() {
			return getRuleContext(ConjContext.class,0);
		}
		public TerminalNode OR() { return getToken(constraintParser.OR, 0); }
		public DisjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disj; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterDisj(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitDisj(this);
		}
	}

	public final DisjContext disj() throws RecognitionException {
		DisjContext _localctx = new DisjContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_disj);
		try {
			setState(70);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(62); ((DisjContext)_localctx).c = conj();

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65); ((DisjContext)_localctx).c = conj();
				setState(66); match(OR);
				setState(67); ((DisjContext)_localctx).d = disj();
				nwc2("|", stack.pop(), stack.pop());
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolExprContext extends ParserRuleContext {
		public DisjContext d;
		public TerminalNode RPAREN() { return getToken(constraintParser.RPAREN, 0); }
		public DisjContext disj() {
			return getRuleContext(DisjContext.class,0);
		}
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(constraintParser.LPAREN, 0); }
		public BoolExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitBoolExpr(this);
		}
	}

	public final BoolExprContext boolExpr() throws RecognitionException {
		BoolExprContext _localctx = new BoolExprContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_boolExpr);
		try {
			setState(77);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(72); ((BoolExprContext)_localctx).d = disj();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(73); match(LPAREN);
				setState(74); boolExpr();
				setState(75); match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqContext extends ParserRuleContext {
		public ExprContext e1;
		public ExprContext e2;
		public TerminalNode GEQ() { return getToken(constraintParser.GEQ, 0); }
		public TerminalNode MORE() { return getToken(constraintParser.MORE, 0); }
		public TerminalNode EQ() { return getToken(constraintParser.EQ, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode LEQ() { return getToken(constraintParser.LEQ, 0); }
		public TerminalNode LESS() { return getToken(constraintParser.LESS, 0); }
		public EqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitEq(this);
		}
	}

	public final EqContext eq() throws RecognitionException {
		EqContext _localctx = new EqContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_eq);
		try {
			setState(104);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(79); ((EqContext)_localctx).e1 = expr();
				setState(80); match(EQ);
				setState(81); ((EqContext)_localctx).e2 = expr();
				nwc2("=", stack.pop(), stack.pop());
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(84); ((EqContext)_localctx).e1 = expr();
				setState(85); match(LESS);
				setState(86); ((EqContext)_localctx).e2 = expr();
				nwc2("<", stack.pop(), stack.pop());
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(89); ((EqContext)_localctx).e1 = expr();
				setState(90); match(MORE);
				setState(91); ((EqContext)_localctx).e2 = expr();
				nwc2(">", stack.pop(), stack.pop());
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(94); ((EqContext)_localctx).e1 = expr();
				setState(95); match(GEQ);
				setState(96); ((EqContext)_localctx).e2 = expr();
				nwc2(">=", stack.pop(), stack.pop());
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(99); ((EqContext)_localctx).e1 = expr();
				setState(100); match(LEQ);
				setState(101); ((EqContext)_localctx).e2 = expr();
				nwc2("<=", stack.pop(), stack.pop());
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ident1Context extends ParserRuleContext {
		public IdContext var;
		public ExprContext ex;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(constraintParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(constraintParser.LPAREN, 0); }
		public Ident1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterIdent1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitIdent1(this);
		}
	}

	public final Ident1Context ident1() throws RecognitionException {
		Ident1Context _localctx = new Ident1Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_ident1);
		try {
			setState(112);
			switch (_input.LA(1)) {
			case ID:
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(106); ((Ident1Context)_localctx).var = id();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(107); match(LPAREN);
				setState(108); ((Ident1Context)_localctx).ex = expr();
				setState(109); match(RPAREN);

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultContext extends ParserRuleContext {
		public int _p;
		public MultContext m;
		public Ident1Context i;
		public Ident1Context ident1() {
			return getRuleContext(Ident1Context.class,0);
		}
		public MultContext mult() {
			return getRuleContext(MultContext.class,0);
		}
		public TerminalNode DIV() { return getToken(constraintParser.DIV, 0); }
		public TerminalNode MULT() { return getToken(constraintParser.MULT, 0); }
		public MultContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public MultContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_mult; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitMult(this);
		}
	}

	public final MultContext mult(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultContext _localctx = new MultContext(_ctx, _parentState, _p);
		MultContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, RULE_mult);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(115); ((MultContext)_localctx).i = ident1();

			}
			_ctx.stop = _input.LT(-1);
			setState(130);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(128);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new MultContext(_parentctx, _parentState, _p);
						_localctx.m = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_mult);
						setState(118);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(119); match(MULT);
						setState(120); ((MultContext)_localctx).i = ident1();
						nwc2("*", stack.pop(), stack.pop());
						}
						break;

					case 2:
						{
						_localctx = new MultContext(_parentctx, _parentState, _p);
						_localctx.m = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_mult);
						setState(123);
						if (!(1 >= _localctx._p)) throw new FailedPredicateException(this, "1 >= $_p");
						setState(124); match(DIV);
						setState(125); ((MultContext)_localctx).i = ident1();
						nwc2("/", stack.pop(), stack.pop());
						}
						break;
					}
					} 
				}
				setState(132);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SumContext extends ParserRuleContext {
		public MultContext m;
		public SumContext s;
		public MultContext mult() {
			return getRuleContext(MultContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(constraintParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(constraintParser.MINUS, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public SumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterSum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitSum(this);
		}
	}

	public final SumContext sum() throws RecognitionException {
		SumContext _localctx = new SumContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_sum);
		try {
			setState(146);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(133); mult(0);

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(136); ((SumContext)_localctx).m = mult(0);
				setState(137); match(PLUS);
				setState(138); ((SumContext)_localctx).s = sum();
				nwc2("+", stack.pop(), stack.pop());
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(141); ((SumContext)_localctx).m = mult(0);
				setState(142); match(MINUS);
				setState(143); ((SumContext)_localctx).s = sum();
				nwc2("-", stack.pop(), stack.pop());
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public TerminalNode RPAREN() { return getToken(constraintParser.RPAREN, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(constraintParser.LPAREN, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expr);
		try {
			setState(153);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(148); sum();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(149); match(LPAREN);
				setState(150); expr();
				setState(151); match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CstrContext extends ParserRuleContext {
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public CstrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cstr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).enterCstr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof constraintListener ) ((constraintListener)listener).exitCstr(this);
		}
	}

	public final CstrContext cstr() throws RecognitionException {
		CstrContext _localctx = new CstrContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_cstr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155); boolExpr(); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9: return mult_sempred((MultContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean mult_sempred(MultContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return 2 >= _localctx._p;

		case 1: return 1 >= _localctx._p;
		}
		return true;
	}

	public static final String _serializedATN =
		"\2\3\26\u00a0\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b"+
		"\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\3\3"+
		"\3\3\3\3\3\5\3$\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4,\n\4\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\5\5\65\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6?\n\6\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\5\7I\n\7\3\b\3\b\3\b\3\b\3\b\5\bP\n\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\5\tk\n\t\3\n\3\n\3\n\3\n\3\n\3\n\5\ns\n\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13"+
		"\u0083\n\13\f\13\16\13\u0086\13\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\5\f\u0095\n\f\3\r\3\r\3\r\3\r\3\r\5\r\u009c\n\r\3\16"+
		"\3\16\3\16\2\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\2\u00a2\2\34\3\2\2"+
		"\2\4#\3\2\2\2\6+\3\2\2\2\b\64\3\2\2\2\n>\3\2\2\2\fH\3\2\2\2\16O\3\2\2"+
		"\2\20j\3\2\2\2\22r\3\2\2\2\24t\3\2\2\2\26\u0094\3\2\2\2\30\u009b\3\2\2"+
		"\2\32\u009d\3\2\2\2\34\35\5\32\16\2\35\36\b\2\1\2\36\3\3\2\2\2\37 \7\5"+
		"\2\2 $\b\3\1\2!\"\7\6\2\2\"$\b\3\1\2#\37\3\2\2\2#!\3\2\2\2$\5\3\2\2\2"+
		"%,\5\20\t\2&\'\7\3\2\2\'(\5\16\b\2()\7\4\2\2)*\b\4\1\2*,\3\2\2\2+%\3\2"+
		"\2\2+&\3\2\2\2,\7\3\2\2\2-.\5\6\4\2./\b\5\1\2/\65\3\2\2\2\60\61\7\t\2"+
		"\2\61\62\5\6\4\2\62\63\b\5\1\2\63\65\3\2\2\2\64-\3\2\2\2\64\60\3\2\2\2"+
		"\65\t\3\2\2\2\66\67\5\b\5\2\678\b\6\1\28?\3\2\2\29:\5\b\5\2:;\7\b\2\2"+
		";<\5\n\6\2<=\b\6\1\2=?\3\2\2\2>\66\3\2\2\2>9\3\2\2\2?\13\3\2\2\2@A\5\n"+
		"\6\2AB\b\7\1\2BI\3\2\2\2CD\5\n\6\2DE\7\7\2\2EF\5\f\7\2FG\b\7\1\2GI\3\2"+
		"\2\2H@\3\2\2\2HC\3\2\2\2I\r\3\2\2\2JP\5\f\7\2KL\7\3\2\2LM\5\16\b\2MN\7"+
		"\4\2\2NP\3\2\2\2OJ\3\2\2\2OK\3\2\2\2P\17\3\2\2\2QR\5\30\r\2RS\7\21\2\2"+
		"ST\5\30\r\2TU\b\t\1\2Uk\3\2\2\2VW\5\30\r\2WX\7\22\2\2XY\5\30\r\2YZ\b\t"+
		"\1\2Zk\3\2\2\2[\\\5\30\r\2\\]\7\23\2\2]^\5\30\r\2^_\b\t\1\2_k\3\2\2\2"+
		"`a\5\30\r\2ab\7\24\2\2bc\5\30\r\2cd\b\t\1\2dk\3\2\2\2ef\5\30\r\2fg\7\25"+
		"\2\2gh\5\30\r\2hi\b\t\1\2ik\3\2\2\2jQ\3\2\2\2jV\3\2\2\2j[\3\2\2\2j`\3"+
		"\2\2\2je\3\2\2\2k\21\3\2\2\2ls\5\4\3\2mn\7\3\2\2no\5\30\r\2op\7\4\2\2"+
		"pq\b\n\1\2qs\3\2\2\2rl\3\2\2\2rm\3\2\2\2s\23\3\2\2\2tu\b\13\1\2uv\5\22"+
		"\n\2vw\b\13\1\2w\u0084\3\2\2\2xy\6\13\2\3yz\7\r\2\2z{\5\22\n\2{|\b\13"+
		"\1\2|\u0083\3\2\2\2}~\6\13\3\3~\177\7\16\2\2\177\u0080\5\22\n\2\u0080"+
		"\u0081\b\13\1\2\u0081\u0083\3\2\2\2\u0082x\3\2\2\2\u0082}\3\2\2\2\u0083"+
		"\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\25\3\2\2"+
		"\2\u0086\u0084\3\2\2\2\u0087\u0088\5\24\13\2\u0088\u0089\b\f\1\2\u0089"+
		"\u0095\3\2\2\2\u008a\u008b\5\24\13\2\u008b\u008c\7\17\2\2\u008c\u008d"+
		"\5\26\f\2\u008d\u008e\b\f\1\2\u008e\u0095\3\2\2\2\u008f\u0090\5\24\13"+
		"\2\u0090\u0091\7\20\2\2\u0091\u0092\5\26\f\2\u0092\u0093\b\f\1\2\u0093"+
		"\u0095\3\2\2\2\u0094\u0087\3\2\2\2\u0094\u008a\3\2\2\2\u0094\u008f\3\2"+
		"\2\2\u0095\27\3\2\2\2\u0096\u009c\5\26\f\2\u0097\u0098\7\3\2\2\u0098\u0099"+
		"\5\30\r\2\u0099\u009a\7\4\2\2\u009a\u009c\3\2\2\2\u009b\u0096\3\2\2\2"+
		"\u009b\u0097\3\2\2\2\u009c\31\3\2\2\2\u009d\u009e\5\16\b\2\u009e\33\3"+
		"\2\2\2\16#+\64>HOjr\u0082\u0084\u0094\u009b";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}