package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class PostfixExpression implements UnaryExpression, ParsedJavaExpression {
    final ParsedJavaExpression expression;
    final String postfix;

    public PostfixExpression(ParsedJavaExpression expression, String postfix) {
        this.expression = expression;
        this.postfix = postfix;
    }

    public String toString(){
        return expression.toString() + postfix;
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }
}
