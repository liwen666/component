package com.temp.common.java8.FunctionalInterface;

@FunctionalInterface
public interface TestInterface {  
 
 
    // 抽象方法  
    public void sub();  
 
    // java.lang.Object中的方法不是抽象方法  
    public boolean equals(Object var1);  
 
    // default不是抽象方法  
    public default void defaultMethod(){  
 
    }  
 
    // static不是抽象方法  
    public static void staticMethod(){  
 
    }  
} 