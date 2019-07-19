package com.temp.common.base.thread.JDKutil.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* <p>describe</p> 
* <p>CountDownDome.java</p>
* <p></p>
* @author lw
* @date 2017年1月11日
* @version 1.0
* @link
*/
public class CountDownDome implements Runnable {
	static final CountDownLatch end = new CountDownLatch(10);
	static final CountDownDome demo = new CountDownDome();
	@Override
	public void run() {
		try{
			Thread.sleep(new Random().nextInt(10)*1000);
			System.out.println("check complete!-->"+Thread.currentThread().getName());
			String substring = Thread.currentThread().getName();
			System.out.println(substring.substring(substring.lastIndexOf("-")+1,substring.length()));
			end.countDown();
			/**
			 * 一个线程可以达成多个条件
			 */
//			end.countDown();
//			end.countDown();
//			end.countDown();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		for(int i=0;i<10;i++){
			exec.submit(demo);
		}
		/**
		 * 等待条件达成
		 * 至少要end.countDown();10次
		 */
		end.await();
		/**
		 * 火箭发射
		 */
		System.out.println("fire!");
		exec.shutdown();
				
	}
}
