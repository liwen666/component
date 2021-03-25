package com.temp.common.java8.FunctionalInterface.demo1;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class FunctionalInterfaceTest {
 
	public static void main(String[] args) {
		
		 /**
		  * Bi类型的接口创建
		  */
		 BiFunction<String, String, Integer> biFunction = (str1, str2) -> str1.length()+str2.length();
		 
		 BiConsumer<String, String> biConsumer = (str1, str2) -> System.out.println(str1+str2);
		 
		 BiPredicate<String, String> biPredicate = (str1, str2) -> str1.length() > str2.length();
		 
		 
		 /**
		  * Bi类型的接口使用
		  */
		 int length = getLength("hello", "world", (str1,str2) -> str1.length() + str2.length()); //输出10
		 boolean boolean1 = getBoolean("hello", "world", (str1,str2) -> str1.length() > str2.length()); //输出false
		 
		 System.out.println(length);
		 System.out.println(boolean1);
		 
		 noResult("hello", "world", (str1,str2) -> System.out.println(str1+" "+str2)); //没有输出
 
		 
	}
	
	public  static int getLength(String str1,String str2,BiFunction<String, String, Integer> function){
		return function.apply(str1, str2);
	}
	
	public static void noResult(String str1,String str2,BiConsumer<String, String> biConcumer){
		biConcumer.accept(str1, str2);
	}
	
	public static boolean getBoolean(String str1,String str2,BiPredicate<String, String> biPredicate){
		return biPredicate.test(str1, str2);
	}
}
