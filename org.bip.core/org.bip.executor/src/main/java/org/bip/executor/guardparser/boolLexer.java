// Generated from bool.g4 by ANTLR 4.0

package org.bip.executor.guardparser;
import java.util.*;

import org.bip.executor.GuardTreeNode;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class boolLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LPAREN=1, RPAREN=2, ID=3, UNION=4, INTERSECTION=5, NEGATION=6, WS=7;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'('", "')'", "ID", "'|'", "'&'", "'!'", "WS"
	};
	public static final String[] ruleNames = {
		"LPAREN", "RPAREN", "ID", "UNION", "INTERSECTION", "NEGATION", "WS"
	};


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


	public boolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "bool.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 6: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\t)\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3"+
		"\2\3\2\3\3\3\3\3\4\3\4\7\4\30\n\4\f\4\16\4\33\13\4\3\5\3\5\3\6\3\6\3\7"+
		"\3\7\3\b\6\b$\n\b\r\b\16\b%\3\b\3\b\2\t\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1"+
		"\r\b\1\17\t\2\3\2\5\5C\\aac|\6\62;C\\aac|\5\13\f\17\17\"\"*\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\3\21\3\2\2\2\5\23\3\2\2\2\7\25\3\2\2\2\t\34\3\2\2\2\13\36\3\2\2"+
		"\2\r \3\2\2\2\17#\3\2\2\2\21\22\7*\2\2\22\4\3\2\2\2\23\24\7+\2\2\24\6"+
		"\3\2\2\2\25\31\t\2\2\2\26\30\t\3\2\2\27\26\3\2\2\2\30\33\3\2\2\2\31\27"+
		"\3\2\2\2\31\32\3\2\2\2\32\b\3\2\2\2\33\31\3\2\2\2\34\35\7~\2\2\35\n\3"+
		"\2\2\2\36\37\7(\2\2\37\f\3\2\2\2 !\7#\2\2!\16\3\2\2\2\"$\t\4\2\2#\"\3"+
		"\2\2\2$%\3\2\2\2%#\3\2\2\2%&\3\2\2\2&\'\3\2\2\2\'(\b\b\2\2(\20\3\2\2\2"+
		"\5\2\31%";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}