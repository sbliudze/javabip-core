package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.javabip.verification.visitors.PJEVisitor;

public class IdentifierExpression implements ParsedJavaExpression, AfterDotExpression, MethodCallBase {
    final String identifierName;

    public String getIdentifierName() {
        return identifierName;
    }

    public IdentifierExpression(String identifierName) {
        this.identifierName = identifierName;
    }

    /*@Override
    public boolean evaluate(Class<?> componentClass, Object bipComponent) throws Exception {
        //nothing to do here
        return true;
    }*/

    public String toString(){
        return identifierName;
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
    }
}
