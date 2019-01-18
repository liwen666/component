package com.temp.common.base.thread.pooldesign.jdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的扩展
* <p>describe</p> 
* <p>ExtThreadPool.java</p>
* <p></p>
* @author lw
* @date 2017年1月13日
* @version 1.0
* @link
*/
public class ExtThreadPool {
	public static class Mytask implements Runnable{
//			private String name;
			public String name;
		public Mytask(String name) {
				super();
				this.name = name;
			}

		@Override
		public void run() {
			System.out.println("正在执行---"+":thread ID:"+Thread.currentThread().getId()+"task Name"+name);
			try {
				//五个线程一起执行 最后1秒钟后全部一起退出
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	public static void main(String[] args) throws InterruptedException {
		//这里0和0L效果一样
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<Runnable>()){

			@Override
			protected void beforeExecute(Thread t, Runnable r) {
				System.out.println("准备执行："+((Mytask)r).name);
//				super.beforeExecute(t, r);
			}

			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				System.out.println("执行完成："+((Mytask)r).name);
//				super.afterExecute(r, t);
			}

			@Override
			protected void terminated() {
				System.out.println("线程池退出！");
//				super.terminated();
			}
			
			
		};
		for(int i=0;i<5;i++){
		   Mytask task = new Mytask("Task"+i);
		   //execute  执行的是runnable任务  
		   es.execute(task);
		   //下面的方法执行会报异常
//		   es.submit(task);
		   Thread.sleep(100);
		}
		es.shutdown();
	}

}
