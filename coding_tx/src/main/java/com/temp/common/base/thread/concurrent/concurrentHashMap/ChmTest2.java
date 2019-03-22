package com.temp.common.base.thread.concurrent.concurrentHashMap;
 
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class ChmTest2 {
	
	public static void main(String[] args) throws InterruptedException {
		final ConcurrentHashMap<Integer, String> chm = new ConcurrentHashMap<Integer, String>();
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		newFixedThreadPool.submit(new Runnable() {
			public void run() {
				for(int i = 0;i <10000;i++){
					chm.put(123, "asd"+i);
					System.out.println(Thread.currentThread().getName()+"--====--"+chm.get(123));
				}
			}
		});
		newFixedThreadPool.submit(new Runnable() {
			public void run() {
				for(int i = 0;i <100;i++){
					System.out.println(Thread.currentThread().getName()+"----"+chm.get(123));
				}


			}
		});
		newFixedThreadPool.shutdown();
		Thread.sleep(2000);

		System.out.println(chm.get(123));
	}
}