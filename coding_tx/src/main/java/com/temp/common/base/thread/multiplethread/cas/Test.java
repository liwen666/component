package com.temp.common.base.thread.multiplethread.cas;
public class Test {
	public static void main(String[] args) {
	   int[] a = new int[10];
	   for(int i=1;i<10;i++){
		   /**
		    * i的值=5的时候值为0  
		    */
		   System.out.println("-----------"+i%5);//i%a.length  这是基本数据类型的长度获取方式
		   System.out.println(i);
		   
	   }
	   System.out.println(a.length);
	   
	}
}