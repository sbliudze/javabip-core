package org.javabip.verification.visitors.test;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javabip.verification.ast.ParsedJavaExpression;
import org.javabip.verification.parser.JavaLexer;
import org.javabip.verification.parser.JavaParser;
import org.javabip.verification.visitors.ExpressionASTBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParsedJavaExpressionASTBuilderTest {
    private String go(String test) {
        JavaLexer lexer = new JavaLexer(CharStreams.fromString(test));
        JavaParser parser = new JavaParser(new CommonTokenStream(lexer));
        JavaParser.ExpressionContext expression = parser.expression();
        FakeSpecification f = new FakeSpecification(1,1,1, "1", true, new Double[]{1.0, 2.0});
        ExpressionASTBuilder v = new ExpressionASTBuilder(f);
        ParsedJavaExpression result = v.build(expression);
        System.out.println(result);
        return result.toString();
    }

    @Test
    public void testThis(){
        String test = "this";
        assertEquals(go(test), test);
    }

    @Test
    public void testSuper(){
        String test = "super";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralIntegerDecimal(){
        String test = "1";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralIntegerBinary(){
        //TODO
    }

    @Test
    public void testLiteralIntegerOct(){
        //TODO
    }

    @Test
    public void testLiteralIntegerHex(){
        //TODO
    }

    @Test
    public void testLiteralDouble(){
        String test = "1.0";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralFloat(){
        String test = "1.0";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralFloatHex(){
        //TODO
    }

    @Test
    public void testLiteralChar(){
        String test = "'a'";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralString(){
        String test = "\"Hello, world!\"";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralNull(){
        String test = "null";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralBoolTrue(){
        String test = "true";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralBoolFalse(){
        String test = "false";
        assertEquals(go(test), test);
    }

    @Test
    public void testLiteralTextBlock(){
        //TODO
    }

    @Test
    public void testPrimaryParentheses(){
        //TODO
    }

    @Test
    public void testIdentifier(){
        String test = "bet";
        assertEquals(go(test), test);
    }

    @Test
    public void testMethodId(){
        String test = "getWin()";
        assertEquals(go(test), test);
    }

    @Test
    public void testMethodThis(){
        String test = "this()";
        assertEquals(go(test), test);
    }

    @Test
    public void testMethodSuper(){
        String test = "super()";
        assertEquals(go(test), test);
    }

    /*@Test
    public void testMethodWithArguments(){
        String test = "go(true, 1)";
        assertEquals(go(test), test);
    }*/

    @Test
    public void testArray(){
        String test = "values[0]";
        assertEquals(go(test), test);
    }

    @Test
    public void testPostfix(){
        String test = "bet++";
        assertEquals(go(test), test);
    }

    @Test
    public void testPrefix(){
        String test = "!true";
        assertEquals(go(test), test);
    }

    @Test
    public void testSum(){
        String test = "1+bet";
        assertEquals(go(test), test);
    }

    @Test
    public void tesProduct(){
        String test = "this.bet*this.pot";
        assertEquals(go(test), test);
    }

    @Test
    public void tesEq(){
        String test = "win==true";
        assertEquals(go(test), test);
    }

    @Test
    public void tesNeq(){
        String test = "false!=true";
        assertEquals(go(test), test);
    }

    @Test
    public void tesDot(){
        String test = "this.win";
        assertEquals(go(test), test);
    }

    @Test
    public void tesRel(){
        String test = "pot>bet";
        assertEquals(go(test), test);
    }

    @Test
    public void tesLogic(){
        String test = "win||win";
        assertEquals(go(test), test);
    }
}
