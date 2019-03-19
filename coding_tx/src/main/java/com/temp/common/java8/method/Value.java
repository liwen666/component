package com.temp.common.java8.method;

public class Value< T > {
    public static< T > T defaultValue() { 
        return null; 
    }

    public T getOrDefault( T value, T defaultValue ) {
        return ( value != null ) ? value : defaultValue;
    }
}