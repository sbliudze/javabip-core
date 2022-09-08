package org.javabip.verification.ast;

import org.javabip.verification.visitors.PJEEvaluateVisitor;

public interface ParsedJavaExpression {
    Object accept(PJEEvaluateVisitor v);
}
