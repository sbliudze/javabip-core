package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class PrefixExpression implements ParsedJavaExpression {
    final ParsedJavaExpression expression;
    final String prefix;

    public PrefixExpression(ParsedJavaExpression expression, String prefix) {
        this.expression = expression;
        this.prefix = prefix;
    }

    public String toString(){
        return prefix + expression.toString();
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }
}
