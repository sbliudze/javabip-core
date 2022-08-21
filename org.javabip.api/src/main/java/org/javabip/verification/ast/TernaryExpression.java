package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.javabip.verification.visitors.PJEVisitor;

public class TernaryExpression implements ParsedJavaExpression {
    final ParsedJavaExpression condition;
    final ParsedJavaExpression trueExpression;
    final ParsedJavaExpression falseExpression;

    public TernaryExpression(ParsedJavaExpression condition, ParsedJavaExpression trueExpression, ParsedJavaExpression falseExpression) {
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    public String toString(){
        return condition.toString() +
                " ? " +
                trueExpression.toString() +
                " : " +
                falseExpression.toString();
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
