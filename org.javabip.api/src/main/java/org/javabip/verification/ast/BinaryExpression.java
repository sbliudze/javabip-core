package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEVisitor;

abstract class BinaryExpression implements ParsedJavaExpression {
    public ParsedJavaExpression leftExpression;
    public ParsedJavaExpression rightExpression;
    public String separator;
    //public Set<String> separators;

    public BinaryExpression(ParsedJavaExpression leftExpression, ParsedJavaExpression rightExpression, String separator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.separator = separator;
    }

    public String toString(){
        return leftExpression.toString() + separator + rightExpression.toString();
    }

    /*public boolean inSeparators(String s) {
        return separators.contains(s);
    }*/

    /*public BinaryExpression(Expression leftExpression, Expression rightExpression, String separator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.separator = separator;
    }*/

    /*@Override
    public boolean evaluate(Class<?> componentClass, Object bipComponent) throws Exception {
        boolean evaluateL = leftExpression.evaluate(componentClass, bipComponent);
        boolean evaluateR = rightExpression.evaluate(componentClass, bipComponent);

        return false;
    }*/
}
