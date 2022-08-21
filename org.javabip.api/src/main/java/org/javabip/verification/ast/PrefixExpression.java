package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.javabip.verification.visitors.PJEVisitor;

public class PrefixExpression implements ParsedJavaExpression {
    final ParsedJavaExpression expression;
    final String prefix;

    public PrefixExpression(ParsedJavaExpression expression, String prefix) {
        this.expression = expression;
        this.prefix = prefix;
    }

    public String toString(){
        return prefix + expression.toString();
    }

    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
    }

    //Set<String> prefixes = new HashSet<String>(){{add("--");add("++");}};
    //boolean inPrefixes(String prefix){return prefixes.contains(prefix);}
    /*@Override
    public boolean evaluate(Class<?> componentClass, Object bipComponent) throws Exception {
        //TODO not implemented
        return false;
    }*/
}
