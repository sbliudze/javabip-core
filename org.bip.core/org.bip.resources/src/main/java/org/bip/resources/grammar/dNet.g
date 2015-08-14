grammar dNet;

@header
{
package org.bip.resources.grammar;
import java.util.*;
import java.util.*;
import org.bip.DNet.core.ConstraintNode;
import org.bip.DNet.core.Transition;
import org.bip.DNet.core.Place;
import org.bip.DNet.core.DNet;
import org.bip.DNet.core.InhibitorArc;
import org.bip.DNet.core.Constraint;
}

@members{
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
}

net returns [DNet resultNet]: { net = new DNet(); }
	places 
	transitions
	inhibitors
	costs*
	{$resultNet = net; }
	request*
	;

places:
	KEY_PLACES place (COMMA place)*
	SEMICOLON
;

place:
	name1=ID
	{
	  Place place = net.addPlace($name1.text);
	  net.nameToPlace.put($name1.text, place);
	  nameToPlace.put($name1.text, place);
	}
; 

transitions:  transition+;
inhibitors:  inhibitor*;

transition:
	KEY_TRANSITION 	tr=ID
	{ 
	  inPlaces = new ArrayList<Place>();
	  outPlaces = new ArrayList<Place>();
	  inhibitorRef = new ArrayList<String>();
	}
	 FROM in_place (COMMA in_place)* TO out_place (COMMA out_place)*

	 (CONSTRAINT constraint=cstr)*
	 	 {
	 		if (!stack.empty()) {
				net.addTransition($tr.text, inPlaces, outPlaces,
						new Constraint(stack.pop(), net));
			} else {
				net.addTransition($tr.text, inPlaces, outPlaces);
			}
	 }
	 
	SEMICOLON
;

inhibitor:
	INHIBITOR {inhibitorRef.clear();}

	 FROM place_name=ID  TO tr_name=ID
	REFERS inh_tr (COMMA inh_tr)*
	 {
	 	net.addInhibitor($place_name.text, $tr_name.text, inhibitorRef); 
	 }
	SEMICOLON
;

in_place: name=ID {inPlaces.add(new Place($name.text));}; 
out_place: name=ID {outPlaces.add(new Place($name.text));};

inh_tr: name=(ID | '*') {inhibitorRef.add($name.text);}; 

costs: cost+;


cost:	COST name=ID { Place pl = net.nameToPlace.get($name.text);}  COLON boolExpr {pl.setCost(stack.pop());}  
 /* Here we should have not a normal constraint, but a special cost function -> the 
 treatment in the constraint down should be different 
 it should be saved to an array of cost trees with two nodes*/
	    SEMICOLON
;

request: REQUEST COLON boolExpr {net.request = stack.pop(); }
SEMICOLON 
;

REQUEST : 'request' ;
COST : 'cost' ;
KEY_PLACES : 'places' ;
KEY_TRANSITION : 'transition' ;
REFERS : 'refers';
FROM : 'from' ;
TO: 'to' ;
CONSTRAINT: 'constraint' ;
INHIBITOR: 'inhibitor' ;
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

id : var=ID {stack.push(new ConstraintNode($var.text, net));} | var=NUM {stack.push(new ConstraintNode($var.text, net));};

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

