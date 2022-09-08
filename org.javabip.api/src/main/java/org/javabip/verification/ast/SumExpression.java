package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class SumExpression extends BinaryExpression implements ParsedJavaExpression {
    public SumExpression(ParsedJavaExpression left, ParsedJavaExpression right, String separator) {
        super(left, right, separator);
        //separators = new HashSet<String>(){{add("+");add("-");}};
    }


    @Override
    public Object accept(PJEEvaluateVisitor v) {
        try {
            Object left = leftExpression.accept(v);
            Object right = rightExpression.accept(v);

            if (left instanceof String && right instanceof String) {
                if (separator.equals("+"))
                    return left + (String) right;
                else {
                    //TODO exception, malformed
                    return null;
                }
            }

            if (left instanceof Number && right instanceof Number) {
                Double leftD = ((Number) left).doubleValue();
                Double rightD = ((Number) right).doubleValue();

                switch (separator) {
                    case "+":
                        return leftD + rightD;
                    case "-":
                        return leftD - rightD;

                    default: {
                        //TODO raise exception
                        return null;
                    }
                }
            }
        } catch (ClassCastException e) {
            //TODO process exception
        }

        return null;
    }
}
