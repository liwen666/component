package com.temp.common.common.util;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class Exp4jUtilsTest {

    @Test
    public void name() {
        Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        double result = e.evaluate();
        System.out.println(result);

    }

    @Test
    public void name2() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(1);
        Expression e = new ExpressionBuilder("3log(y)/(x+1)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        Future<Double> future = e.evaluateAsync(exec);
        double result = future.get();
        System.out.println(result);
    }

    @Test
    public void name3() {
        double result = new ExpressionBuilder("2cos(xy)")
                .variables("x", "y")
                .build()
                .setVariable("x", 0.5d)
                .setVariable("y", 0.25d)
                .evaluate();
        assertEquals(2d * Math.cos(0.5d * 0.25d), result, 0d);
    }

    @Test
    public void name4() {
        String expr = "pi+π+e+φ";
        double expected = 2 * Math.PI + Math.E + 1.61803398874d;
        Expression e = new ExpressionBuilder(expr).build();
        assertEquals(expected, e.evaluate(), 0d);

    }

    @Test
    public void name5() {
        String expr = "7.2973525698e-3";
        double expected = Double.parseDouble(expr);
        Expression e = new ExpressionBuilder(expr).build();
        assertEquals(expected, e.evaluate(), 0d);
        System.out.println(expected);
    }

    @Test
    public void name6() {
        Function logb = new Function("logb", 2) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(args[1]);
            }
        };
        double result = new ExpressionBuilder("logb(8, 2)")
                .function(logb)
                .build()
                .evaluate();
        double expected = 3;
        assertEquals(expected, result, 0d);
        System.out.println(result);
    }

    @Test
    public void name7() {
        Function avg = new Function("avg", 4) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        double result = new ExpressionBuilder("avg(1,2,3,4)")
                .function(avg)
                .build()
                .evaluate();

        double expected = 2.5d;
        assertEquals(expected, result, 0d);
    }

    @Test
    public void name8() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        double result = new ExpressionBuilder("3!")
                .operator(factorial)
                .build()
                .evaluate();

        double expected = 6d;
        assertEquals(expected, result, 0d);
    }


    @Test
    public void testOperators3() throws Exception {
        Operator gteq = new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };

        Expression e = new ExpressionBuilder("1>=2").operator(gteq)
                .build();
        assertTrue(0d == e.evaluate());
        e = new ExpressionBuilder("2>=1").operator(gteq)
                .build();
        assertTrue(1d == e.evaluate());
    }

    @Test
    public void name10() {
        Operator reciprocal = new Operator("$", 1, true, Operator.PRECEDENCE_DIVISION) {
            @Override
            public double apply(final double... args) {
                if (args[0] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return 1d / args[0];
            }
        };
        Expression e = new ExpressionBuilder("100$").operator(reciprocal).build();
        double evaluate = e.evaluate();// <- this call will throw an ArithmeticException
        System.out.println(evaluate);
    }

    @Test
    public void name11() {
        Expression e = new ExpressionBuilder("x")
                .variable("x")
                .build();

        ValidationResult res = e.validate();
        assertFalse(res.isValid());
        assertEquals(1, res.getErrors().size());

        e.setVariable("x",1d);
        res = e.validate();
        assertTrue(res.isValid());
    }

    @Test
    public void name12() {
        Expression e = new ExpressionBuilder("x")
                .variable("x")
                .build();

        ValidationResult res = e.validate(false);
        assertTrue(res.isValid());
        assertNull(res.getErrors());
    }

    @Test
    public void max() {
        Function avg = new Exp4jFunction("max");
        Expression xx = new ExpressionBuilder("max(x)")
                .function(avg)
                .variable("x")
                .build();
        double x = xx.setVariable("x", 10).evaluate();


        System.out.println(x);

    }
    @Test
    public void max2() {
        Function avg = new Function("max") {
            @Override
            public double apply(double... doubles) {
                System.out.println(doubles.length);
                return Arrays.stream(doubles).max().getAsDouble();
            }
        };
        double evaluate = new ExpressionBuilder("max(1,122,4,5,8,13,6,21,0)")
                .function(avg)
                .build().evaluateCus(true);
        System.out.println(evaluate);



    }


    @Test
    public void name100() {
        Function sin = Functions.getBuiltinFunction("sin");
        double result = new ExpressionBuilder("sin(30)")
                .function(sin)
                .build()
                .evaluate();
        System.out.println(result);
    }


}