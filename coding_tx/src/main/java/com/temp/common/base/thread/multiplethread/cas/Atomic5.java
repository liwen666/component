package com.temp.common.base.thread.multiplethread.cas;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**AtomicIntegerArray  数组的原子操作
* <p>describe</p> 
* <p>Atomic1.java</p>
* <p></p>
* @author lw
* @date 2017年1月10日
* @version 1.0
* @link
*/
public class Atomic5 {
//	public static AtomicIntegerArray arr= new AtomicIntegerArray(10);
	public static AtomicIntegerArray arr= new AtomicIntegerArray(10);
	public static class AddThread implements Runnable{

		@Override
		public void run() {
			for(int k=0;k<1000000;k++){
				/**
				 * 这种算法相当于对数组的每个下标进行遍历 来做操作
				 *
				 * 每个线程循环1000次
				 *
				 * 对数组元素的操作由低下标 到高下标  数据平均分配
				 */
//				System.out.println(arr.getAndIncrement(k%arr.length()));
				arr.getAndIncrement(k%arr.length());
//				System.out.println(arr.getAndAdd(k%arr.length(),3));

			}

		}

	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] ts= new Thread[10];
//		Runnable r = new AddThread();
		long before = System.currentTimeMillis();
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
		System.out.println(System.currentTimeMillis()-before);
		System.out.println(arr);
		System.out.println(arr.getClass());
		System.out.println(arr.getClass().getTypeName());
		System.out.println(arr.getClass().getTypeParameters());
		System.out.println(arr.getClass().getClassLoader());
	}

}
