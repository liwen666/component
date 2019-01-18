package com.temp.common.base.thread.JDKutil;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;


/**
* <p>describe</p> 
* <p>DeadLockCheck.java</p>
* <p></p>
* @author lw
* @date 2017年1月11日
* @version 1.0
* @link
*/
public class DeadLockCheck {
	private final static ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
	final static Runnable deadLockCheck = new Runnable() {
		
		@Override
		public void run() {
			while(true){
				long[]deadlockedThreadIds= mbean.findDeadlockedThreads();
				if(deadlockedThreadIds !=null ){
					ThreadInfo[] threadInfos= mbean.getThreadInfo(deadlockedThreadIds);
					for(Thread t:Thread.getAllStackTraces().keySet()){
						for(int i=0;i<threadInfos.length;i++){
							if(t.getId()==threadInfos[i].getThreadId()){
								t.interrupt();
							}
						}
					}
				}
				try{
					/**
					 * 每五秒钟检查一次
					 */
					Thread.sleep(5000);
				}catch(InterruptedException e){}
				
			}
		}
	};
	
	public static void check(){
		System.out.println("死锁检查");
		Thread t = new Thread(deadLockCheck);
		t.setDaemon(true);
		t.start();
	}
}
