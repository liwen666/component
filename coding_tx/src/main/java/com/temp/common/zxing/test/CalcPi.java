package com.temp.common.zxing.test;

/**
 * This is a javadoc style comment
 * Pi =2 + 1/3(2 +2/5(2 + ...(n/2n+1)(2+ ......
 *
 */


public class CalcPi {
	
	// static constant variable
	public static final int MAX_ROUND = 2000;
	public static int[] PiArray = new int[MAX_ROUND];
	
	public static void CalcPi(int k)
	{
		int divide = 2*(MAX_ROUND - k) + 3;
		for(int i=1; i< MAX_ROUND; i++ )
		{
			int relay = PiArray[i-1];
			PiArray[i-1] = relay / divide;
			PiArray[i] += (relay % divide) * 10000;
		}
		int multi = MAX_ROUND - k + 1;

		for(int i = MAX_ROUND-2; i>0; i--)
		{
			PiArray[i] *= multi;
		}
		for(int i = MAX_ROUND-2; i>0; i--)
		{
			PiArray[i-1] += (PiArray[i])/10000;
			PiArray[i] = (PiArray[i])%10000;
		}

		PiArray[0] += 2;
	}

	public static void main(String[] args) {
		
		PiArray[0] = 2;
		for(int k=1; k<=MAX_ROUND; k++)
		{
			CalcPi(k);
		}

		java.text.DecimalFormat format=new java.text.DecimalFormat("0000");

		for(int i=0; i<MAX_ROUND-1; i++)
		{
			System.out.print(format.format(PiArray[i]));
		}
	}
}