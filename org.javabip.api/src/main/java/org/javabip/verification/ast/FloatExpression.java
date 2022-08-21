package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
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

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
    }

    /*@Override
    public boolean evaluate(Class<?> componentClass, Object bipComponent) throws Exception {
        //TODO not implemented
        return false;
    }*/

    public enum FloatType { FLOAT, HEX_FLOAT }

    public String toString(){
        return value.toString();
    }


}
