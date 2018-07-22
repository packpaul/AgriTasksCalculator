/**
 * Copyright 2018 by Pavel Perminov (packpaul@mail.ru)
 */

package com.pp.agritasks.calculator.utils;

import java.math.BigInteger;

/**
 * Optimized class for accumulation of {@link BigInteger} values.
 * Since {@link BigInteger} is an immutable class for speeding up things
 * a temporal {@code int} value is used, and when it is deemed to exceed {@code Integer.MAX_VALUE}
 * it gets combined.
 * 
 * @author Pavel Perminov (packpaul@mail.ru)
 */
public class BigIntegerAccumulator {

    private BigInteger value = BigInteger.ZERO;
    private int tmpValue;
    
    public void resetToZero() {
        value = BigInteger.ZERO;
        tmpValue = 0;
    }
    
    public boolean isZero() {
        return ((tmpValue == 0) && (value.signum() == 0));
    }
    
    public BigInteger getValue() {
        normalize();
        return value;
    }
    
    private void normalize() {
        if (tmpValue != 0) {
            value = value.add(BigInteger.valueOf(tmpValue));
            tmpValue = 0;
        }
    }

    public void add(BigIntegerAccumulator other) {
        
        if (other.value.signum() != 0) {
            value = value.add(other.value);
        }
        
        add(other.tmpValue);
    }
    
    public void add(int number) {
        
        long tmpSum = (long) tmpValue + number;
        if (isExceedsIntegerRange(tmpSum)) {
            value = value.add(BigInteger.valueOf(tmpSum));
            tmpValue = 0;
        } else {
            tmpValue = (int) tmpSum;
        }
    }
    
    public void multiply(int number) {
        if (number == 0) {
            resetToZero();
            return;
        }
        
        if (value.signum() != 0) {
            value = value.multiply(BigInteger.valueOf(number));
        }
        
        long tmpMult = (long) tmpValue * number;
        if (isExceedsIntegerRange(tmpMult)) {
            value = value.add(BigInteger.valueOf(tmpMult));
            tmpValue = 0;
        } else {
            tmpValue = (int) tmpMult;
        }
    }
    
    private boolean isExceedsIntegerRange(long number) {
        return ((number > Integer.MAX_VALUE) || (number < Integer.MIN_VALUE));
    }
    
}
