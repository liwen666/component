package com.temp.common.base.thread.JDKutil.lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程的挂起和唤醒 
 * 
 * 不同于resume  和 suspend  的用法
* <p>describe</p> 
* <p>LockSupportDemo.java</p>
* <p></p>
* @author lw
* @date 2017年1月12日
* @version 1.0
* @link
*/
public class LockSupportDemo {
	public static Object u = new Object();
	public static ChangeObjectThread t1 = new ChangeObjectThread("t1");
	static ChangeObjectThread t2 = new ChangeObjectThread("t2");
	
	public static class ChangeObjectThread extends Thread{

		public ChangeObjectThread(String string) {
			super.setName(string);
		}

		@Override
		public void run() {
			synchronized (u){
				System.out.println("in"+getName());
				LockSupport.park();
			} 
			super.run();
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		t1.start();
		Thread.sleep(1000);
		t2.start();
		/**
		 * 线程的挂起和唤醒不分先后都能让线程执行下去
		 * 
		 * 如果先使用unpark
		 * park就不会让线程挂起
		 */
		LockSupport.unpark(t1);
		LockSupport.unpark(t2);
		t1.join();
		t2.join();
		
	}

}
