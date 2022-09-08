package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public class DotSeparatedExpression implements ParsedJavaExpression {
    final ParsedJavaExpression left;
    final AfterDotExpression right;

    public DotSeparatedExpression(ParsedJavaExpression left, AfterDotExpression right) {
        this.left = left;
        this.right = right;
    }

    public String toString(){
        return left.toString() + "." + right.toString();
    }

    @Override
    public Object accept(PJEEvaluateVisitor v) {
        //basic case
        if (left instanceof ThisExpression){
            return right.accept(v);
        }
        if (left instanceof SuperExpression){
            //TODO communicate with the parent class
        }
        else {
            //TODO evaluate left part
        }

        return null;
    }

}
