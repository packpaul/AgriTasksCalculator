/**
 * Copyright 2018 by Pavel Perminov (packpaul@mail.ru)
 */
package com.pp.agritasks.calculator;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.text.ParseException;

import org.junit.Test;

/**
 * Test cases for {@link Calculator}
 * 
 * @author Pavel Perminov (packpaul@mail.ru)
 */
public class CalculatorTest {
    
    @Test
    public void testSingleAddition() throws ParseException {
        
        final String expression = "34+ 45";
        
        BigInteger result = Calculator.calculate(expression);
        
        assertEquals(79, result.intValueExact());
    }
    
    @Test
    public void testSingleMultiplocation() throws ParseException {
        
        final String expression = "34 *45";
        
        BigInteger result = Calculator.calculate(expression);
        
        assertEquals(1530, result.intValueExact());
    }
    
    @Test
    public void testDirectOperationOrder() throws ParseException {
        
        final String expression = "34 *45 + 77 ";
        
        BigInteger result = Calculator.calculate(expression);
        
        assertEquals(1607, result.intValueExact());
    }
    
    @Test
    public void testReverseOperationOrder() throws ParseException {
        
        final String expression = "77 + 34 * 45";
        
        BigInteger result = Calculator.calculate(expression);
        
        assertEquals(1607, result.intValueExact());
    }
    
    @Test
    public void testLongerExpression() throws ParseException {
        
        final String expression = "77 + 34 * 45 + 66 + 44 * -45";
        
        BigInteger result = Calculator.calculate(expression);
        
        assertEquals(-307, result.intValueExact());
    }
    
    @Test
    public void testLongerExpression2() throws ParseException {
        
        final String expression = "77 + 34 + 45 + 66 * 44 * -45";
        
        BigInteger result = Calculator.calculate(expression);
        
        assertEquals(-130524, result.intValueExact());
    }
    
    @Test(expected = ParseException.class)
    public void testMalformedExpression() throws ParseException {
        
        final String expression = "77 + 34 *";
        
        Calculator.calculate(expression);
    }
    
    @Test(expected = ParseException.class)
    public void testMalformedExpression2() throws ParseException {
        
        final String expression = "77 34";
        
        Calculator.calculate(expression);
    }
    
}
