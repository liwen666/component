package com.temp.common.base.thread.pooldesign.threadpool.demo4;

import java.util.concurrent.*;

public class ThreadPool {
    private static ExecutorService pool;
    public static void main( String[] args ) throws InterruptedException
    {
        //实现自定义接口
        pool = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5),
                new ThreadFactory() {
            public Thread newThread(Runnable r) {
                System.out.println("线程"+r.hashCode()+"创建");
                //线程命名
                Thread th = new Thread(r,"threadPool"+r.hashCode());
                return th;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy()) {
    
            protected void beforeExecute(Thread t,Runnable r) {
                System.out.println("准备执行："+ ((ThreadTask)r).getTaskName());
            }
            
            protected void afterExecute(Runnable r,Throwable t) {
                System.out.println("执行完毕："+((ThreadTask)r).getTaskName());
            }
            
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };
          
        for(int i=0;i<10;i++) {
            pool.execute(new ThreadTask("Task"+i));
        }    
        pool.shutdown();
    }
}

 class ThreadTask implements Runnable{
    private String taskName;
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public ThreadTask(String name) {
        this.setTaskName(name);
    }
    public void run() {
        //输出执行线程的名称
        System.out.println("TaskName"+this.getTaskName()+"---ThreadName:"+Thread.currentThread().getName());
    }
}