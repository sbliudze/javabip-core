package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

import java.lang.reflect.Field;

public class IdentifierExpression implements ParsedJavaExpression, AfterDotExpression, MethodCallBase {
    final String identifierName;
    final Field associatedField;
    final Object bipComponent;

    public IdentifierExpression(String identifierName, Field field, Object component) {
        this.identifierName = identifierName;
        this.associatedField = field;
        this.bipComponent = component;
    }

    public String toString(){
        return identifierName;
    }

    @Override
    public Object accept(PJEEvaluateVisitor v) {
        try {
            associatedField.setAccessible(true);
            return associatedField.get(bipComponent);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
