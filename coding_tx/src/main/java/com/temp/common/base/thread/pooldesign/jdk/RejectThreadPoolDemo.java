package com.temp.common.base.thread.pooldesign.jdk;

import java.util.concurrent.*;

/**
* <p>describe</p> 
* <p>RejectThreadPoolDemo.java</p>
* <p></p>
* @author lw
* @date 2017年1月16日
* @version 1.0
* @link
*/
public class RejectThreadPoolDemo {
	public static class Mytask implements Runnable{

		@Override
		public void run() {
			System.out.println(System.currentTimeMillis()+":ThreadID"+Thread.currentThread().getId());
			try {
				//每个线程执行消耗100毫秒    制造线程无法被执行到的情况
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		Mytask task = new Mytask();
		//拒绝策略    如果任务不执行会做什么
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new RejectedExecutionHandler() {
			
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				//这个线程池的队列是一个同步的队列  无法保留任务  所以很多任务会丢掉
				System.out.println(r.toString()+"is discard");
				// TODO Auto-generated method stub
				//任务不执行可以抛出一个异常  RejectedExecutionException
				
				
			}
		});
		for(int i=0;i<Integer.MAX_VALUE;i++){
			es.submit(task);
			//每10毫秒提交一个线程
			Thread.sleep(10);
		}
	}

}
