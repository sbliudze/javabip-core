// Generated from constraint.g by ANTLR 4.0

package org.bip.resources.grammar;
import java.util.*;
import java.util.*;
import org.bip.resources.ConstraintNode;
import org.bip.resources.Constraint;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class constraintLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LPAREN=1, RPAREN=2, ID=3, NUM=4, OR=5, AND=6, NEGATION=7, SEMICOLON=8, 
		COLON=9, COMMA=10, MULT=11, DIV=12, PLUS=13, MINUS=14, EQ=15, LESS=16, 
		MORE=17, GEQ=18, LEQ=19, WS=20;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'('", "')'", "ID", "NUM", "'|'", "'&'", "'!'", "';'", "':'", "','", "'*'", 
		"'/'", "'+'", "'-'", "'='", "'<'", "'>'", "'>='", "'<='", "WS"
	};
	public static final String[] ruleNames = {
		"LPAREN", "RPAREN", "ID", "NUM", "OR", "AND", "NEGATION", "SEMICOLON", 
		"COLON", "COMMA", "MULT", "DIV", "PLUS", "MINUS", "EQ", "LESS", "MORE", 
		"GEQ", "LEQ", "WS"
	};


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


	public constraintLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "constraint.g"; }

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
		case 19: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\26b\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\3\3\3\3\4"+
		"\3\4\7\4\62\n\4\f\4\16\4\65\13\4\3\5\6\58\n\5\r\5\16\59\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\6"+
		"\25]\n\25\r\25\16\25^\3\25\3\25\2\26\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r"+
		"\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21"+
		"\1!\22\1#\23\1%\24\1\'\25\1)\26\2\3\2\5\5C\\aac|\6\62;C\\aac|\5\13\f\17"+
		"\17\"\"d\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\3+\3\2\2\2\5-\3\2\2\2"+
		"\7/\3\2\2\2\t\67\3\2\2\2\13;\3\2\2\2\r=\3\2\2\2\17?\3\2\2\2\21A\3\2\2"+
		"\2\23C\3\2\2\2\25E\3\2\2\2\27G\3\2\2\2\31I\3\2\2\2\33K\3\2\2\2\35M\3\2"+
		"\2\2\37O\3\2\2\2!Q\3\2\2\2#S\3\2\2\2%U\3\2\2\2\'X\3\2\2\2)\\\3\2\2\2+"+
		",\7*\2\2,\4\3\2\2\2-.\7+\2\2.\6\3\2\2\2/\63\t\2\2\2\60\62\t\3\2\2\61\60"+
		"\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\b\3\2\2\2\65\63"+
		"\3\2\2\2\668\4\62;\2\67\66\3\2\2\289\3\2\2\29\67\3\2\2\29:\3\2\2\2:\n"+
		"\3\2\2\2;<\7~\2\2<\f\3\2\2\2=>\7(\2\2>\16\3\2\2\2?@\7#\2\2@\20\3\2\2\2"+
		"AB\7=\2\2B\22\3\2\2\2CD\7<\2\2D\24\3\2\2\2EF\7.\2\2F\26\3\2\2\2GH\7,\2"+
		"\2H\30\3\2\2\2IJ\7\61\2\2J\32\3\2\2\2KL\7-\2\2L\34\3\2\2\2MN\7/\2\2N\36"+
		"\3\2\2\2OP\7?\2\2P \3\2\2\2QR\7>\2\2R\"\3\2\2\2ST\7@\2\2T$\3\2\2\2UV\7"+
		"@\2\2VW\7?\2\2W&\3\2\2\2XY\7>\2\2YZ\7?\2\2Z(\3\2\2\2[]\t\4\2\2\\[\3\2"+
		"\2\2]^\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_`\3\2\2\2`a\b\25\2\2a*\3\2\2\2\6\2"+
		"\639^";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}