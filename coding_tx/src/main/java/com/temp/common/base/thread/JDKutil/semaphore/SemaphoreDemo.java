package com.temp.common.base.thread.JDKutil.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 共享锁
* <p>describe</p> 
* <p>SemaphoreDemo.java</p>
* <p></p>
* @author lw
* @date 2017年1月11日
* @version 1.0
* @link
*/
public class SemaphoreDemo implements Runnable {
		final Semaphore semp = new Semaphore(5);

		@Override
		public void run() {
			try{
				/**
				 * 可以持有多把锁
				 * 对资源的一种分配
				 */
				semp.acquire(2);
//				semp.acquire();
				Thread.sleep(2000);
				System.out.println(Thread.currentThread().getId()+":done");
			}catch(InterruptedException e){
				e.printStackTrace();
			}finally{
				/**
				 * 释放许可
				 */
				semp.release(2);
//				semp.release();
			}
			
		}
		 public static void main(String[] args) {
			 /**
			  * 创建20个线程的池子
			  */
			 ExecutorService exec= Executors.newFixedThreadPool(20);
			 final SemaphoreDemo demo = new SemaphoreDemo();
			 for(int i=0;i<20;i++){
				 exec.submit(demo);
			 }
			
		}
}
