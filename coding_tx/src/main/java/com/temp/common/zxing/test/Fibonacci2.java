package com.temp.common.zxing.test;

public class Fibonacci2 {
	
	public static int calculate(int value) {
		
		switch (value) {
			/*case 0:
				return 0;
			
			case 1:
				return 1;
			
			case 2:
				return 1;*/
			
			default:
				return Fibonacci2.calculate(value - 1) + Fibonacci2.calculate(value - 2);
		}
	}
	
	public static void main(String[] args) {
		
		int value;
		System.out.print("Input: ");
		value = Integer.parseInt(System.console().readLine());
		System.out.println("Result: " + new Integer(Fibonacci2.calculate(value)).toString());
		
		for (int i = 1; i <= value; i++) {
			System.out.print(new Integer(Fibonacci2.calculate(i)).toString() + " ");
		}
	}
}