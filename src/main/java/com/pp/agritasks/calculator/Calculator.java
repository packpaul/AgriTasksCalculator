/**
 * Copyright 2018 by Pavel Perminov (packpaul@mail.ru)
 */
package com.pp.agritasks.calculator;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.StringTokenizer;

import com.pp.agritasks.calculator.utils.BigIntegerAccumulator;

/**
 * Calculator class that evaluates results for passed in expressions.
 * Expressions are to contain only <b>+</b> and <b>*</b> operations. 
 * 
 * Two result accumulators <b>S</b> and <b>M</b> are used: <b>S</b> stores result of addition and <b>M</b> -
 * the one of multiplication. By the end <b>S</b> would contain the result of passed in expression evaluation.
 * 
 * @author Pavel Perminov (packpaul@mail.ru)
 */
public class Calculator {
    
    private State state = State.WAITING_NUMBER;
    private int tokenCount = 0;
    
    private BigIntegerAccumulator s = new BigIntegerAccumulator();
    private BigIntegerAccumulator m = new BigIntegerAccumulator();
    
    public static BigInteger calculate(String expression) throws ParseException {
        return new Calculator().evaluate(expression).buildResult();
    }

    public Calculator evaluate(String expression) throws ParseException {
        
        StringTokenizer tokenizer = new StringTokenizer(expression, " +*", true);
        
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            tokenCount++;
            switch(token) {
                case " ": {
                    continue;
                }
                case "+": {
                    if (state != State.WAITING_OPERATION) {
                        throw new ParseException(
                                "Number expected but operation '+' found at token position!",
                                tokenCount);
                    }
                    processAddition();
                    break;
                }
                case "*": {
                    if (state != State.WAITING_OPERATION) {
                        throw new ParseException(
                                "Number expected but operation '*' found at token position!",
                                tokenCount);
                    }
                    processMultiplication();
                    break;
                }
                default: {
                    if (state != State.WAITING_NUMBER) {
                        throw new ParseException(String.format(
                                "Operation '+' or '*' expected but number %s found at token position!", token),
                                tokenCount);
                    }
                    processNumber(Integer.parseInt(token));
                }
            }
        }
        
        return this;
    }
    
    private void processAddition() {
        if (! m.isZero()) {
            s.add(m);
            m.resetToZero();
        }
        
        state = State.WAITING_NUMBER;
    }

    private void processMultiplication() {
        state = State.WAITING_NUMBER;
    }

    private void processNumber(int number) {
        if (m.isZero()) {
            m.add(number);
        } else {
            m.multiply(number);
        }
        
        state = State.WAITING_OPERATION;
    }

    public BigInteger buildResult() throws ParseException {
        
        if (state != State.WAITING_OPERATION) {
            throw new ParseException("The expression is incomplete, number is expected as last token!", tokenCount);            
        }
        
        processAddition();
        
        return s.getValue();
    }
    
    private enum State { WAITING_NUMBER, WAITING_OPERATION };
 
}