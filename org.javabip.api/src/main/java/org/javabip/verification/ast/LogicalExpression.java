package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class LogicalExpression extends BinaryExpression implements ParsedJavaExpression {
    public LogicalExpression(ParsedJavaExpression left, ParsedJavaExpression right, String separator){
        super(left, right, separator);
        //separators = new HashSet<String>(){{add("&&");add("||");}};
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        try {
            Boolean left = (Boolean) leftExpression.accept(v);
            Boolean right = (Boolean) rightExpression.accept(v);

            switch (separator) {
                case "||": return left || right;
                case "&&": return left && right;

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

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
    }
}
