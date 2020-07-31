package com.temp.common.common.util;

import net.objecthunter.exp4j.function.Function;

import java.util.ArrayList;
import java.util.Arrays;

public class Exp4jFunction extends Function {


    public Exp4jFunction(String name) {
        super(name,1);
    }


    @Override
    public double apply(double... doubles) {
//        super.numArguments=doubles.length;
        System.out.println(doubles.length);
        return Arrays.stream(doubles).max().getAsDouble();
    }
}