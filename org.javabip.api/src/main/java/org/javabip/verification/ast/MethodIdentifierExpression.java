package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MethodIdentifierExpression implements ParsedJavaExpression, AfterDotExpression, MethodCallBase {
    final String identifierName;
    final Method associatedMethod;
    final Object bipComponent;

    public MethodIdentifierExpression(String identifierName, Method method, Object component) {
        this.identifierName = identifierName;
        this.associatedMethod = method;
        this.bipComponent = component;
    }

    public String toString(){
        return identifierName;
    }

    @Override
    public Object accept(PJEEvaluateVisitor v) {
        /*try {
            associatedField.setAccessible(true);
            return associatedField.get(bipComponent);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
