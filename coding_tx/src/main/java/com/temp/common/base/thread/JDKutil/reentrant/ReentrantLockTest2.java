package com.temp.common.base.thread.JDKutil.reentrant;


import com.temp.common.base.thread.JDKutil.DeadLockCheck;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可中断
* <p>describe</p> 
* <p>ReentrantLockTest.java</p>
* <p></p>
* @author lw
* @date 2017年1月11日
* @version 1.0
* @link
*/
public class ReentrantLockTest2 implements Runnable{
	public static ReentrantLock lock1= new ReentrantLock();
	public static ReentrantLock lock2= new ReentrantLock();
	int lock;
	
	public ReentrantLockTest2(int lock) {
		super();
		this.lock = lock;
	}
	@Override
	public void run() {
		try{
			if(lock==1){
				/**
				 * lock是一个实例变量  用来区分不同的线程
				 * lock1 和lock2是两个不同的锁  用来构造死锁现象
				 * 这种锁法是可中断锁
				 * 不同于  lock.lock()
				 */
				lock1.lockInterruptibly();
				try{
					Thread.sleep(500);
					
				}catch(InterruptedException e){}
				lock2.lockInterruptibly();
				}else{
					lock2.lockInterruptibly();
					try{
						Thread.sleep(500);
						
					}catch(InterruptedException e){}
					lock1.lockInterruptibly();
			}
		}catch(InterruptedException e){
			/**
			 * 此处用于线程中断补救
			 */
			e.printStackTrace();
			
		}finally{
			if(lock1.isHeldByCurrentThread()){
				System.out.println("lock1");
				lock1.unlock();
			}
			if(lock2.isHeldByCurrentThread()){
				System.out.println("lock2");
				lock2.unlock();
			}
			System.out.println(Thread.currentThread().getId()+":线程退出");
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ReentrantLockTest2 ta = new ReentrantLockTest2(1);
		ReentrantLockTest2 tb = new ReentrantLockTest2(2);
		Thread t1 = new Thread(ta);
		Thread t2 = new Thread(tb);
		
		t1.start();
		t2.start();
		Thread.sleep(1000);
		/**
		 * 守护线程用于死锁检查
		 * 
		 * 并中断线程
		 */
		DeadLockCheck.check();
	}
	

}
