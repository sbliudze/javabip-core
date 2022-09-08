package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.javabip.verification.visitors.PJEVisitor;

public class PostfixExpression implements UnaryExpression, ParsedJavaExpression {
    final ParsedJavaExpression expression;
    final String postfix;

    public PostfixExpression(ParsedJavaExpression expression, String postfix) {
        this.expression = expression;
        this.postfix = postfix;
    }

    public String toString(){
        return expression.toString() + postfix;
    }

    //Set<String> postfixes = new HashSet<String>(){{add("-");add("+"); add("++");add("--");add("~");add("!");}};

    //boolean inPostfixes(String postfix){return postfixes.contains(postfix);}

    /*@Override
    public boolean evaluate(Class<?> componentClass, Object bipComponent) throws Exception {
        //TODO not implemented
        return false;
    }*/

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
    }
}
