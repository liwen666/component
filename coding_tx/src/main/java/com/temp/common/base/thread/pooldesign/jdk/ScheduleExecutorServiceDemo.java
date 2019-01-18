package com.temp.common.base.thread.pooldesign.jdk;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
* <p>describe</p> 
* <p>ScheduleExecutorServiceDemo.java</p>
* <p></p>
* @author lw
* @date 2017年1月13日
* @version 1.0
* @link
*/
public class ScheduleExecutorServiceDemo {
	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		//如果前面的任务不做完后面是不会启动线程的     执行任务是在线程池中随机选择一个线程执行
		ses.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getId()+"--------"+System.currentTimeMillis()/1000);//打印秒数
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}, 0, 2, TimeUnit.SECONDS);//第一次调 隔多少时间   后面周期性每隔  （不算线程执行任务的时间）   多长时间调用一次
	}

}
