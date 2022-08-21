package org.javabip.verification.visitors;

import org.javabip.verification.ast.*;

public class PJEWellformednessChecker implements PJEVisitor<Boolean> {
    public Boolean visit(ArrayExpression e){
        return false;
    }
    public Boolean visit(BooleanExpression e){
        return false;
    }
    public Boolean visit(BitBinaryExpression e){
        return false;
    }
    public Boolean visit(DotSeparatedExpression e){
        return false;
    }
    public Boolean visit(EqualityExpression e){
        return false;
    }
    public Boolean visit(FloatExpression e){
        return false;
    }
    public Boolean visit(IdentifierExpression e){
        return false;
    }
    public Boolean visit(IntegerExpression e){
        return false;
    }
    public Boolean visit(LogicalExpression e){
        return false;
    }
    public Boolean visit(MethodCallExpression e){
        return false;
    }
    public Boolean visit(NullExpression e){
        return false;
    }
    public Boolean visit(PostfixExpression e){
        return false;
    }
    public Boolean visit(PrefixExpression e){
        return false;
    }
    public Boolean visit(ProductExpression e){
        return false;
    }
    public Boolean visit(RelationalExpression e){
        return false;
    }
    public Boolean visit(StringExpression e){
        return false;
    }
    public Boolean visit(SumExpression e){
        return false;
    }
    public Boolean visit(SuperExpression e){
        return false;
    }
    public Boolean visit(TernaryExpression e){
        return false;
    }
    public Boolean visit(ThisExpression e){
        return false;
    }
}
