package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class EqualityExpression extends BinaryExpression implements ParsedJavaExpression {
    public EqualityExpression(ParsedJavaExpression left, ParsedJavaExpression right, String separator){
        super(left, right, separator);
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        Object left = leftExpression.accept(v);
        Object right = rightExpression.accept(v);

        switch (separator) {
            case "!=":
                return left != right;
            case "==": {
                if (left == null){
                    return right == null;
                } else return left.equals(right);
            }

            default: {
                //TODO throw exception
                return null;
            }
        }
    }
}
