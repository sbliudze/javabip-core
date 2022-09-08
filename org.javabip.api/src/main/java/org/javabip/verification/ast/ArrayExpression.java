package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

// for expressions like expression[expression]
public class ArrayExpression implements ParsedJavaExpression {
    final ParsedJavaExpression outerExpression;
    final ParsedJavaExpression innerExpression;

    public ArrayExpression(ParsedJavaExpression array, ParsedJavaExpression value) {
        this.outerExpression = array;
        this.innerExpression = value;
    }

    public String toString(){
        return outerExpression.toString() + "[" + innerExpression.toString() + "]";
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }
}
