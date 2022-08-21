package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.javabip.verification.visitors.PJEVisitor;

public class BitBinaryExpression extends BinaryExpression implements ParsedJavaExpression {
    public BitBinaryExpression(ParsedJavaExpression left, ParsedJavaExpression right, String separator){
        super(left, right, separator);
        //separators = new HashSet<String>(){{add("<<");add(">>"); add(">>>");add("&");add("^");add("|");}};
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
        //could be
    }
}
