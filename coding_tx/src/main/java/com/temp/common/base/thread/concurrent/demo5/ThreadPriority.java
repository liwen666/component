package com.temp.common.base.thread.concurrent.demo5;
/**
 * 线程优先级
* <p>describe</p> 
* <p>ThreadPriority.java</p>
* <p></p>
* @author lw
* @date 2017年1月6日
* @version 1.0
* @link
*/
public class ThreadPriority {

	public static class HightPriority extends Thread{
		static int count=0;

		@Override
		public void run() {
			while(true){
				synchronized (ThreadPriority.class) {
					count++;
					if(count>100000){
						System.out.println("HightPripority is complete");
						break;
					}
					
				}
				
			}
		}
		
		
	}
	public static class LowPripority extends Thread{
		static int count=0;
		@Override
		public void run() {
			while(true){
				synchronized (ThreadPriority.class) {
					count++;
					if(count>100000){
						System.out.println("LowPripority is complete");
						break;
					}
					
				}
				
			}
		}
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Thread high = new HightPriority();
		LowPripority low = new LowPripority();
		high.setPriority(Thread.MAX_PRIORITY);
		low.setPriority(Thread.MIN_PRIORITY);
		low.start();
		System.out.println("------------");
		high.start();
		
	}
}
