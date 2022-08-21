package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.javabip.verification.visitors.PJEVisitor;

// for expressions like expression[expression]
public class ArrayExpression implements ParsedJavaExpression {
    final ParsedJavaExpression outerExpression;
    final ParsedJavaExpression innerExpression;

    public ArrayExpression(ParsedJavaExpression array, ParsedJavaExpression value) {
        this.outerExpression = array;
        this.innerExpression = value;
    }

    /*@Override
    public boolean evaluate(Class<?> componentClass, Object bipComponent) throws Exception {
        //TODO not implemented
        return false;
    }*/

    public String toString(){
        return outerExpression.toString() + "[" + innerExpression.toString() + "]";
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
        //could be: a[i]
    }
}
