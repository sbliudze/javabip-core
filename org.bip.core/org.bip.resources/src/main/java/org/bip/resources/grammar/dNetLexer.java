// Generated from dNet.g by ANTLR 4.0

package org.bip.resources.grammar;
import java.util.*;
import java.util.*;
import org.bip.resources.ConstraintNode;
import org.bip.resources.Transition;
import org.bip.resources.Place;
import org.bip.resources.DNet;
import org.bip.resources.InhibitorArc;
import org.bip.resources.Constraint;
import org.bip.resources.Utility;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class dNetLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		REQUEST=1, COST=2, KEY_PLACES=3, KEY_TRANSITION=4, REFERS=5, FROM=6, TO=7, 
		CONSTRAINT=8, INHIBITOR=9, LPAREN=10, RPAREN=11, ID=12, NUM=13, OR=14, 
		AND=15, NEGATION=16, SEMICOLON=17, COLON=18, COMMA=19, MULT=20, DIV=21, 
		PLUS=22, MINUS=23, EQ=24, LESS=25, MORE=26, GEQ=27, LEQ=28, WS=29;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'request'", "'cost'", "'places'", "'transition'", "'refers'", "'from'", 
		"'to'", "'constraint'", "'inhibitor'", "'('", "')'", "ID", "NUM", "'|'", 
		"'&'", "'!'", "';'", "':'", "','", "'*'", "'/'", "'+'", "'-'", "'='", 
		"'<'", "'>'", "'>='", "'<='", "WS"
	};
	public static final String[] ruleNames = {
		"REQUEST", "COST", "KEY_PLACES", "KEY_TRANSITION", "REFERS", "FROM", "TO", 
		"CONSTRAINT", "INHIBITOR", "LPAREN", "RPAREN", "ID", "NUM", "OR", "AND", 
		"NEGATION", "SEMICOLON", "COLON", "COMMA", "MULT", "DIV", "PLUS", "MINUS", 
		"EQ", "LESS", "MORE", "GEQ", "LEQ", "WS"
	};


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


	public dNetLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "dNet.g"; }

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
		case 28: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\37\u00b7\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36"+
		"\t\36\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\f\3\f\3\r\3\r\7\r\u0087\n\r\f\r\16\r\u008a\13\r\3\16\6\16\u008d"+
		"\n\16\r\16\16\16\u008e\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3"+
		"\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3"+
		"\32\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\6\36\u00b2\n\36\r\36"+
		"\16\36\u00b3\3\36\3\36\2\37\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t"+
		"\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1"+
		"#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67"+
		"\35\19\36\1;\37\2\3\2\5\5C\\aac|\6\62;C\\aac|\5\13\f\17\17\"\"\u00b9\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\3=\3\2\2\2\5E\3\2\2\2\7J\3\2\2\2\tQ\3\2\2\2\13\\\3\2\2\2\rc\3\2\2"+
		"\2\17h\3\2\2\2\21k\3\2\2\2\23v\3\2\2\2\25\u0080\3\2\2\2\27\u0082\3\2\2"+
		"\2\31\u0084\3\2\2\2\33\u008c\3\2\2\2\35\u0090\3\2\2\2\37\u0092\3\2\2\2"+
		"!\u0094\3\2\2\2#\u0096\3\2\2\2%\u0098\3\2\2\2\'\u009a\3\2\2\2)\u009c\3"+
		"\2\2\2+\u009e\3\2\2\2-\u00a0\3\2\2\2/\u00a2\3\2\2\2\61\u00a4\3\2\2\2\63"+
		"\u00a6\3\2\2\2\65\u00a8\3\2\2\2\67\u00aa\3\2\2\29\u00ad\3\2\2\2;\u00b1"+
		"\3\2\2\2=>\7t\2\2>?\7g\2\2?@\7s\2\2@A\7w\2\2AB\7g\2\2BC\7u\2\2CD\7v\2"+
		"\2D\4\3\2\2\2EF\7e\2\2FG\7q\2\2GH\7u\2\2HI\7v\2\2I\6\3\2\2\2JK\7r\2\2"+
		"KL\7n\2\2LM\7c\2\2MN\7e\2\2NO\7g\2\2OP\7u\2\2P\b\3\2\2\2QR\7v\2\2RS\7"+
		"t\2\2ST\7c\2\2TU\7p\2\2UV\7u\2\2VW\7k\2\2WX\7v\2\2XY\7k\2\2YZ\7q\2\2Z"+
		"[\7p\2\2[\n\3\2\2\2\\]\7t\2\2]^\7g\2\2^_\7h\2\2_`\7g\2\2`a\7t\2\2ab\7"+
		"u\2\2b\f\3\2\2\2cd\7h\2\2de\7t\2\2ef\7q\2\2fg\7o\2\2g\16\3\2\2\2hi\7v"+
		"\2\2ij\7q\2\2j\20\3\2\2\2kl\7e\2\2lm\7q\2\2mn\7p\2\2no\7u\2\2op\7v\2\2"+
		"pq\7t\2\2qr\7c\2\2rs\7k\2\2st\7p\2\2tu\7v\2\2u\22\3\2\2\2vw\7k\2\2wx\7"+
		"p\2\2xy\7j\2\2yz\7k\2\2z{\7d\2\2{|\7k\2\2|}\7v\2\2}~\7q\2\2~\177\7t\2"+
		"\2\177\24\3\2\2\2\u0080\u0081\7*\2\2\u0081\26\3\2\2\2\u0082\u0083\7+\2"+
		"\2\u0083\30\3\2\2\2\u0084\u0088\t\2\2\2\u0085\u0087\t\3\2\2\u0086\u0085"+
		"\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089"+
		"\32\3\2\2\2\u008a\u0088\3\2\2\2\u008b\u008d\4\62;\2\u008c\u008b\3\2\2"+
		"\2\u008d\u008e\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f\34"+
		"\3\2\2\2\u0090\u0091\7~\2\2\u0091\36\3\2\2\2\u0092\u0093\7(\2\2\u0093"+
		" \3\2\2\2\u0094\u0095\7#\2\2\u0095\"\3\2\2\2\u0096\u0097\7=\2\2\u0097"+
		"$\3\2\2\2\u0098\u0099\7<\2\2\u0099&\3\2\2\2\u009a\u009b\7.\2\2\u009b("+
		"\3\2\2\2\u009c\u009d\7,\2\2\u009d*\3\2\2\2\u009e\u009f\7\61\2\2\u009f"+
		",\3\2\2\2\u00a0\u00a1\7-\2\2\u00a1.\3\2\2\2\u00a2\u00a3\7/\2\2\u00a3\60"+
		"\3\2\2\2\u00a4\u00a5\7?\2\2\u00a5\62\3\2\2\2\u00a6\u00a7\7>\2\2\u00a7"+
		"\64\3\2\2\2\u00a8\u00a9\7@\2\2\u00a9\66\3\2\2\2\u00aa\u00ab\7@\2\2\u00ab"+
		"\u00ac\7?\2\2\u00ac8\3\2\2\2\u00ad\u00ae\7>\2\2\u00ae\u00af\7?\2\2\u00af"+
		":\3\2\2\2\u00b0\u00b2\t\4\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2"+
		"\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6"+
		"\b\36\2\2\u00b6<\3\2\2\2\6\2\u0088\u008e\u00b3";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}