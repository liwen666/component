package com.temp.common.java8.FunctionalInterface.demo2;

import java.util.function.Function;

public class FunctionalInterfaceTest {
 
	public static void main(String[] args) {
		
		 String str1 = getLength1("hello", value -> "hello的长度："+value, value -> value.length()); //输出:hello的长度：5
		 System.out.println(str1);
		 
		 Integer result = getLength2("hello", value -> value, value -> value.length()); //输出：5
		 System.out.println(result);
		 
	}
	
	public  static String getLength1(String str1, Function<Integer, String> function1, Function<String,Integer> function2){
		/**
		 * 这里一定要注意，function1和function2的参数类型。
		 * function2的输出类型与function1的输入类型一定要一致，
		 * 否则编译不会通过
		 */
		return function1.compose(function2).apply(str1);
	}
				
	public  static Integer getLength2(String str1,Function<String, String> function1,Function<String,Integer> function2){
		/**
		 * 这里一定要注意，function1和function2的参数类型。
		 * function1的输出类型与function2的输入类型一定要一致，(方向相反)
		 * 否则编译不会通过
		 */
		return function1.andThen(function2).apply(str1);
	}
}