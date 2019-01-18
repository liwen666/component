package com.temp.common.base.thread.multiplethread.cas;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**automicLong  和Integer 操作一样
 * atomicInteger  的测试
* <p>describe</p> 
* <p>Atomic1.java</p>
* <p></p>
* @author lw
* @date 2017年1月10日
* @version 1.0
* @link
*/
public class Atomic4 {
	public static AtomicIntegerArray arr= new AtomicIntegerArray(10);
	public static class AddThread implements Runnable{

		@Override
		public void run() {
			for(int k=0;k<1000;k++){
				/**
				 * 线程对数据中的每个元素都 做一次加一的操作
				 */
				System.out.println(arr.getAndIncrement(0));
				System.out.println(arr.getAndIncrement(k%arr.length()));

			}
			
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] ts= new Thread[10];
//		Runnable r = new AddThread();
		for(int k=0;k<10;k++){
			/**
			 * 创建线程的目标是new 出来的  但是目标操作的数据i是同一个  
			 *  i  中的方法是线程安全的
			 */
			ts[k]=new Thread(new AddThread());
//			ts[k]=new Thread(r);
		}
		for(int k=0 ;k<10;k++){
			ts[k].start();
		}
		for(int k=0 ;k<10;k++){
			ts[k].join();
		}
		System.out.println(arr);
		System.out.println(arr.getClass());
		System.out.println(arr.getClass().getTypeName());
		System.out.println(arr.getClass().getTypeParameters());
		System.out.println(arr.getClass().getClassLoader());
	}

}
