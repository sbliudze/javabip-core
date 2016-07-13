// Generated from dNet.g by ANTLR 4.0

package org.bip.resources.grammar;
import java.util.*;
import java.util.*;

import org.bip.resources.ConstraintNode;
import org.bip.resources.DNetException;
import org.bip.resources.Transition;
import org.bip.resources.Place;
import org.bip.resources.DNet;
import org.bip.resources.InhibitorArc;
import org.bip.resources.Constraint;
import org.bip.resources.Utility;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class dNetParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		REQUEST=1, COST=2, KEY_PLACES=3, KEY_TRANSITION=4, REFERS=5, FROM=6, TO=7, 
		CONSTRAINT=8, INHIBITOR=9, LPAREN=10, RPAREN=11, ID=12, NUM=13, OR=14, 
		AND=15, NEGATION=16, SEMICOLON=17, COLON=18, COMMA=19, MULT=20, DIV=21, 
		PLUS=22, MINUS=23, EQ=24, LESS=25, MORE=26, GEQ=27, LEQ=28, WS=29;
	public static final String[] tokenNames = {
		"<INVALID>", "'request'", "'cost'", "'places'", "'transition'", "'refers'", 
		"'from'", "'to'", "'constraint'", "'inhibitor'", "'('", "')'", "ID", "NUM", 
		"'|'", "'&'", "'!'", "';'", "':'", "','", "'*'", "'/'", "'+'", "'-'", 
		"'='", "'<'", "'>'", "'>='", "'<='", "WS"
	};
	public static final int
		RULE_net = 0, RULE_places = 1, RULE_place = 2, RULE_transitions = 3, RULE_inhibitors = 4, 
		RULE_transition = 5, RULE_inhibitor = 6, RULE_in_place = 7, RULE_out_place = 8, 
		RULE_inh_tr = 9, RULE_costs = 10, RULE_cost = 11, RULE_request = 12, RULE_id = 13, 
		RULE_ident = 14, RULE_term = 15, RULE_conj = 16, RULE_disj = 17, RULE_boolExpr = 18, 
		RULE_eq = 19, RULE_ident1 = 20, RULE_mult = 21, RULE_sum = 22, RULE_expr = 23, 
		RULE_cstr = 24, RULE_utility = 25, RULE_subUtil = 26;
	public static final String[] ruleNames = {
		"net", "places", "place", "transitions", "inhibitors", "transition", "inhibitor", 
		"in_place", "out_place", "inh_tr", "costs", "cost", "request", "id", "ident", 
		"term", "conj", "disj", "boolExpr", "eq", "ident1", "mult", "sum", "expr", 
		"cstr", "utility", "subUtil"
	};

	@Override
	public String getGrammarFileName() { return "dNet.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }


		public DNet net;  
		private ArrayList<Place> inPlaces;
		private ArrayList<Place> outPlaces;
		private HashMap<String, Place> nameToPlace=new HashMap<String, Place>();;
		private ArrayList<String> inhibitorRef;
		public ConstraintNode req;
		public Utility utility;
		
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

	public dNetParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class NetContext extends ParserRuleContext {
		public DNet resultNet;
		public RequestContext request(int i) {
			return getRuleContext(RequestContext.class,i);
		}
		public TransitionsContext transitions() {
			return getRuleContext(TransitionsContext.class,0);
		}
		public InhibitorsContext inhibitors() {
			return getRuleContext(InhibitorsContext.class,0);
		}
		public List<RequestContext> request() {
			return getRuleContexts(RequestContext.class);
		}
		public PlacesContext places() {
			return getRuleContext(PlacesContext.class,0);
		}
		public CostsContext costs(int i) {
			return getRuleContext(CostsContext.class,i);
		}
		public List<CostsContext> costs() {
			return getRuleContexts(CostsContext.class);
		}
		public NetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_net; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterNet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitNet(this);
		}
	}

	public final NetContext net() throws RecognitionException, DNetException {
		NetContext _localctx = new NetContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_net);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 net = new DNet(); 
			setState(55); places();
			setState(56); transitions();
			setState(57); inhibitors();
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COST) {
				{
				{
				setState(58); costs();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			((NetContext)_localctx).resultNet =  net; 
			setState(68);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==REQUEST) {
				{
				{
				setState(65); request();
				}
				}
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class PlacesContext extends ParserRuleContext {
		public TerminalNode COMMA(int i) {
			return getToken(dNetParser.COMMA, i);
		}
		public TerminalNode SEMICOLON() { return getToken(dNetParser.SEMICOLON, 0); }
		public List<TerminalNode> COMMA() { return getTokens(dNetParser.COMMA); }
		public PlaceContext place(int i) {
			return getRuleContext(PlaceContext.class,i);
		}
		public List<PlaceContext> place() {
			return getRuleContexts(PlaceContext.class);
		}
		public TerminalNode KEY_PLACES() { return getToken(dNetParser.KEY_PLACES, 0); }
		public PlacesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_places; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterPlaces(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitPlaces(this);
		}
	}

	public final PlacesContext places() throws RecognitionException {
		PlacesContext _localctx = new PlacesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_places);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71); match(KEY_PLACES);
			setState(72); place();
			setState(77);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(73); match(COMMA);
				setState(74); place();
				}
				}
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(80); match(SEMICOLON);
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

	public static class PlaceContext extends ParserRuleContext {
		public Token name1;
		public TerminalNode ID() { return getToken(dNetParser.ID, 0); }
		public PlaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_place; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterPlace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitPlace(this);
		}
	}

	public final PlaceContext place() throws RecognitionException {
		PlaceContext _localctx = new PlaceContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_place);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82); ((PlaceContext)_localctx).name1 = match(ID);

				  Place place = net.addPlace((((PlaceContext)_localctx).name1!=null?((PlaceContext)_localctx).name1.getText():null));
				  net.nameToPlace.put((((PlaceContext)_localctx).name1!=null?((PlaceContext)_localctx).name1.getText():null), place);
				  nameToPlace.put((((PlaceContext)_localctx).name1!=null?((PlaceContext)_localctx).name1.getText():null), place);
				
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

	public static class TransitionsContext extends ParserRuleContext {
		public TransitionContext transition(int i) {
			return getRuleContext(TransitionContext.class,i);
		}
		public List<TransitionContext> transition() {
			return getRuleContexts(TransitionContext.class);
		}
		public TransitionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transitions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterTransitions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitTransitions(this);
		}
	}

	public final TransitionsContext transitions() throws RecognitionException, DNetException {
		TransitionsContext _localctx = new TransitionsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_transitions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(85); transition();
				}
				}
				setState(88); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==KEY_TRANSITION );
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

	public static class InhibitorsContext extends ParserRuleContext {
		public List<InhibitorContext> inhibitor() {
			return getRuleContexts(InhibitorContext.class);
		}
		public InhibitorContext inhibitor(int i) {
			return getRuleContext(InhibitorContext.class,i);
		}
		public InhibitorsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inhibitors; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterInhibitors(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitInhibitors(this);
		}
	}

	public final InhibitorsContext inhibitors() throws RecognitionException {
		InhibitorsContext _localctx = new InhibitorsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_inhibitors);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==INHIBITOR) {
				{
				{
				setState(90); inhibitor();
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class TransitionContext extends ParserRuleContext {
		public Token tr;
		public CstrContext constraint;
		public TerminalNode CONSTRAINT(int i) {
			return getToken(dNetParser.CONSTRAINT, i);
		}
		public In_placeContext in_place(int i) {
			return getRuleContext(In_placeContext.class,i);
		}
		public TerminalNode KEY_TRANSITION() { return getToken(dNetParser.KEY_TRANSITION, 0); }
		public TerminalNode TO() { return getToken(dNetParser.TO, 0); }
		public List<TerminalNode> COMMA() { return getTokens(dNetParser.COMMA); }
		public CstrContext cstr(int i) {
			return getRuleContext(CstrContext.class,i);
		}
		public List<Out_placeContext> out_place() {
			return getRuleContexts(Out_placeContext.class);
		}
		public TerminalNode SEMICOLON() { return getToken(dNetParser.SEMICOLON, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(dNetParser.COMMA, i);
		}
		public TerminalNode FROM() { return getToken(dNetParser.FROM, 0); }
		public TerminalNode ID() { return getToken(dNetParser.ID, 0); }
		public List<TerminalNode> CONSTRAINT() { return getTokens(dNetParser.CONSTRAINT); }
		public Out_placeContext out_place(int i) {
			return getRuleContext(Out_placeContext.class,i);
		}
		public List<CstrContext> cstr() {
			return getRuleContexts(CstrContext.class);
		}
		public List<In_placeContext> in_place() {
			return getRuleContexts(In_placeContext.class);
		}
		public TransitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterTransition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitTransition(this);
		}
	}

	public final TransitionContext transition() throws RecognitionException, DNetException {
		TransitionContext _localctx = new TransitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_transition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96); match(KEY_TRANSITION);
			setState(97); ((TransitionContext)_localctx).tr = match(ID);
			 
				  inPlaces = new ArrayList<Place>();
				  outPlaces = new ArrayList<Place>();
				  inhibitorRef = new ArrayList<String>();
				
			setState(99); match(FROM);
			setState(100); in_place();
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(101); match(COMMA);
				setState(102); in_place();
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(108); match(TO);
			setState(109); out_place();
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(110); match(COMMA);
				setState(111); out_place();
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CONSTRAINT) {
				{
				{
				setState(117); match(CONSTRAINT);
				setState(118); ((TransitionContext)_localctx).constraint = cstr();
				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

				 		if (!stack.empty()) {
							net.addTransition((((TransitionContext)_localctx).tr!=null?((TransitionContext)_localctx).tr.getText():null), inPlaces, outPlaces,
									new Constraint(stack.pop()));
						} else {
							net.addTransition((((TransitionContext)_localctx).tr!=null?((TransitionContext)_localctx).tr.getText():null), inPlaces, outPlaces);
						}
				 
			setState(125); match(SEMICOLON);
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

	public static class InhibitorContext extends ParserRuleContext {
		public Token place_name;
		public Token tr_name;
		public TerminalNode INHIBITOR() { return getToken(dNetParser.INHIBITOR, 0); }
		public TerminalNode ID(int i) {
			return getToken(dNetParser.ID, i);
		}
		public List<Inh_trContext> inh_tr() {
			return getRuleContexts(Inh_trContext.class);
		}
		public TerminalNode COMMA(int i) {
			return getToken(dNetParser.COMMA, i);
		}
		public TerminalNode SEMICOLON() { return getToken(dNetParser.SEMICOLON, 0); }
		public List<TerminalNode> ID() { return getTokens(dNetParser.ID); }
		public TerminalNode REFERS() { return getToken(dNetParser.REFERS, 0); }
		public TerminalNode TO() { return getToken(dNetParser.TO, 0); }
		public TerminalNode FROM() { return getToken(dNetParser.FROM, 0); }
		public List<TerminalNode> COMMA() { return getTokens(dNetParser.COMMA); }
		public Inh_trContext inh_tr(int i) {
			return getRuleContext(Inh_trContext.class,i);
		}
		public InhibitorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inhibitor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterInhibitor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitInhibitor(this);
		}
	}

	public final InhibitorContext inhibitor() throws RecognitionException {
		InhibitorContext _localctx = new InhibitorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_inhibitor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127); match(INHIBITOR);
			inhibitorRef.clear();
			setState(129); match(FROM);
			setState(130); ((InhibitorContext)_localctx).place_name = match(ID);
			setState(131); match(TO);
			setState(132); ((InhibitorContext)_localctx).tr_name = match(ID);
			setState(133); match(REFERS);
			setState(134); inh_tr();
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(135); match(COMMA);
				setState(136); inh_tr();
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

				 	net.addInhibitor((((InhibitorContext)_localctx).place_name!=null?((InhibitorContext)_localctx).place_name.getText():null), (((InhibitorContext)_localctx).tr_name!=null?((InhibitorContext)_localctx).tr_name.getText():null), inhibitorRef); 
				 
			setState(143); match(SEMICOLON);
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

	public static class In_placeContext extends ParserRuleContext {
		public Token name;
		public TerminalNode ID() { return getToken(dNetParser.ID, 0); }
		public In_placeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_in_place; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterIn_place(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitIn_place(this);
		}
	}

	public final In_placeContext in_place() throws RecognitionException {
		In_placeContext _localctx = new In_placeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_in_place);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145); ((In_placeContext)_localctx).name = match(ID);
			inPlaces.add(new Place((((In_placeContext)_localctx).name!=null?((In_placeContext)_localctx).name.getText():null)));
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

	public static class Out_placeContext extends ParserRuleContext {
		public Token name;
		public TerminalNode ID() { return getToken(dNetParser.ID, 0); }
		public Out_placeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_out_place; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterOut_place(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitOut_place(this);
		}
	}

	public final Out_placeContext out_place() throws RecognitionException {
		Out_placeContext _localctx = new Out_placeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_out_place);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148); ((Out_placeContext)_localctx).name = match(ID);
			outPlaces.add(new Place((((Out_placeContext)_localctx).name!=null?((Out_placeContext)_localctx).name.getText():null)));
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

	public static class Inh_trContext extends ParserRuleContext {
		public Token name;
		public TerminalNode ID() { return getToken(dNetParser.ID, 0); }
		public Inh_trContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inh_tr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterInh_tr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitInh_tr(this);
		}
	}

	public final Inh_trContext inh_tr() throws RecognitionException {
		Inh_trContext _localctx = new Inh_trContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_inh_tr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			((Inh_trContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==ID || _la==MULT) ) {
				((Inh_trContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			}
			consume();
			inhibitorRef.add((((Inh_trContext)_localctx).name!=null?((Inh_trContext)_localctx).name.getText():null));
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

	public static class CostsContext extends ParserRuleContext {
		public CostContext cost(int i) {
			return getRuleContext(CostContext.class,i);
		}
		public List<CostContext> cost() {
			return getRuleContexts(CostContext.class);
		}
		public CostsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_costs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterCosts(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitCosts(this);
		}
	}

	public final CostsContext costs() throws RecognitionException {
		CostsContext _localctx = new CostsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_costs);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(155); 
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(154); cost();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(157); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			} while ( _alt!=2 && _alt!=-1 );
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

	public static class CostContext extends ParserRuleContext {
		public Token name;
		public TerminalNode COLON() { return getToken(dNetParser.COLON, 0); }
		public TerminalNode SEMICOLON() { return getToken(dNetParser.SEMICOLON, 0); }
		public TerminalNode ID() { return getToken(dNetParser.ID, 0); }
		public TerminalNode COST() { return getToken(dNetParser.COST, 0); }
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public CostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cost; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterCost(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitCost(this);
		}
	}

	public final CostContext cost() throws RecognitionException {
		CostContext _localctx = new CostContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_cost);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159); match(COST);
			setState(160); ((CostContext)_localctx).name = match(ID);
			 Place pl = net.nameToPlace.get((((CostContext)_localctx).name!=null?((CostContext)_localctx).name.getText():null));
			setState(162); match(COLON);
			setState(163); boolExpr();
			pl.setCost(stack.pop());
			setState(165); match(SEMICOLON);
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

	public static class RequestContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(dNetParser.COLON, 0); }
		public TerminalNode SEMICOLON() { return getToken(dNetParser.SEMICOLON, 0); }
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public TerminalNode REQUEST() { return getToken(dNetParser.REQUEST, 0); }
		public RequestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_request; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterRequest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitRequest(this);
		}
	}

	public final RequestContext request() throws RecognitionException {
		RequestContext _localctx = new RequestContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_request);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167); match(REQUEST);
			setState(168); match(COLON);
			setState(169); boolExpr();
			net.request = stack.pop(); 
			setState(171); match(SEMICOLON);
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
		public TerminalNode ID() { return getToken(dNetParser.ID, 0); }
		public TerminalNode NUM() { return getToken(dNetParser.NUM, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitId(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_id);
		try {
			setState(177);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(173); ((IdContext)_localctx).var = match(ID);
				stack.push(new ConstraintNode((((IdContext)_localctx).var!=null?((IdContext)_localctx).var.getText():null)));
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(175); ((IdContext)_localctx).var = match(NUM);
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
		public TerminalNode RPAREN() { return getToken(dNetParser.RPAREN, 0); }
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(dNetParser.LPAREN, 0); }
		public EqContext eq() {
			return getRuleContext(EqContext.class,0);
		}
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterIdent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitIdent(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ident);
		try {
			setState(185);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(179); eq();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(180); match(LPAREN);
				setState(181); ((IdentContext)_localctx).ex = boolExpr();
				setState(182); match(RPAREN);

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
		public TerminalNode NEGATION() { return getToken(dNetParser.NEGATION, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_term);
		try {
			setState(194);
			switch (_input.LA(1)) {
			case LPAREN:
			case ID:
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(187); ((TermContext)_localctx).i = ident();

				}
				break;
			case NEGATION:
				enterOuterAlt(_localctx, 2);
				{
				setState(190); match(NEGATION);
				setState(191); ((TermContext)_localctx).i = ident();
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
		public TerminalNode AND() { return getToken(dNetParser.AND, 0); }
		public ConjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conj; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterConj(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitConj(this);
		}
	}

	public final ConjContext conj() throws RecognitionException {
		ConjContext _localctx = new ConjContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_conj);
		try {
			setState(204);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(196); ((ConjContext)_localctx).t = term();

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(199); ((ConjContext)_localctx).t = term();
				setState(200); match(AND);
				setState(201); ((ConjContext)_localctx).c = conj();
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
		public TerminalNode OR() { return getToken(dNetParser.OR, 0); }
		public DisjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disj; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterDisj(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitDisj(this);
		}
	}

	public final DisjContext disj() throws RecognitionException {
		DisjContext _localctx = new DisjContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_disj);
		try {
			setState(214);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(206); ((DisjContext)_localctx).c = conj();

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(209); ((DisjContext)_localctx).c = conj();
				setState(210); match(OR);
				setState(211); ((DisjContext)_localctx).d = disj();
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
		public TerminalNode RPAREN() { return getToken(dNetParser.RPAREN, 0); }
		public DisjContext disj() {
			return getRuleContext(DisjContext.class,0);
		}
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(dNetParser.LPAREN, 0); }
		public BoolExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitBoolExpr(this);
		}
	}

	public final BoolExprContext boolExpr() throws RecognitionException {
		BoolExprContext _localctx = new BoolExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_boolExpr);
		try {
			setState(221);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(216); ((BoolExprContext)_localctx).d = disj();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(217); match(LPAREN);
				setState(218); boolExpr();
				setState(219); match(RPAREN);
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
		public TerminalNode GEQ() { return getToken(dNetParser.GEQ, 0); }
		public TerminalNode MORE() { return getToken(dNetParser.MORE, 0); }
		public TerminalNode EQ() { return getToken(dNetParser.EQ, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode LEQ() { return getToken(dNetParser.LEQ, 0); }
		public TerminalNode LESS() { return getToken(dNetParser.LESS, 0); }
		public EqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitEq(this);
		}
	}

	public final EqContext eq() throws RecognitionException {
		EqContext _localctx = new EqContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_eq);
		try {
			setState(248);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(223); ((EqContext)_localctx).e1 = expr();
				setState(224); match(EQ);
				setState(225); ((EqContext)_localctx).e2 = expr();
				nwc2("=", stack.pop(), stack.pop());
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(228); ((EqContext)_localctx).e1 = expr();
				setState(229); match(LESS);
				setState(230); ((EqContext)_localctx).e2 = expr();
				nwc2("<", stack.pop(), stack.pop());
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(233); ((EqContext)_localctx).e1 = expr();
				setState(234); match(MORE);
				setState(235); ((EqContext)_localctx).e2 = expr();
				nwc2(">", stack.pop(), stack.pop());
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(238); ((EqContext)_localctx).e1 = expr();
				setState(239); match(GEQ);
				setState(240); ((EqContext)_localctx).e2 = expr();
				nwc2(">=", stack.pop(), stack.pop());
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(243); ((EqContext)_localctx).e1 = expr();
				setState(244); match(LEQ);
				setState(245); ((EqContext)_localctx).e2 = expr();
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
		public TerminalNode RPAREN() { return getToken(dNetParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(dNetParser.LPAREN, 0); }
		public Ident1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterIdent1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitIdent1(this);
		}
	}

	public final Ident1Context ident1() throws RecognitionException {
		Ident1Context _localctx = new Ident1Context(_ctx, getState());
		enterRule(_localctx, 40, RULE_ident1);
		try {
			setState(256);
			switch (_input.LA(1)) {
			case ID:
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(250); ((Ident1Context)_localctx).var = id();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(251); match(LPAREN);
				setState(252); ((Ident1Context)_localctx).ex = expr();
				setState(253); match(RPAREN);

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
		public TerminalNode DIV() { return getToken(dNetParser.DIV, 0); }
		public TerminalNode MULT() { return getToken(dNetParser.MULT, 0); }
		public MultContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public MultContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_mult; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitMult(this);
		}
	}

	public final MultContext mult(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultContext _localctx = new MultContext(_ctx, _parentState, _p);
		MultContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, RULE_mult);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(259); ((MultContext)_localctx).i = ident1();

			}
			_ctx.stop = _input.LT(-1);
			setState(274);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(272);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new MultContext(_parentctx, _parentState, _p);
						_localctx.m = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_mult);
						setState(262);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(263); match(MULT);
						setState(264); ((MultContext)_localctx).i = ident1();
						nwc2("*", stack.pop(), stack.pop());
						}
						break;

					case 2:
						{
						_localctx = new MultContext(_parentctx, _parentState, _p);
						_localctx.m = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_mult);
						setState(267);
						if (!(1 >= _localctx._p)) throw new FailedPredicateException(this, "1 >= $_p");
						setState(268); match(DIV);
						setState(269); ((MultContext)_localctx).i = ident1();
						nwc2("/", stack.pop(), stack.pop());
						}
						break;
					}
					} 
				}
				setState(276);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
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
		public TerminalNode PLUS() { return getToken(dNetParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(dNetParser.MINUS, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public SumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterSum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitSum(this);
		}
	}

	public final SumContext sum() throws RecognitionException {
		SumContext _localctx = new SumContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_sum);
		try {
			setState(290);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(277); mult(0);

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(280); ((SumContext)_localctx).m = mult(0);
				setState(281); match(PLUS);
				setState(282); ((SumContext)_localctx).s = sum();
				nwc2("+", stack.pop(), stack.pop());
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(285); ((SumContext)_localctx).m = mult(0);
				setState(286); match(MINUS);
				setState(287); ((SumContext)_localctx).s = sum();
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
		public TerminalNode RPAREN() { return getToken(dNetParser.RPAREN, 0); }
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(dNetParser.LPAREN, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_expr);
		try {
			setState(297);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(292); sum();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(293); match(LPAREN);
				setState(294); expr();
				setState(295); match(RPAREN);
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
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterCstr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitCstr(this);
		}
	}

	public final CstrContext cstr() throws RecognitionException {
		CstrContext _localctx = new CstrContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_cstr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299); boolExpr();
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

	public static class UtilityContext extends ParserRuleContext {
		public Utility resUtil;
		public SubUtilContext subUtil(int i) {
			return getRuleContext(SubUtilContext.class,i);
		}
		public List<SubUtilContext> subUtil() {
			return getRuleContexts(SubUtilContext.class);
		}
		public UtilityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_utility; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterUtility(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitUtility(this);
		}
	}

	public final UtilityContext utility() throws RecognitionException {
		UtilityContext _localctx = new UtilityContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_utility);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 utility = new Utility(); 
			setState(303); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(302); subUtil();
				}
				}
				setState(305); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NUM );
			((UtilityContext)_localctx).resUtil =  utility; 
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

	public static class SubUtilContext extends ParserRuleContext {
		public Token NUM;
		public TerminalNode SEMICOLON() { return getToken(dNetParser.SEMICOLON, 0); }
		public TerminalNode COMMA() { return getToken(dNetParser.COMMA, 0); }
		public TerminalNode NUM() { return getToken(dNetParser.NUM, 0); }
		public CstrContext cstr() {
			return getRuleContext(CstrContext.class,0);
		}
		public SubUtilContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subUtil; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).enterSubUtil(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof dNetListener ) ((dNetListener)listener).exitSubUtil(this);
		}
	}

	public final SubUtilContext subUtil() throws RecognitionException {
		SubUtilContext _localctx = new SubUtilContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_subUtil);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309); ((SubUtilContext)_localctx).NUM = match(NUM);
			setState(310); match(COMMA);
			setState(311); cstr();
			setState(312); match(SEMICOLON);
			utility.addValue((((SubUtilContext)_localctx).NUM!=null?((SubUtilContext)_localctx).NUM.getText():null), stack.pop());
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
		case 21: return mult_sempred((MultContext)_localctx, predIndex);
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
		"\2\3\37\u013e\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b"+
		"\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t"+
		"\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t"+
		"\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2"+
		"\3\2\7\2>\n\2\f\2\16\2A\13\2\3\2\3\2\7\2E\n\2\f\2\16\2H\13\2\3\3\3\3\3"+
		"\3\3\3\7\3N\n\3\f\3\16\3Q\13\3\3\3\3\3\3\4\3\4\3\4\3\5\6\5Y\n\5\r\5\16"+
		"\5Z\3\6\7\6^\n\6\f\6\16\6a\13\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7j\n\7\f"+
		"\7\16\7m\13\7\3\7\3\7\3\7\3\7\7\7s\n\7\f\7\16\7v\13\7\3\7\3\7\7\7z\n\7"+
		"\f\7\16\7}\13\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7"+
		"\b\u008c\n\b\f\b\16\b\u008f\13\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\13\3\13\3\13\3\f\6\f\u009e\n\f\r\f\16\f\u009f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\5\17\u00b4"+
		"\n\17\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00bc\n\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\5\21\u00c5\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\5\22\u00cf\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00d9\n"+
		"\23\3\24\3\24\3\24\3\24\3\24\5\24\u00e0\n\24\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u00fb\n\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\5\26\u0103\n\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\7\27\u0113\n\27\f\27\16\27\u0116\13\27\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0125\n\30"+
		"\3\31\3\31\3\31\3\31\3\31\5\31\u012c\n\31\3\32\3\32\3\33\3\33\6\33\u0132"+
		"\n\33\r\33\16\33\u0133\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\2"+
		"\35\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66\2\3\4"+
		"\16\16\26\26\u013d\28\3\2\2\2\4I\3\2\2\2\6T\3\2\2\2\bX\3\2\2\2\n_\3\2"+
		"\2\2\fb\3\2\2\2\16\u0081\3\2\2\2\20\u0093\3\2\2\2\22\u0096\3\2\2\2\24"+
		"\u0099\3\2\2\2\26\u009d\3\2\2\2\30\u00a1\3\2\2\2\32\u00a9\3\2\2\2\34\u00b3"+
		"\3\2\2\2\36\u00bb\3\2\2\2 \u00c4\3\2\2\2\"\u00ce\3\2\2\2$\u00d8\3\2\2"+
		"\2&\u00df\3\2\2\2(\u00fa\3\2\2\2*\u0102\3\2\2\2,\u0104\3\2\2\2.\u0124"+
		"\3\2\2\2\60\u012b\3\2\2\2\62\u012d\3\2\2\2\64\u012f\3\2\2\2\66\u0137\3"+
		"\2\2\289\b\2\1\29:\5\4\3\2:;\5\b\5\2;?\5\n\6\2<>\5\26\f\2=<\3\2\2\2>A"+
		"\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2A?\3\2\2\2BF\b\2\1\2CE\5\32\16\2"+
		"DC\3\2\2\2EH\3\2\2\2FD\3\2\2\2FG\3\2\2\2G\3\3\2\2\2HF\3\2\2\2IJ\7\5\2"+
		"\2JO\5\6\4\2KL\7\25\2\2LN\5\6\4\2MK\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2"+
		"\2\2PR\3\2\2\2QO\3\2\2\2RS\7\23\2\2S\5\3\2\2\2TU\7\16\2\2UV\b\4\1\2V\7"+
		"\3\2\2\2WY\5\f\7\2XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[\t\3\2\2\2"+
		"\\^\5\16\b\2]\\\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3\2\2\2`\13\3\2\2\2a_\3"+
		"\2\2\2bc\7\6\2\2cd\7\16\2\2de\b\7\1\2ef\7\b\2\2fk\5\20\t\2gh\7\25\2\2"+
		"hj\5\20\t\2ig\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2ln\3\2\2\2mk\3\2\2"+
		"\2no\7\t\2\2ot\5\22\n\2pq\7\25\2\2qs\5\22\n\2rp\3\2\2\2sv\3\2\2\2tr\3"+
		"\2\2\2tu\3\2\2\2u{\3\2\2\2vt\3\2\2\2wx\7\n\2\2xz\5\62\32\2yw\3\2\2\2z"+
		"}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|~\3\2\2\2}{\3\2\2\2~\177\b\7\1\2\177\u0080"+
		"\7\23\2\2\u0080\r\3\2\2\2\u0081\u0082\7\13\2\2\u0082\u0083\b\b\1\2\u0083"+
		"\u0084\7\b\2\2\u0084\u0085\7\16\2\2\u0085\u0086\7\t\2\2\u0086\u0087\7"+
		"\16\2\2\u0087\u0088\7\7\2\2\u0088\u008d\5\24\13\2\u0089\u008a\7\25\2\2"+
		"\u008a\u008c\5\24\13\2\u008b\u0089\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b"+
		"\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090"+
		"\u0091\b\b\1\2\u0091\u0092\7\23\2\2\u0092\17\3\2\2\2\u0093\u0094\7\16"+
		"\2\2\u0094\u0095\b\t\1\2\u0095\21\3\2\2\2\u0096\u0097\7\16\2\2\u0097\u0098"+
		"\b\n\1\2\u0098\23\3\2\2\2\u0099\u009a\t\2\2\2\u009a\u009b\b\13\1\2\u009b"+
		"\25\3\2\2\2\u009c\u009e\5\30\r\2\u009d\u009c\3\2\2\2\u009e\u009f\3\2\2"+
		"\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\27\3\2\2\2\u00a1\u00a2"+
		"\7\4\2\2\u00a2\u00a3\7\16\2\2\u00a3\u00a4\b\r\1\2\u00a4\u00a5\7\24\2\2"+
		"\u00a5\u00a6\5&\24\2\u00a6\u00a7\b\r\1\2\u00a7\u00a8\7\23\2\2\u00a8\31"+
		"\3\2\2\2\u00a9\u00aa\7\3\2\2\u00aa\u00ab\7\24\2\2\u00ab\u00ac\5&\24\2"+
		"\u00ac\u00ad\b\16\1\2\u00ad\u00ae\7\23\2\2\u00ae\33\3\2\2\2\u00af\u00b0"+
		"\7\16\2\2\u00b0\u00b4\b\17\1\2\u00b1\u00b2\7\17\2\2\u00b2\u00b4\b\17\1"+
		"\2\u00b3\u00af\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4\35\3\2\2\2\u00b5\u00bc"+
		"\5(\25\2\u00b6\u00b7\7\f\2\2\u00b7\u00b8\5&\24\2\u00b8\u00b9\7\r\2\2\u00b9"+
		"\u00ba\b\20\1\2\u00ba\u00bc\3\2\2\2\u00bb\u00b5\3\2\2\2\u00bb\u00b6\3"+
		"\2\2\2\u00bc\37\3\2\2\2\u00bd\u00be\5\36\20\2\u00be\u00bf\b\21\1\2\u00bf"+
		"\u00c5\3\2\2\2\u00c0\u00c1\7\22\2\2\u00c1\u00c2\5\36\20\2\u00c2\u00c3"+
		"\b\21\1\2\u00c3\u00c5\3\2\2\2\u00c4\u00bd\3\2\2\2\u00c4\u00c0\3\2\2\2"+
		"\u00c5!\3\2\2\2\u00c6\u00c7\5 \21\2\u00c7\u00c8\b\22\1\2\u00c8\u00cf\3"+
		"\2\2\2\u00c9\u00ca\5 \21\2\u00ca\u00cb\7\21\2\2\u00cb\u00cc\5\"\22\2\u00cc"+
		"\u00cd\b\22\1\2\u00cd\u00cf\3\2\2\2\u00ce\u00c6\3\2\2\2\u00ce\u00c9\3"+
		"\2\2\2\u00cf#\3\2\2\2\u00d0\u00d1\5\"\22\2\u00d1\u00d2\b\23\1\2\u00d2"+
		"\u00d9\3\2\2\2\u00d3\u00d4\5\"\22\2\u00d4\u00d5\7\20\2\2\u00d5\u00d6\5"+
		"$\23\2\u00d6\u00d7\b\23\1\2\u00d7\u00d9\3\2\2\2\u00d8\u00d0\3\2\2\2\u00d8"+
		"\u00d3\3\2\2\2\u00d9%\3\2\2\2\u00da\u00e0\5$\23\2\u00db\u00dc\7\f\2\2"+
		"\u00dc\u00dd\5&\24\2\u00dd\u00de\7\r\2\2\u00de\u00e0\3\2\2\2\u00df\u00da"+
		"\3\2\2\2\u00df\u00db\3\2\2\2\u00e0\'\3\2\2\2\u00e1\u00e2\5\60\31\2\u00e2"+
		"\u00e3\7\32\2\2\u00e3\u00e4\5\60\31\2\u00e4\u00e5\b\25\1\2\u00e5\u00fb"+
		"\3\2\2\2\u00e6\u00e7\5\60\31\2\u00e7\u00e8\7\33\2\2\u00e8\u00e9\5\60\31"+
		"\2\u00e9\u00ea\b\25\1\2\u00ea\u00fb\3\2\2\2\u00eb\u00ec\5\60\31\2\u00ec"+
		"\u00ed\7\34\2\2\u00ed\u00ee\5\60\31\2\u00ee\u00ef\b\25\1\2\u00ef\u00fb"+
		"\3\2\2\2\u00f0\u00f1\5\60\31\2\u00f1\u00f2\7\35\2\2\u00f2\u00f3\5\60\31"+
		"\2\u00f3\u00f4\b\25\1\2\u00f4\u00fb\3\2\2\2\u00f5\u00f6\5\60\31\2\u00f6"+
		"\u00f7\7\36\2\2\u00f7\u00f8\5\60\31\2\u00f8\u00f9\b\25\1\2\u00f9\u00fb"+
		"\3\2\2\2\u00fa\u00e1\3\2\2\2\u00fa\u00e6\3\2\2\2\u00fa\u00eb\3\2\2\2\u00fa"+
		"\u00f0\3\2\2\2\u00fa\u00f5\3\2\2\2\u00fb)\3\2\2\2\u00fc\u0103\5\34\17"+
		"\2\u00fd\u00fe\7\f\2\2\u00fe\u00ff\5\60\31\2\u00ff\u0100\7\r\2\2\u0100"+
		"\u0101\b\26\1\2\u0101\u0103\3\2\2\2\u0102\u00fc\3\2\2\2\u0102\u00fd\3"+
		"\2\2\2\u0103+\3\2\2\2\u0104\u0105\b\27\1\2\u0105\u0106\5*\26\2\u0106\u0107"+
		"\b\27\1\2\u0107\u0114\3\2\2\2\u0108\u0109\6\27\2\3\u0109\u010a\7\26\2"+
		"\2\u010a\u010b\5*\26\2\u010b\u010c\b\27\1\2\u010c\u0113\3\2\2\2\u010d"+
		"\u010e\6\27\3\3\u010e\u010f\7\27\2\2\u010f\u0110\5*\26\2\u0110\u0111\b"+
		"\27\1\2\u0111\u0113\3\2\2\2\u0112\u0108\3\2\2\2\u0112\u010d\3\2\2\2\u0113"+
		"\u0116\3\2\2\2\u0114\u0112\3\2\2\2\u0114\u0115\3\2\2\2\u0115-\3\2\2\2"+
		"\u0116\u0114\3\2\2\2\u0117\u0118\5,\27\2\u0118\u0119\b\30\1\2\u0119\u0125"+
		"\3\2\2\2\u011a\u011b\5,\27\2\u011b\u011c\7\30\2\2\u011c\u011d\5.\30\2"+
		"\u011d\u011e\b\30\1\2\u011e\u0125\3\2\2\2\u011f\u0120\5,\27\2\u0120\u0121"+
		"\7\31\2\2\u0121\u0122\5.\30\2\u0122\u0123\b\30\1\2\u0123\u0125\3\2\2\2"+
		"\u0124\u0117\3\2\2\2\u0124\u011a\3\2\2\2\u0124\u011f\3\2\2\2\u0125/\3"+
		"\2\2\2\u0126\u012c\5.\30\2\u0127\u0128\7\f\2\2\u0128\u0129\5\60\31\2\u0129"+
		"\u012a\7\r\2\2\u012a\u012c\3\2\2\2\u012b\u0126\3\2\2\2\u012b\u0127\3\2"+
		"\2\2\u012c\61\3\2\2\2\u012d\u012e\5&\24\2\u012e\63\3\2\2\2\u012f\u0131"+
		"\b\33\1\2\u0130\u0132\5\66\34\2\u0131\u0130\3\2\2\2\u0132\u0133\3\2\2"+
		"\2\u0133\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0136"+
		"\b\33\1\2\u0136\65\3\2\2\2\u0137\u0138\7\17\2\2\u0138\u0139\7\25\2\2\u0139"+
		"\u013a\5\62\32\2\u013a\u013b\7\23\2\2\u013b\u013c\b\34\1\2\u013c\67\3"+
		"\2\2\2\31?FOZ_kt{\u008d\u009f\u00b3\u00bb\u00c4\u00ce\u00d8\u00df\u00fa"+
		"\u0102\u0112\u0114\u0124\u012b\u0133";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}