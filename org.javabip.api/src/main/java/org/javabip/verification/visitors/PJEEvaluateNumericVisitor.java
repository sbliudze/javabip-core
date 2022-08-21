package org.javabip.verification.visitors;

import org.javabip.verification.ast.*;

public class PJEEvaluateNumericVisitor implements PJEVisitor<Number> {
    public Number visit(ParsedJavaExpression e) {
        return e.accept(this);
    }

    public Number visit(ArrayExpression e){
        return null;
    }
    public Number visit(BooleanExpression e){
        return null;
    }
    public Number visit(BitBinaryExpression e){
        return null;
    }
    public Number visit(DotSeparatedExpression e){
        return null;
    }
    public Number visit(EqualityExpression e){
        return null;
    }
    public Number visit(FloatExpression e){
        return null;
    }
    public Number visit(IdentifierExpression e){
        return null;
    }
    public Number visit(IntegerExpression e){
        return null;
    }
    public Number visit(LogicalExpression e){
        return null;
    }
    public Number visit(MethodCallExpression e){
        return null;
    }
    public Number visit(NullExpression e){
        return null;
    }
    public Number visit(PostfixExpression e){
        return null;
    }
    public Number visit(PrefixExpression e){
        return null;
    }
    public Number visit(ProductExpression e){
        return null;
    }
    public Number visit(RelationalExpression e){
        return null;
    }
    public Number visit(StringExpression e){
        return null;
    }
    public Number visit(SumExpression e){
        return null;
    }
    public Number visit(SuperExpression e){
        return null;
    }
    public Number visit(TernaryExpression e){
        return null;
    }
    public Number visit(ThisExpression e){
        return null;
    }
}
