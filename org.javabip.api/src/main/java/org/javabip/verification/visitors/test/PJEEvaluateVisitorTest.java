package org.javabip.verification.visitors.test;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javabip.annotations.*;
import org.javabip.verification.ast.*;
import org.javabip.verification.parser.JavaLexer;
import org.javabip.verification.parser.JavaParser;
import org.javabip.verification.visitors.ExpressionASTBuilder;
import org.javabip.verification.visitors.PJEEvaluateVisitor;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PJEEvaluateVisitorTest {
    private boolean go(String test) {
        JavaLexer lexer = new JavaLexer(CharStreams.fromString(test));
        JavaParser parser = new JavaParser(new CommonTokenStream(lexer));
        JavaParser.ExpressionContext expression = parser.expression();
        ExpressionASTBuilder v = new ExpressionASTBuilder();
        ParsedJavaExpression invariantParsedExpression = v.build(expression);

        FakeInvariantImpl invariant = new FakeInvariantImpl(test, invariantParsedExpression);
        Boolean result = invariant.evaluateInvariant();

        System.out.println(test);
        return result;
    }

    static class FakeInvariantImpl {
        private final String expression;
        private final ParsedJavaExpression parsedExpression;

        public FakeInvariantImpl(String expression, ParsedJavaExpression invariantParsedExpression) {
            this.expression = expression;
            this.parsedExpression = invariantParsedExpression;
        }

        public String expr() {
            return expression;
        }

        public String toString() {
            return "Invariant=(expr = " + expr() + ")";
        }

        public Boolean evaluateInvariant() {
            return (Boolean) parsedExpression.accept(new PJEEvaluateVisitor());
        }

        public Boolean evaluateInvariant(Class<?> componentClass, Object bipComponent) {
            return (Boolean) parsedExpression.accept(new PJEEvaluateVisitor());
        }
    }

    static class FakeSpecification {
        Float bet;
        Integer operator;
        int pot;
        String secretNumber;
        Boolean win;

        public FakeSpecification(Float bet, Integer operator, int pot, String secretNumber, Boolean win) {
            this.bet = bet;
            this.operator = operator;
            this.pot = pot;
            this.secretNumber = secretNumber;
            this.win = win;
        }

        public Float getBet() {
            return bet;
        }

        public void setBet(Float bet) {
            this.bet = bet;
        }

        public Integer getOperator() {
            return operator;
        }

        public void setOperator(Integer operator) {
            this.operator = operator;
        }

        public int getPot() {
            return pot;
        }

        public void setPot(int pot) {
            this.pot = pot;
        }

        public String getSecretNumber() {
            return secretNumber;
        }

        public void setSecretNumber(String secretNumber) {
            this.secretNumber = secretNumber;
        }

        public Boolean getWin() {
            return win;
        }

        public void setWin(Boolean win) {
            this.win = win;
        }
    }

    //region Basic Types Tests
    @Test
    public void testBooleanExpressionFalse() {
        String test = "true && false";
        assertFalse(go(test));
    }

    @Test
    public void testBooleanExpressionTrue() {
        String test = "true || false";
        assertTrue(go(test));
    }

    @Test
    public void testBooleanExpressionParent() {
        String test = "(true || false) && true";
        assertTrue(go(test));
    }

    @Test
    public void testNumberExpression() {
        String test = "5 > 1.0";
        assertTrue(go(test));
    }

    @Test
    public void testEqualityExpressionString() {
        String test = "\"apple\" != \"pear\"";
        assertTrue(go(test));
    }

    @Test
    public void testEqualityExpressionChar() {
        String test = "'a' == 'a'";
        assertTrue(go(test));
    }

    @Test
    public void testEqualityExpressionNumber() {
        String test = "1 == 1.0";
        assertTrue(go(test));
    }

    @Test
    public void testNullExpression() {
        String test = "\"null\" != null";
        assertTrue(go(test));
    }

    @Test
    public void testProductExpression() {
        String test = "1 * 1 == 1";
        assertTrue(go(test));
    }

    @Test
    public void testProductExpressionFail() {
        String test = "1 / 0 == null";
        assertTrue(go(test));
    }

    @Test
    public void testSumExpressionNumber() {
        String test = "1 + 1 == 2";
        assertTrue(go(test));
    }

    @Test
    public void testSumExpressionString() {
        String test = "\"1\" + \"1\" == \"11\"";
        assertTrue(go(test));
    }

    @Test
    public void testTernaryExpression() {
        //TODO ternary implementation does not work
        String test = "1 + 1 == 2 ? true : false";
        assertTrue(go(test));
    }

    @Test
    public void testBitBinaryExpression() { /*TODO to be implemented*/ }
    //endregion

    //region Field Accessing Tests
    @Test
    public void testIdentifierExpression() {
    }

    @Test
    public void testSuperExpression() {
        //TODO
        //in fact should not be supported
    }

    @Test
    public void testThisExpression() {
        //TODO
        // in fact "this.value" is just a syntactic sugar for "value"
        String test = "this.value";
        assertFalse(go(test));
    }

    @Test
    public void testMethodCallExpression() {
    }

    @Test
    public void testDotSeparatedExpression() {
    }

    @Test
    public void testArrayExpression() {
    }

    @Test
    public void testPostfixExpression() {
    }

    @Test
    public void testPrefixExpression() {
    }
    //endregion
}
