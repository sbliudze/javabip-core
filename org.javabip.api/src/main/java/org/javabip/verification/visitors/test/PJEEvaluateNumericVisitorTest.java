package org.javabip.verification.visitors.test;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javabip.verification.ast.ParsedJavaExpression;
import org.javabip.verification.parser.JavaLexer;
import org.javabip.verification.parser.JavaParser;
import org.javabip.verification.visitors.ExpressionASTBuilder;
import org.javabip.verification.visitors.PJEEvaluateNumericVisitor;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.junit.Test;

import static org.junit.Assert.*;

public class PJEEvaluateNumericVisitorTest {
    private Number go(String test) {
        JavaLexer lexer = new JavaLexer(CharStreams.fromString(test));
        JavaParser parser = new JavaParser(new CommonTokenStream(lexer));
        JavaParser.ExpressionContext expression = parser.expression();
        ExpressionASTBuilder v = new ExpressionASTBuilder();
        ParsedJavaExpression invariantParsedExpression = v.build(expression);

        FakeInvariantImpl invariant = new FakeInvariantImpl(test, invariantParsedExpression);
        Number result = invariant.evaluateNumericExpression();
        
        System.out.println(result);
        return result;
    }

    static class FakeInvariantImpl {

        private final String expression;

        private final ParsedJavaExpression parsedExpression;

        public FakeInvariantImpl(String expression, ParsedJavaExpression invariantParsedExpression) {
            this.expression =expression;
            this.parsedExpression = invariantParsedExpression;
        }

        public String expr() {
            return expression;
        }

        public String toString() {
            return "Invariant=(expr = " + expr() + ")";
        }

        public Number evaluateNumericExpression() {
            return parsedExpression.accept(new PJEEvaluateNumericVisitor());
        }
    }

    @Test
    public void testBooleanExpressionFalse(){
        go("false");
        //null
    }

    @Test
    public void testBooleanExpressionTrue(){
        go("true");
        //null
    }
    
    @Test
    public void testArrayExpression(){
    }

    @Test
    public void testBinaryExpression () {
    }

    @Test
    public void testBooleanExpression(){
    }

    @Test
    public void testBitBinaryExpression(){

    }

    @Test
    public void testDotSeparatedExpression(){

    }

    @Test
    public void testEqualityExpression(){

    }

    @Test
    public void testFloatExpression(){

    }

    @Test
    public void testIdentifierExpression(){

    }

    @Test
    public void testIntegerExpression(){

    }

    @Test
    public void testLogicalExpression(){

    }

    @Test
    public void testMethodCallExpression(){

    }

    @Test
    public void testNullExpression(){

    }

    @Test
    public void testPostfixExpression(){

    }

    @Test
    public void testPrefixExpression(){

    }

    @Test
    public void testProductExpression(){

    }

    @Test
    public void testRelationalExpression(){

    }

    @Test
    public void testStringExpression(){

    }

    @Test
    public void testSumExpression(){

    }

    @Test
    public void testSuperExpression(){

    }

    @Test
    public void testTernaryExpression(){

    }

    @Test
    public void testThisExpression(){

    }
}
