package org.javabip.verification.visitors;

import org.javabip.verification.ast.*;

public class PJEEvaluateVisitor implements PJEVisitor<Object> {
    public Boolean visit(ArrayExpression e){
        return false;
    }

    public Object visit(BooleanExpression e){
        return false;
    }
    public Object visit(BitBinaryExpression e){
        return false;
    }
    public Object visit(DotSeparatedExpression e){
        return false;
    }
    public Object visit(EqualityExpression e){
        return false;
    }
    public Object visit(FloatExpression e){
        return false;
    }
    public Object visit(IdentifierExpression e){
        return false;
    }
    public Object visit(IntegerExpression e){
        return false;
    }
    public Object visit(LogicalExpression e){
        return false;
    }
    public Object visit(MethodCallExpression e){
        return false;
    }
    public Object visit(NullExpression e){
        return false;
    }
    public Object visit(PostfixExpression e){
        return false;
    }
    public Object visit(PrefixExpression e){
        return false;
    }
    public Object visit(ProductExpression e){
        return false;
    }
    public Object visit(RelationalExpression e){
        return false;
    }
    public Object visit(StringExpression e){
        return false;
    }
    public Object visit(SumExpression e){
        return false;
    }
    public Object visit(SuperExpression e){
        return false;
    }
    public Object visit(TernaryExpression e){
        return false;
    }
    public Object visit(ThisExpression e){
        return false;
    }
}
