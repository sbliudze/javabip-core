package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class SuperExpression implements ParsedJavaExpression, MethodCallBase {
    public String toString(){
        return "super";
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }
}
