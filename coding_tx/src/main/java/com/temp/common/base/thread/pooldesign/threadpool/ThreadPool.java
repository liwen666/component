package com.temp.common.base.thread.pooldesign.threadpool;

import java.util.concurrent.*;

public class ThreadPool {
    private static ExecutorService pool;
    public static void main( String[] args )
    {
        //maximumPoolSize设置为2 ，拒绝策略为AbortPolic策略，直接抛出异常
        pool = new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        for(int i=0;i<3;i++) {
            pool.execute(new ThreadTask());
        }   
    }
}

 class ThreadTask implements Runnable{
    
    public ThreadTask() {
        
    }
    
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}