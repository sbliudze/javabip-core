package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class ThisExpression implements ParsedJavaExpression, AfterDotExpression, MethodCallBase {
    public String toString(){
        return "this";
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }

}
