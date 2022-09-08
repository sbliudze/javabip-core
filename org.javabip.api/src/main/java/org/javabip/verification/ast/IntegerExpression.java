package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class IntegerExpression implements NumericLiteral {
    public Integer value;
    public IntegerType integerType;

    public IntegerExpression(Integer value, IntegerType integerType) {
        this.value = value;
        this.integerType = integerType;
    }

    @Override
    public Double accept(PJEEvaluateVisitor v) {
        return value.doubleValue();
    }

    public enum IntegerType { DECIMAL_LITERAL, HEX_LITERAL, OCT_LITERAL, BINARY_LITERAL }

    public String toString(){
        return value.toString();
    }


}
