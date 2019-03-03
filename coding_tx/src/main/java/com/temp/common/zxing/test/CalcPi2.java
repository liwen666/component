package com.temp.common.zxing.test; /**
 * This is a javadoc style comment
 * Pi =2 + 1/3(2 +2/5(2 + ...(n/2n+1)(2+ ......
 *
 */

import java.util.*;

public class CalcPi2 {
	
	// member variable
	private int[] piArray;
	private int maxRound;
	
	public CalcPi2(int rounds) {
		maxRound = rounds;
		piArray = new int[rounds];
		piArray[0] = 2;
	}
	
	public void calculate(int k)
	{
		int divide = 2*(maxRound - k) + 3;
		for(int i=1; i< maxRound; i++ )
		{
			int relay = piArray[i-1];
			piArray[i-1] = relay / divide;
			piArray[i] += (relay % divide) * 10000;
		}
		int multi = maxRound - k + 1;

		for(int i = maxRound-2; i>0; i--)
		{
			piArray[i] *= multi;
		}
		for(int i = maxRound-2; i>0; i--)
		{
			piArray[i-1] += (piArray[i])/10000;
			piArray[i] = (piArray[i])%10000;
		}

		piArray[0] += 2;
	}
	
	public void print() {
		System.out.print(piArray[0]);
		System.out.print('.');
		
		for (int i = 1; i < maxRound; i++) {
			System.out.print(piArray[i]);
		}			
	}

	public static void main(String[] args) {
		
		int rounds = 10;
		// output a hint
		System.out.print("Input rounds: ");
		
		try {
			// read value from console and convert to integer
//			rounds =Integer.parseInt(System.console().readLine());
			rounds =1000;

			// record time started
			long time1 = System.currentTimeMillis();
			
			// create a CalcPi2 instance
			CalcPi2 calc = new CalcPi2(rounds);
			
			// while loop
			int k = 1;
			while (k <= rounds) {
				calc.calculate(k);
				k++;
			}

			// record time finished calculation
			long time2 = System.currentTimeMillis();
			
			// output time taken.
			System.out.println("Time taken: " + new Long(time2 - time1).toString() + "ms");

			// print output
			calc.print();
		} catch (Exception e) {
			// catch exception and print stack trace
			e.printStackTrace();
		}
	}
}