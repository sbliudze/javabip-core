grammar bool;
@header
{
import java.util.*;
import org.javabip.executor.GuardTreeNode;
}

@members{
  public Stack<Node> stack = new Stack<Node>();

  public void nwc(String s, Node t){
  	Node n = new Node(s);
  	t.attachToNode(n);
  	stack.push(n);
  }
  
  public void nwc2(String s, Node t, Node t2){
  	Node n = new Node(s);
  	t.attachToNode(n);
  	t2.attachToNode(n);
  	stack.push(n);
  }
}

LPAREN : '(';
RPAREN : ')';
ID : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;
UNION : '|';
INTERSECTION : '&'; 
NEGATION : '!';

WS : [ \t\r\n]+ -> skip;

ident : var=ID {stack.push(new GuardTreeNode($var.text));} | LPAREN ex=expr RPAREN {};
term : id=ident {} | NEGATION id=ident {nwc("!", stack.pop()); } ;
manom : t=term {} | t=term INTERSECTION m=manom {nwc2("&", stack.pop(), stack.pop());};
expr_inner : m=manom {} | m=manom UNION e=expr_inner {nwc2("|", stack.pop(), stack.pop());};
expr : expr_inner | LPAREN expr RPAREN;

formula : expr;
