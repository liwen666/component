package com.temp.common.base.thread.pooldesign.jdk;

import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* <p>describe</p> 
* <p>ThreadPoolDemo.java</p>
* <p></p>
* @author lw
* @date 2017年1月13日
* @version 1.0
* @link
*/
public class ThreadPoolDemo {
	public static class Mytask implements Runnable{
		static final CountDownLatch end = new CountDownLatch(10);

		@Override
		public void run() {
			System.out.println(System.currentTimeMillis()+":thread ID:"+Thread.currentThread().getId());
			try {
				Thread.sleep(1000);
				end.countDown();
				System.out.println(end.getCount());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	public static void main(String[] args) throws InterruptedException {
		StopWatch clock = new StopWatch();
		clock.start("开始执行："+"任务");
		Mytask task = new Mytask();
		ExecutorService es = Executors.newFixedThreadPool(5);//创建固定大小的线程
		for(int i=0;i<10;i++){
			//线程池可以 处理Callablel类型的任务  获取返回值
			/**
			 * 线程是5个5个的执行任务 
			 * 这里下面两个处理任务的效果是一样的
			 */
//			es.submit(task);//这个会返回一个future对象
			es.execute(task);
		}
		Mytask.end.await();
		clock.stop();
		System.out.println(clock.prettyPrint());
		es.shutdown();
	}
}
