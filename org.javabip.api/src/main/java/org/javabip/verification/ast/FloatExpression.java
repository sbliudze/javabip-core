package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class FloatExpression implements NumericLiteral {
    public Float value;
    public FloatType floatType;

    public FloatExpression(Float value, FloatType floatType) {
        this.value = value;
        this.floatType = floatType;
    }

    @Override
    public Double accept(PJEEvaluateVisitor v) {
        return value.doubleValue();
    }

    public enum FloatType { FLOAT, HEX_FLOAT }

    public String toString(){
        return value.toString();
    }


}
