package com.temp.common.base.thread.multiplethread.demo4;
/**
 * volatile  只是作为线程的标志位使用   
 * 不能作为数据的安全来使用；
* <p>describe</p> 
* <p>VolatileTest.java</p>
* <p></p>
* @author lw
* @date 2017年1月9日
* @version 1.0
* @link
*/
public class VolatileTest {
	public static void main(String[] args) throws InterruptedException {
		Thread t1= new Thread1();
		Thread t2 = new Thread1();
		t1.start();
		t2.start();
		t2.join();
		t2.join();
		System.out.println(Thread1.i);
		
	}
public static class Thread1 extends Thread{
	private  volatile static  int i;

	@Override
	public void run() {
		for(int a=0;a<10000;a++){
			i++;
		}
		
	}

	public static int getI() {
		return i;
	}

	public static void setI(int i) {
		Thread1.i = i;
	}
	
	
	
}
}
