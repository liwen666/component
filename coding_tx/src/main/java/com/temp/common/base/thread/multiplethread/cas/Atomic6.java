package com.temp.common.base.thread.multiplethread.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**AtomicIntgegerFieldUpdate  使普通的变量享受到CAS的原子操作
* <p>describe</p> 
* <p>Atomic1.java</p>
* <p></p>
* @author lw
* @date 2017年1月10日
* @version 1.0
* @link
*/
public class Atomic6 {
	public static class Candidate{
		int id;
		volatile int score;
		
	}
    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater 
    /**
     * 通过反射将对应的字段变成原子操作
     */
    = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
    public static AtomicInteger allScore= new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
		final Candidate stu = new Candidate();
		Thread[] ts = new Thread[10000];
		for(int i=0;i<10000;i++){
			ts[i]= new Thread(){
				public void run(){
					if(Math.random()>0.4){
						/**
						 * 两者做对比，看原子操作是否有效果
						 */
						scoreUpdater.incrementAndGet(stu);
						allScore.incrementAndGet();
					}
				}
			};
			ts[i].start();
		}
		/**
		 * 等待所有线程结束
		 */
		for(int i=0;i<10000;i++){
			ts[i].join();
	}
		//打印输出 看两者的结果是否一致
		System.out.println("score="+ stu.score);
		System.out.println("allScore="+allScore);
    }		
}
/**
 * 匿名线程的启动
 * new Thread(){
				public void run(){
					try {
						Thread.sleep(Math.abs((int)(Math.random()*100)));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(atomicStr.compareAndSet("abc", "def")){
						System.out.println("Thread:"+Thread.currentThread().getId()+"Change---------");
					}else{
						System.out.println("Thread:"+Thread.currentThread().getId()+"fail---------");
					}
					
				}
			}.start();
 * 
 */
