package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class NullExpression implements Literal, ParsedJavaExpression {
    public String toString(){
        return "null";
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }
}
