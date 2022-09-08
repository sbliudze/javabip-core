package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.javabip.verification.visitors.PJEVisitor;

public class DotSeparatedExpression implements ParsedJavaExpression {
    final ParsedJavaExpression left;
    final AfterDotExpression right;

    public DotSeparatedExpression(ParsedJavaExpression left, AfterDotExpression right) {
        this.left = left;
        this.right = right;
    }

    public String toString(){
        return left.toString() + "." + right.toString();
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
    }

    /*@Override
    public boolean evaluate(Class<?> componentClass, Object bipComponent) throws Exception {
        //TODO not implemented
        return false;
    }*/

}
