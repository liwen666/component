package com.temp.common.base.thread.pooldesign.jdk;

import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
* <p>describe</p> 
* <p>ThreadPoolDemo.java</p>
* <p></p>
* @author lw
* @date 2017年1月13日
* @version 1.0
* @link
*/
public class ThreadPoolDemo1 {
	public static class Mytask implements Runnable{
		static final CountDownLatch end = new CountDownLatch(10);
		static AtomicInteger atomicInteger= new AtomicInteger(0);

		@Override
		public void run() {
			System.out.println(System.currentTimeMillis()+":thread ID:"+Thread.currentThread().getId());
			try {
				getName();
				atomicInteger.getAndIncrement();
				end.countDown();
				System.out.println(end.getCount());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		private  void getName() throws InterruptedException {

			synchronized (getLock(Thread.currentThread().getName())) {
				Thread.sleep(2000);
			}
			System.out.println(Thread.currentThread().getName()+"-----执行任务");
		}

		private Object getLock(String name) {
			if("pool-1-thread-6".equals(name)){
				return "pool-1-thread-3";
			}
			if("pool-1-thread-4".equals(name)){
				return "pool-1-thread-3";
			}
			if("pool-1-thread-1".equals(name)){
				return "pool-1-thread-3";
			}
		return 	name;
		}


	}
	public static void main(String[] args) throws InterruptedException {
		StopWatch clock = new StopWatch();
		clock.start("开始执行："+"任务");
		Mytask task = new Mytask();
		ExecutorService es = Executors.newFixedThreadPool(10);//创建固定大小的线程
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
		System.out.println("-------"+Mytask.atomicInteger);
	}
}
