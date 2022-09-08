package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class RelationalExpression extends BinaryExpression implements ParsedJavaExpression {
    public RelationalExpression(ParsedJavaExpression left, ParsedJavaExpression right, String separator) {
        super(left, right, separator);
        //separators = new HashSet<String>(){{add("<=");add("=>");add("<");add(">");}};
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        //here we cast the result to double as a "base class" of a Number. However, if big values are expected, it should be changed to BigDecimal

        try {
            Object leftResult = leftExpression.accept(v);
            Object rightResult = rightExpression.accept(v);

            if (leftResult != null && rightResult != null) {

                double left = ((Number) leftResult).doubleValue();
                double right = ((Number) rightResult).doubleValue();

                switch (separator) {
                    case ">":
                        return left > right;
                    case "<":
                        return left < right;
                    case ">=":
                        return left >= right;
                    case "<=":
                        return left <= right;

                    default: {
                        //TODO raise exception
                        return null;
                    }
                }
            } else return false;
        } catch (ClassCastException e){
            //TODO process exception
        }
        return null;
    }
}
