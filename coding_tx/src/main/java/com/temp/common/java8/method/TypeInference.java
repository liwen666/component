package com.temp.common.java8.method;

public class TypeInference {
    public static void main(String[] args) {
        final Value< String > value = new Value<>();
        String orDefault = value.getOrDefault("22", Value.defaultValue());
        System.out.println(orDefault);
    }
}