package com.temp.common.base.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * author lw
 * date 2019/7/18  15:53
 * discribe 测试bigdecimal
 */
public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal a1 = new BigDecimal(1000);
        BigDecimal a2 = new BigDecimal(2);
        System.out.println(a1.multiply(a2));
        System.out.println(a1.divide(a2));
        System.out.println(a1.divide(a2,new MathContext(2)));
        System.out.println(a1.multiply(a2,new MathContext(1)));

    }
}
