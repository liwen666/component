package com.temp.common.zxing.test;

import java.util.Scanner;

public class Fibonacci {
	
	public static int calculate(int value) {
		
		switch (value) {
			case 0:
				return 0;
			
			case 1:
				return 1;
			
			case 2:
				return 1;
			
			default:
				return Fibonacci.calculate(value - 1) + Fibonacci.calculate(value - 2);
		}
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int value=0;
		System.out.print("Input: ");
//		value = Integer.parseInt(System.console().readLine());
		System.out.print("Input: ");
		if (scanner.hasNext()) {
			value = Integer.parseInt(scanner.next());
		}

//		value=10;
		System.out.println("Result: " + new Integer(Fibonacci.calculate(value)).toString());
		
		for (int i = 1; i <= value; i++) {
			System.out.print(new Integer(Fibonacci.calculate(i)).toString() + " ");
		}
	}
}