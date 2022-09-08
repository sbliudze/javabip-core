package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.javabip.verification.visitors.PJEVisitor;

import java.util.ArrayList;

public class MethodCallExpression implements ParsedJavaExpression, AfterDotExpression {
    MethodCallBase methodCallBase;
    ArrayList<ParsedJavaExpression> arguments;

    public MethodCallExpression(MethodCallBase methodCallBase, ArrayList<ParsedJavaExpression> arguments) {
        this.methodCallBase = methodCallBase;
        this.arguments = arguments;
    }


    Boolean isThis(){
        return methodCallBase instanceof ThisExpression;
    }

    Boolean isSuper(){
        return methodCallBase instanceof  SuperExpression;
    }

    Boolean isIdentifier(){
        return methodCallBase instanceof IdentifierExpression;
    }


    /*@Override
    public boolean evaluate(Class<?> componentClass, Object bipComponent) throws Exception {
        //TODO not implemented
        return false;
    }*/

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(methodCallBase.toString()).append("(");
        arguments.forEach(a -> sb.append(a.toString()).append(", "));
        int length = sb.length();
        if (arguments.size()>0) sb.delete(length -2, length);
        sb.append(")");
        return sb.toString();
    }


    @Override
    public Boolean accept(PJEEvaluateVisitor v) {
        return null;
    }

    @Override
    public Number accept(PJEEvaluateNumericVisitor v) {
        return null;
    }
}
