package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class StringExpression implements Literal {
    public String value;
    public StringExpression(String value) {
        this.value = value;
    }

    public String toString(){
        return value;
    }

    @Override
    public String accept(PJEEvaluateVisitor v) {
        return value;
    }

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
    }
}
