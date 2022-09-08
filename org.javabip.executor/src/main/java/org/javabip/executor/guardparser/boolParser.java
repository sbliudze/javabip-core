// Generated from /Users/lsafina/Projects/javabip-core/org.javabip.executor/src/main/java/org/javabip/executor/guardparser/bool.g4 by ANTLR 4.9.2
package org.javabip.executor.guardparser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.javabip.executor.GuardTreeNode;

import java.util.Stack;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class boolParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LPAREN=1, RPAREN=2, ID=3, UNION=4, INTERSECTION=5, NEGATION=6, WS=7;
	public static final int
		RULE_ident = 0, RULE_term = 1, RULE_manom = 2, RULE_expr_inner = 3, RULE_expr = 4, 
		RULE_formula = 5;
	private static String[] makeRuleNames() {
		return new String[] {
			"ident", "term", "manom", "expr_inner", "expr", "formula"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", null, "'|'", "'&'", "'!'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LPAREN", "RPAREN", "ID", "UNION", "INTERSECTION", "NEGATION", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "bool.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	  public Stack<GuardTreeNode> stack = new Stack<GuardTreeNode>();

	  public void nwc(String s, GuardTreeNode t){
		GuardTreeNode n = new GuardTreeNode(s);
	  	t.attachToNode(n);
	  	stack.push(n);
	  }
	  
	  public void nwc2(String s, GuardTreeNode t, GuardTreeNode t2){
		GuardTreeNode n = new GuardTreeNode(s);
	  	t.attachToNode(n);
	  	t2.attachToNode(n);
	  	stack.push(n);
	  }

	public boolParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class IdentContext extends ParserRuleContext {
		public Token var;
		public ExprContext ex;
		public TerminalNode ID() { return getToken(boolParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(boolParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(boolParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).enterIdent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).exitIdent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof boolVisitor ) return ((boolVisitor<? extends T>)visitor).visitIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_ident);
		try {
			setState(19);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(12);
				((IdentContext)_localctx).var = match(ID);
				stack.push(new GuardTreeNode((((IdentContext)_localctx).var!=null?((IdentContext)_localctx).var.getText():null)));
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(14);
				match(LPAREN);
				setState(15);
				((IdentContext)_localctx).ex = expr();
				setState(16);
				match(RPAREN);

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

	public static class TermContext extends ParserRuleContext {
		public IdentContext id;
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode NEGATION() { return getToken(boolParser.NEGATION, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof boolVisitor ) return ((boolVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_term);
		try {
			setState(28);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(21);
				((TermContext)_localctx).id = ident();

				}
				break;
			case NEGATION:
				enterOuterAlt(_localctx, 2);
				{
				setState(24);
				match(NEGATION);
				setState(25);
				((TermContext)_localctx).id = ident();
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

	public static class ManomContext extends ParserRuleContext {
		public TermContext t;
		public ManomContext m;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode INTERSECTION() { return getToken(boolParser.INTERSECTION, 0); }
		public ManomContext manom() {
			return getRuleContext(ManomContext.class,0);
		}
		public ManomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_manom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).enterManom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).exitManom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof boolVisitor ) return ((boolVisitor<? extends T>)visitor).visitManom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ManomContext manom() throws RecognitionException {
		ManomContext _localctx = new ManomContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_manom);
		try {
			setState(38);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(30);
				((ManomContext)_localctx).t = term();

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(33);
				((ManomContext)_localctx).t = term();
				setState(34);
				match(INTERSECTION);
				setState(35);
				((ManomContext)_localctx).m = manom();
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

	public static class Expr_innerContext extends ParserRuleContext {
		public ManomContext m;
		public Expr_innerContext e;
		public ManomContext manom() {
			return getRuleContext(ManomContext.class,0);
		}
		public TerminalNode UNION() { return getToken(boolParser.UNION, 0); }
		public Expr_innerContext expr_inner() {
			return getRuleContext(Expr_innerContext.class,0);
		}
		public Expr_innerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_inner; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).enterExpr_inner(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).exitExpr_inner(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof boolVisitor ) return ((boolVisitor<? extends T>)visitor).visitExpr_inner(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr_innerContext expr_inner() throws RecognitionException {
		Expr_innerContext _localctx = new Expr_innerContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_expr_inner);
		try {
			setState(48);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				((Expr_innerContext)_localctx).m = manom();

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
				((Expr_innerContext)_localctx).m = manom();
				setState(44);
				match(UNION);
				setState(45);
				((Expr_innerContext)_localctx).e = expr_inner();
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

	public static class ExprContext extends ParserRuleContext {
		public Expr_innerContext expr_inner() {
			return getRuleContext(Expr_innerContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(boolParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(boolParser.RPAREN, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof boolVisitor ) return ((boolVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expr);
		try {
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				expr_inner();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				match(LPAREN);
				setState(52);
				expr();
				setState(53);
				match(RPAREN);
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

	public static class FormulaContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof boolListener ) ((boolListener)listener).exitFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof boolVisitor ) return ((boolVisitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_formula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			expr();
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\t>\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\26"+
		"\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\37\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\5\4)\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\63\n\5\3\6\3\6\3\6"+
		"\3\6\3\6\5\6:\n\6\3\7\3\7\3\7\2\2\b\2\4\6\b\n\f\2\2\2<\2\25\3\2\2\2\4"+
		"\36\3\2\2\2\6(\3\2\2\2\b\62\3\2\2\2\n9\3\2\2\2\f;\3\2\2\2\16\17\7\5\2"+
		"\2\17\26\b\2\1\2\20\21\7\3\2\2\21\22\5\n\6\2\22\23\7\4\2\2\23\24\b\2\1"+
		"\2\24\26\3\2\2\2\25\16\3\2\2\2\25\20\3\2\2\2\26\3\3\2\2\2\27\30\5\2\2"+
		"\2\30\31\b\3\1\2\31\37\3\2\2\2\32\33\7\b\2\2\33\34\5\2\2\2\34\35\b\3\1"+
		"\2\35\37\3\2\2\2\36\27\3\2\2\2\36\32\3\2\2\2\37\5\3\2\2\2 !\5\4\3\2!\""+
		"\b\4\1\2\")\3\2\2\2#$\5\4\3\2$%\7\7\2\2%&\5\6\4\2&\'\b\4\1\2\')\3\2\2"+
		"\2( \3\2\2\2(#\3\2\2\2)\7\3\2\2\2*+\5\6\4\2+,\b\5\1\2,\63\3\2\2\2-.\5"+
		"\6\4\2./\7\6\2\2/\60\5\b\5\2\60\61\b\5\1\2\61\63\3\2\2\2\62*\3\2\2\2\62"+
		"-\3\2\2\2\63\t\3\2\2\2\64:\5\b\5\2\65\66\7\3\2\2\66\67\5\n\6\2\678\7\4"+
		"\2\28:\3\2\2\29\64\3\2\2\29\65\3\2\2\2:\13\3\2\2\2;<\5\n\6\2<\r\3\2\2"+
		"\2\7\25\36(\629";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}