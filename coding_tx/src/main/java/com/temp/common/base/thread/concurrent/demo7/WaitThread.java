package com.temp.common.base.thread.concurrent.demo7;
/**
 * 多线程等待在同一个对象
 * 使用对象的 wait  和 notify协作
 * 
* <p>describe</p> 
* <p>WaitThread.java</p>
* <p></p>
* @author lw
* @date 2017年1月6日
* @version 1.0
* @link
*/
public class WaitThread {
	static Object obj= new Object();
	public static class T1 extends Thread{

		@Override
		public void run() {
			synchronized (obj) {
				System.out.println("线程一执行");
				try {
					System.out.println("线程一执行");
					/**
					 * 线程被唤醒后会先拿锁，然后接着执行下去，，不会从头执行
					 */
					obj.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("线程一执行结束");
				
			}
		}
		
	}
	public static class T2 extends Thread{

		@Override
		public void run() {
			synchronized (obj) {
				System.out.println("线程  2 执行");
				/**
				 * 一定要在线程一先执行才能生效
				 */
				obj.notify();
				try {
					Thread.sleep(2000);
//					obj.notify();  两个线程共享同一个对象   线程二启动不能唤醒线程一
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("线程 2 执行结束");
				
			}
		}
		
	}
	
    public static void main(String[] args) {
		Thread t1 = new Thread(new T1());
		Thread t2 = new Thread (new T2());
		
		t1.start();
		/**
		 * 要保证线程一先启动
		 */
		t2.start();
		
				
	}
}
