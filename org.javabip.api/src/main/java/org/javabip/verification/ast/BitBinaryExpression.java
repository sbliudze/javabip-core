package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class BitBinaryExpression extends BinaryExpression implements ParsedJavaExpression {
    public BitBinaryExpression(ParsedJavaExpression left, ParsedJavaExpression right, String separator){
        super(left, right, separator);
        //separators = new HashSet<String>(){{add("<<");add(">>"); add(">>>");add("&");add("^");add("|");}};
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }
}
