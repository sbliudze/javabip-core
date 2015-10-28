grammar constraint;

@header
{
package org.bip.resources.grammar;
import java.util.*;
import java.util.*;
import org.bip.resources.ConstraintNode;
import org.bip.resources.Constraint;
}

@members{
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
}

constraint returns [ConstraintNode result]:
cstr {constraint = stack.pop(); }
	;


LPAREN : '(';
RPAREN : ')';
ID : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;
NUM : ('0'..'9')+;
OR : '|';
AND : '&'; 
NEGATION : '!';
SEMICOLON : ';';
COLON : ':';
COMMA : ',';
MULT : '*'; 
DIV : '/';
PLUS : '+';
MINUS : '-';
EQ : '=';
LESS : '<';
MORE : '>';
GEQ : '>=';
LEQ : '<=';

WS : [ \t\r\n]+ -> skip;

id : var=ID {stack.push(new ConstraintNode($var.text));} | var=NUM {stack.push(new ConstraintNode($var.text));};

ident : eq | LPAREN ex=boolExpr RPAREN {};
term : i=ident {} | NEGATION i=ident {nwc("!", stack.pop()); } ;
conj : t=term {} | t=term AND c=conj {nwc2("&", stack.pop(), stack.pop());};
disj : c=conj {} | c=conj OR d=disj {nwc2("|", stack.pop(), stack.pop());};
boolExpr : d=disj | LPAREN boolExpr RPAREN;

eq : e1=expr EQ e2=expr {nwc2("=", stack.pop(), stack.pop());} | e1=expr LESS e2=expr {nwc2("<", stack.pop(), stack.pop());} | e1=expr MORE e2=expr {nwc2(">", stack.pop(), stack.pop());}  |
 e1=expr GEQ e2=expr {nwc2(">=", stack.pop(), stack.pop());} | e1=expr LEQ e2=expr {nwc2("<=", stack.pop(), stack.pop());};

ident1 : var=id | LPAREN ex=expr RPAREN {};
mult : i=ident1 {} | m=mult MULT i=ident1 {nwc2("*", stack.pop(), stack.pop());} | m=mult DIV i=ident1 {nwc2("/", stack.pop(), stack.pop());};
sum : mult {} | m=mult PLUS s=sum {nwc2("+", stack.pop(), stack.pop());} | m=mult MINUS s=sum {nwc2("-", stack.pop(), stack.pop());};
expr : sum | LPAREN expr RPAREN;


cstr : boolExpr;

/* BIG QUESTION:
what do we do with utility and costs which are not really boolean, but in fact have different values 
for different conditions?
Question: is it always a number? Yes.
So actually, it is as follows:
there is a map value - condition.
Now the question is how we satisfy the global utility using z3 and if it is possible to do both (maximize and satisfy)*/

