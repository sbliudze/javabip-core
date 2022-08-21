package org.javabip.verification.visitors;

import org.javabip.verification.ast.*;

public interface PJEVisitor<R> {
    R visit(ArrayExpression e);
    R visit(BooleanExpression e);
    R visit(BitBinaryExpression e);
    R visit(DotSeparatedExpression e);
    R visit(EqualityExpression e);
    R visit(FloatExpression e);
    R visit(IdentifierExpression e);
    R visit(IntegerExpression e);
    R visit(LogicalExpression e);
    R visit(MethodCallExpression e);
    R visit(NullExpression e);
    R visit(PostfixExpression e);
    R visit(PrefixExpression e);
    R visit(ProductExpression e);
    R visit(RelationalExpression e);
    R visit(StringExpression e);
    R visit(SumExpression e);
    R visit(SuperExpression e);
    R visit(TernaryExpression e);
    R visit(ThisExpression e);
}
