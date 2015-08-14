// Generated from dNet.g by ANTLR 4.0

package org.bip.resources.grammar;
import java.util.*;
import java.util.*;

import org.bip.resources.Constraint;
import org.bip.resources.ConstraintNode;
import org.bip.resources.DNet;
import org.bip.resources.DNetException;
import org.bip.resources.InhibitorArc;
import org.bip.resources.Place;
import org.bip.resources.Transition;
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
		RULE_cstr = 24;
	public static final String[] ruleNames = {
		"net", "places", "place", "transitions", "inhibitors", "transition", "inhibitor", 
		"in_place", "out_place", "inh_tr", "costs", "cost", "request", "id", "ident", 
		"term", "conj", "disj", "boolExpr", "eq", "ident1", "mult", "sum", "expr", 
		"cstr"
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
		
		public Stack<ConstraintNode> stack = new Stack<ConstraintNode>();

	  public void nwc(String s, ConstraintNode t){
	  	ConstraintNode n = new ConstraintNode(s, net);
	  	t.attachToRight(n);
	  	stack.push(n);
	  }
	  
	  public void nwc2(String s, ConstraintNode left, ConstraintNode right) {
		ConstraintNode n = new ConstraintNode(s, net);
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
			setState(51); places();
			setState(52); transitions();
			setState(53); inhibitors();
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COST) {
				{
				{
				setState(54); costs();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			((NetContext)_localctx).resultNet =  net; 
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==REQUEST) {
				{
				{
				setState(61); request();
				}
				}
				setState(66);
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
			setState(67); match(KEY_PLACES);
			setState(68); place();
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(69); match(COMMA);
				setState(70); place();
				}
				}
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(76); match(SEMICOLON);
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
			setState(78); ((PlaceContext)_localctx).name1 = match(ID);

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
			setState(82); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(81); transition();
				}
				}
				setState(84); 
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
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==INHIBITOR) {
				{
				{
				setState(86); inhibitor();
				}
				}
				setState(91);
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
			setState(92); match(KEY_TRANSITION);
			setState(93); ((TransitionContext)_localctx).tr = match(ID);
			 
				  inPlaces = new ArrayList<Place>();
				  outPlaces = new ArrayList<Place>();
				  inhibitorRef = new ArrayList<String>();
				
			setState(95); match(FROM);
			setState(96); in_place();
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(97); match(COMMA);
				setState(98); in_place();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104); match(TO);
			setState(105); out_place();
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(106); match(COMMA);
				setState(107); out_place();
				}
				}
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CONSTRAINT) {
				{
				{
				setState(113); match(CONSTRAINT);
				setState(114); ((TransitionContext)_localctx).constraint = cstr();
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

				 		if (!stack.empty()) {
							net.addTransition((((TransitionContext)_localctx).tr!=null?((TransitionContext)_localctx).tr.getText():null), inPlaces, outPlaces,
									new Constraint(stack.pop(), net));
						} else {
							net.addTransition((((TransitionContext)_localctx).tr!=null?((TransitionContext)_localctx).tr.getText():null), inPlaces, outPlaces);
						}
				 
			setState(121); match(SEMICOLON);
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
			setState(123); match(INHIBITOR);
			inhibitorRef.clear();
			setState(125); match(FROM);
			setState(126); ((InhibitorContext)_localctx).place_name = match(ID);
			setState(127); match(TO);
			setState(128); ((InhibitorContext)_localctx).tr_name = match(ID);
			setState(129); match(REFERS);
			setState(130); inh_tr();
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(131); match(COMMA);
				setState(132); inh_tr();
				}
				}
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

				 	net.addInhibitor((((InhibitorContext)_localctx).place_name!=null?((InhibitorContext)_localctx).place_name.getText():null), (((InhibitorContext)_localctx).tr_name!=null?((InhibitorContext)_localctx).tr_name.getText():null), inhibitorRef); 
				 
			setState(139); match(SEMICOLON);
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
			setState(141); ((In_placeContext)_localctx).name = match(ID);
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
			setState(144); ((Out_placeContext)_localctx).name = match(ID);
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
			setState(147);
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
			setState(151); 
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(150); cost();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(153); 
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
			setState(155); match(COST);
			setState(156); ((CostContext)_localctx).name = match(ID);
			 Place pl = net.nameToPlace.get((((CostContext)_localctx).name!=null?((CostContext)_localctx).name.getText():null));
			setState(158); match(COLON);
			setState(159); boolExpr();
			pl.setCost(stack.pop());
			setState(161); match(SEMICOLON);
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
			setState(163); match(REQUEST);
			setState(164); match(COLON);
			setState(165); boolExpr();
			net.request = stack.pop(); 
			setState(167); match(SEMICOLON);
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
			setState(173);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(169); ((IdContext)_localctx).var = match(ID);
				stack.push(new ConstraintNode((((IdContext)_localctx).var!=null?((IdContext)_localctx).var.getText():null), net));
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(171); ((IdContext)_localctx).var = match(NUM);
				stack.push(new ConstraintNode((((IdContext)_localctx).var!=null?((IdContext)_localctx).var.getText():null), net));
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
			setState(181);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(175); eq();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(176); match(LPAREN);
				setState(177); ((IdentContext)_localctx).ex = boolExpr();
				setState(178); match(RPAREN);

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
			setState(190);
			switch (_input.LA(1)) {
			case LPAREN:
			case ID:
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(183); ((TermContext)_localctx).i = ident();

				}
				break;
			case NEGATION:
				enterOuterAlt(_localctx, 2);
				{
				setState(186); match(NEGATION);
				setState(187); ((TermContext)_localctx).i = ident();
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
			setState(200);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(192); ((ConjContext)_localctx).t = term();

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(195); ((ConjContext)_localctx).t = term();
				setState(196); match(AND);
				setState(197); ((ConjContext)_localctx).c = conj();
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
			setState(210);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(202); ((DisjContext)_localctx).c = conj();

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(205); ((DisjContext)_localctx).c = conj();
				setState(206); match(OR);
				setState(207); ((DisjContext)_localctx).d = disj();
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
			setState(217);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(212); ((BoolExprContext)_localctx).d = disj();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(213); match(LPAREN);
				setState(214); boolExpr();
				setState(215); match(RPAREN);
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
			setState(244);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(219); ((EqContext)_localctx).e1 = expr();
				setState(220); match(EQ);
				setState(221); ((EqContext)_localctx).e2 = expr();
				nwc2("=", stack.pop(), stack.pop());
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(224); ((EqContext)_localctx).e1 = expr();
				setState(225); match(LESS);
				setState(226); ((EqContext)_localctx).e2 = expr();
				nwc2("<", stack.pop(), stack.pop());
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(229); ((EqContext)_localctx).e1 = expr();
				setState(230); match(MORE);
				setState(231); ((EqContext)_localctx).e2 = expr();
				nwc2(">", stack.pop(), stack.pop());
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(234); ((EqContext)_localctx).e1 = expr();
				setState(235); match(GEQ);
				setState(236); ((EqContext)_localctx).e2 = expr();
				nwc2(">=", stack.pop(), stack.pop());
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(239); ((EqContext)_localctx).e1 = expr();
				setState(240); match(LEQ);
				setState(241); ((EqContext)_localctx).e2 = expr();
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
			setState(252);
			switch (_input.LA(1)) {
			case ID:
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(246); ((Ident1Context)_localctx).var = id();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(247); match(LPAREN);
				setState(248); ((Ident1Context)_localctx).ex = expr();
				setState(249); match(RPAREN);

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
			setState(255); ((MultContext)_localctx).i = ident1();

			}
			_ctx.stop = _input.LT(-1);
			setState(270);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(268);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new MultContext(_parentctx, _parentState, _p);
						_localctx.m = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_mult);
						setState(258);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(259); match(MULT);
						setState(260); ((MultContext)_localctx).i = ident1();
						nwc2("*", stack.pop(), stack.pop());
						}
						break;

					case 2:
						{
						_localctx = new MultContext(_parentctx, _parentState, _p);
						_localctx.m = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_mult);
						setState(263);
						if (!(1 >= _localctx._p)) throw new FailedPredicateException(this, "1 >= $_p");
						setState(264); match(DIV);
						setState(265); ((MultContext)_localctx).i = ident1();
						nwc2("/", stack.pop(), stack.pop());
						}
						break;
					}
					} 
				}
				setState(272);
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
			setState(286);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(273); mult(0);

				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(276); ((SumContext)_localctx).m = mult(0);
				setState(277); match(PLUS);
				setState(278); ((SumContext)_localctx).s = sum();
				nwc2("+", stack.pop(), stack.pop());
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(281); ((SumContext)_localctx).m = mult(0);
				setState(282); match(MINUS);
				setState(283); ((SumContext)_localctx).s = sum();
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
			setState(293);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(288); sum();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(289); match(LPAREN);
				setState(290); expr();
				setState(291); match(RPAREN);
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
			setState(295); boolExpr();
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
		"\2\3\37\u012c\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b"+
		"\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t"+
		"\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t"+
		"\27\4\30\t\30\4\31\t\31\4\32\t\32\3\2\3\2\3\2\3\2\3\2\7\2:\n\2\f\2\16"+
		"\2=\13\2\3\2\3\2\7\2A\n\2\f\2\16\2D\13\2\3\3\3\3\3\3\3\3\7\3J\n\3\f\3"+
		"\16\3M\13\3\3\3\3\3\3\4\3\4\3\4\3\5\6\5U\n\5\r\5\16\5V\3\6\7\6Z\n\6\f"+
		"\6\16\6]\13\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7f\n\7\f\7\16\7i\13\7\3\7"+
		"\3\7\3\7\3\7\7\7o\n\7\f\7\16\7r\13\7\3\7\3\7\7\7v\n\7\f\7\16\7y\13\7\3"+
		"\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u0088\n\b\f\b\16"+
		"\b\u008b\13\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\6"+
		"\f\u009a\n\f\r\f\16\f\u009b\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\5\17\u00b0\n\17\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\5\20\u00b8\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21"+
		"\u00c1\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00cb\n\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00d5\n\23\3\24\3\24\3\24"+
		"\3\24\3\24\5\24\u00dc\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u00f7\n\25\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u00ff\n"+
		"\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\7\27\u010f\n\27\f\27\16\27\u0112\13\27\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0121\n\30\3\31\3\31\3\31"+
		"\3\31\3\31\5\31\u0128\n\31\3\32\3\32\3\32\2\33\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&(*,.\60\62\2\3\4\16\16\26\26\u012c\2\64\3\2\2\2\4"+
		"E\3\2\2\2\6P\3\2\2\2\bT\3\2\2\2\n[\3\2\2\2\f^\3\2\2\2\16}\3\2\2\2\20\u008f"+
		"\3\2\2\2\22\u0092\3\2\2\2\24\u0095\3\2\2\2\26\u0099\3\2\2\2\30\u009d\3"+
		"\2\2\2\32\u00a5\3\2\2\2\34\u00af\3\2\2\2\36\u00b7\3\2\2\2 \u00c0\3\2\2"+
		"\2\"\u00ca\3\2\2\2$\u00d4\3\2\2\2&\u00db\3\2\2\2(\u00f6\3\2\2\2*\u00fe"+
		"\3\2\2\2,\u0100\3\2\2\2.\u0120\3\2\2\2\60\u0127\3\2\2\2\62\u0129\3\2\2"+
		"\2\64\65\b\2\1\2\65\66\5\4\3\2\66\67\5\b\5\2\67;\5\n\6\28:\5\26\f\298"+
		"\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<>\3\2\2\2=;\3\2\2\2>B\b\2\1\2?"+
		"A\5\32\16\2@?\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2C\3\3\2\2\2DB\3\2\2"+
		"\2EF\7\5\2\2FK\5\6\4\2GH\7\25\2\2HJ\5\6\4\2IG\3\2\2\2JM\3\2\2\2KI\3\2"+
		"\2\2KL\3\2\2\2LN\3\2\2\2MK\3\2\2\2NO\7\23\2\2O\5\3\2\2\2PQ\7\16\2\2QR"+
		"\b\4\1\2R\7\3\2\2\2SU\5\f\7\2TS\3\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2"+
		"W\t\3\2\2\2XZ\5\16\b\2YX\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\\13\3"+
		"\2\2\2][\3\2\2\2^_\7\6\2\2_`\7\16\2\2`a\b\7\1\2ab\7\b\2\2bg\5\20\t\2c"+
		"d\7\25\2\2df\5\20\t\2ec\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2hj\3\2\2"+
		"\2ig\3\2\2\2jk\7\t\2\2kp\5\22\n\2lm\7\25\2\2mo\5\22\n\2nl\3\2\2\2or\3"+
		"\2\2\2pn\3\2\2\2pq\3\2\2\2qw\3\2\2\2rp\3\2\2\2st\7\n\2\2tv\5\62\32\2u"+
		"s\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2xz\3\2\2\2yw\3\2\2\2z{\b\7\1\2"+
		"{|\7\23\2\2|\r\3\2\2\2}~\7\13\2\2~\177\b\b\1\2\177\u0080\7\b\2\2\u0080"+
		"\u0081\7\16\2\2\u0081\u0082\7\t\2\2\u0082\u0083\7\16\2\2\u0083\u0084\7"+
		"\7\2\2\u0084\u0089\5\24\13\2\u0085\u0086\7\25\2\2\u0086\u0088\5\24\13"+
		"\2\u0087\u0085\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a"+
		"\3\2\2\2\u008a\u008c\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008d\b\b\1\2\u008d"+
		"\u008e\7\23\2\2\u008e\17\3\2\2\2\u008f\u0090\7\16\2\2\u0090\u0091\b\t"+
		"\1\2\u0091\21\3\2\2\2\u0092\u0093\7\16\2\2\u0093\u0094\b\n\1\2\u0094\23"+
		"\3\2\2\2\u0095\u0096\t\2\2\2\u0096\u0097\b\13\1\2\u0097\25\3\2\2\2\u0098"+
		"\u009a\5\30\r\2\u0099\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u0099\3"+
		"\2\2\2\u009b\u009c\3\2\2\2\u009c\27\3\2\2\2\u009d\u009e\7\4\2\2\u009e"+
		"\u009f\7\16\2\2\u009f\u00a0\b\r\1\2\u00a0\u00a1\7\24\2\2\u00a1\u00a2\5"+
		"&\24\2\u00a2\u00a3\b\r\1\2\u00a3\u00a4\7\23\2\2\u00a4\31\3\2\2\2\u00a5"+
		"\u00a6\7\3\2\2\u00a6\u00a7\7\24\2\2\u00a7\u00a8\5&\24\2\u00a8\u00a9\b"+
		"\16\1\2\u00a9\u00aa\7\23\2\2\u00aa\33\3\2\2\2\u00ab\u00ac\7\16\2\2\u00ac"+
		"\u00b0\b\17\1\2\u00ad\u00ae\7\17\2\2\u00ae\u00b0\b\17\1\2\u00af\u00ab"+
		"\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\35\3\2\2\2\u00b1\u00b8\5(\25\2\u00b2"+
		"\u00b3\7\f\2\2\u00b3\u00b4\5&\24\2\u00b4\u00b5\7\r\2\2\u00b5\u00b6\b\20"+
		"\1\2\u00b6\u00b8\3\2\2\2\u00b7\u00b1\3\2\2\2\u00b7\u00b2\3\2\2\2\u00b8"+
		"\37\3\2\2\2\u00b9\u00ba\5\36\20\2\u00ba\u00bb\b\21\1\2\u00bb\u00c1\3\2"+
		"\2\2\u00bc\u00bd\7\22\2\2\u00bd\u00be\5\36\20\2\u00be\u00bf\b\21\1\2\u00bf"+
		"\u00c1\3\2\2\2\u00c0\u00b9\3\2\2\2\u00c0\u00bc\3\2\2\2\u00c1!\3\2\2\2"+
		"\u00c2\u00c3\5 \21\2\u00c3\u00c4\b\22\1\2\u00c4\u00cb\3\2\2\2\u00c5\u00c6"+
		"\5 \21\2\u00c6\u00c7\7\21\2\2\u00c7\u00c8\5\"\22\2\u00c8\u00c9\b\22\1"+
		"\2\u00c9\u00cb\3\2\2\2\u00ca\u00c2\3\2\2\2\u00ca\u00c5\3\2\2\2\u00cb#"+
		"\3\2\2\2\u00cc\u00cd\5\"\22\2\u00cd\u00ce\b\23\1\2\u00ce\u00d5\3\2\2\2"+
		"\u00cf\u00d0\5\"\22\2\u00d0\u00d1\7\20\2\2\u00d1\u00d2\5$\23\2\u00d2\u00d3"+
		"\b\23\1\2\u00d3\u00d5\3\2\2\2\u00d4\u00cc\3\2\2\2\u00d4\u00cf\3\2\2\2"+
		"\u00d5%\3\2\2\2\u00d6\u00dc\5$\23\2\u00d7\u00d8\7\f\2\2\u00d8\u00d9\5"+
		"&\24\2\u00d9\u00da\7\r\2\2\u00da\u00dc\3\2\2\2\u00db\u00d6\3\2\2\2\u00db"+
		"\u00d7\3\2\2\2\u00dc\'\3\2\2\2\u00dd\u00de\5\60\31\2\u00de\u00df\7\32"+
		"\2\2\u00df\u00e0\5\60\31\2\u00e0\u00e1\b\25\1\2\u00e1\u00f7\3\2\2\2\u00e2"+
		"\u00e3\5\60\31\2\u00e3\u00e4\7\33\2\2\u00e4\u00e5\5\60\31\2\u00e5\u00e6"+
		"\b\25\1\2\u00e6\u00f7\3\2\2\2\u00e7\u00e8\5\60\31\2\u00e8\u00e9\7\34\2"+
		"\2\u00e9\u00ea\5\60\31\2\u00ea\u00eb\b\25\1\2\u00eb\u00f7\3\2\2\2\u00ec"+
		"\u00ed\5\60\31\2\u00ed\u00ee\7\35\2\2\u00ee\u00ef\5\60\31\2\u00ef\u00f0"+
		"\b\25\1\2\u00f0\u00f7\3\2\2\2\u00f1\u00f2\5\60\31\2\u00f2\u00f3\7\36\2"+
		"\2\u00f3\u00f4\5\60\31\2\u00f4\u00f5\b\25\1\2\u00f5\u00f7\3\2\2\2\u00f6"+
		"\u00dd\3\2\2\2\u00f6\u00e2\3\2\2\2\u00f6\u00e7\3\2\2\2\u00f6\u00ec\3\2"+
		"\2\2\u00f6\u00f1\3\2\2\2\u00f7)\3\2\2\2\u00f8\u00ff\5\34\17\2\u00f9\u00fa"+
		"\7\f\2\2\u00fa\u00fb\5\60\31\2\u00fb\u00fc\7\r\2\2\u00fc\u00fd\b\26\1"+
		"\2\u00fd\u00ff\3\2\2\2\u00fe\u00f8\3\2\2\2\u00fe\u00f9\3\2\2\2\u00ff+"+
		"\3\2\2\2\u0100\u0101\b\27\1\2\u0101\u0102\5*\26\2\u0102\u0103\b\27\1\2"+
		"\u0103\u0110\3\2\2\2\u0104\u0105\6\27\2\3\u0105\u0106\7\26\2\2\u0106\u0107"+
		"\5*\26\2\u0107\u0108\b\27\1\2\u0108\u010f\3\2\2\2\u0109\u010a\6\27\3\3"+
		"\u010a\u010b\7\27\2\2\u010b\u010c\5*\26\2\u010c\u010d\b\27\1\2\u010d\u010f"+
		"\3\2\2\2\u010e\u0104\3\2\2\2\u010e\u0109\3\2\2\2\u010f\u0112\3\2\2\2\u0110"+
		"\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111-\3\2\2\2\u0112\u0110\3\2\2\2"+
		"\u0113\u0114\5,\27\2\u0114\u0115\b\30\1\2\u0115\u0121\3\2\2\2\u0116\u0117"+
		"\5,\27\2\u0117\u0118\7\30\2\2\u0118\u0119\5.\30\2\u0119\u011a\b\30\1\2"+
		"\u011a\u0121\3\2\2\2\u011b\u011c\5,\27\2\u011c\u011d\7\31\2\2\u011d\u011e"+
		"\5.\30\2\u011e\u011f\b\30\1\2\u011f\u0121\3\2\2\2\u0120\u0113\3\2\2\2"+
		"\u0120\u0116\3\2\2\2\u0120\u011b\3\2\2\2\u0121/\3\2\2\2\u0122\u0128\5"+
		".\30\2\u0123\u0124\7\f\2\2\u0124\u0125\5\60\31\2\u0125\u0126\7\r\2\2\u0126"+
		"\u0128\3\2\2\2\u0127\u0122\3\2\2\2\u0127\u0123\3\2\2\2\u0128\61\3\2\2"+
		"\2\u0129\u012a\5&\24\2\u012a\63\3\2\2\2\30;BKV[gpw\u0089\u009b\u00af\u00b7"+
		"\u00c0\u00ca\u00d4\u00db\u00f6\u00fe\u010e\u0110\u0120\u0127";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}