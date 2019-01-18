package com.temp.common.base.thread.JDKutil.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁  防止线程产生饥饿现象  但是新能稍微低
 * 
* <p>describe</p> 
* <p>ReentrantLockTest.java</p>
* <p></p>
* @author lw
* @date 2017年1月11日
* @version 1.0
* @link
*/
public class ReentrantLockTest4 implements Runnable{
	/**
	 * 这个锁是公平锁，线程会排队  不会饥饿
	 */
	public static ReentrantLock lock= new ReentrantLock(true);
	
	@Override
	public void run() {
		try {
			/**
			 * 试图获取锁  前面参数是时间   后面是指定单位
			 */
			if(lock.tryLock(5, TimeUnit.SECONDS)){
				Thread.sleep(6000);
			}else{
				/**
				 * 这里可以选择将自己已经又得锁释放掉
				 * lock1
				 *   lock2
				 */
				System.out.println(Thread.currentThread().getId()+"----get lock failed");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			/**
			 * 如果锁被当前线程所持有则释放锁
			 */
			if(lock.isHeldByCurrentThread()){
				/**
				 * 如果没有锁直接调用这个方法会抛出  非法调用异常
				 */
				lock.unlock();
			}
			
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ReentrantLockTest4 t = new ReentrantLockTest4();
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		
		t2.start();
		t1.start();
	}
	

}
