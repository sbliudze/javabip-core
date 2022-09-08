package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class ProductExpression extends BinaryExpression implements ParsedJavaExpression {
    public ProductExpression(ParsedJavaExpression left, ParsedJavaExpression right, String separator){
        super(left, right, separator);
        //separators = new HashSet<String>(){{add("*");add("/");add("%");}};
    }

    @Override
    public Double accept(PJEEvaluateVisitor v) {
        try {
            double left = ((Number) leftExpression.accept(v)).doubleValue();
            double right = ((Number) rightExpression.accept(v)).doubleValue();

            switch (separator) {
                case "*": return left * right;
                case "/": {
                    if (right == 0) return null; //TODO exception?
                    return left / right;
                }
                case "%": return left % right;

                default: {
                    //TODO raise exception
                    return null;
                }
            }
        } catch (ClassCastException e){
            //TODO process exception
        }
        return null;
    }
}
