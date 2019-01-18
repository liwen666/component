package com.temp.common.base.thread.JDKutil.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**重入锁
* <p>describe</p> 
* <p>ReentrantLockTest.java</p>
* <p></p>
* @author lw
* @date 2017年1月11日
* @version 1.0
* @link
*/
public class ReentrantLockTest implements Runnable{
	public static ReentrantLock lock= new ReentrantLock();
	public static Condition condition = lock.newCondition();
	@Override
	public void run() {
		try {
			lock.lock();
			/**
			 * 相当于object.wait()
			 * 还有一个特有的方法 
			 * 不可中断的等待  awaitUninterrupted
			 */
			condition.await(200, TimeUnit.SECONDS);
			System.out.println("线程退出");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(lock.isHeldByCurrentThread()){
			lock.unlock();
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ReentrantLockTest t = new ReentrantLockTest();
		Thread t1 = new Thread(t);
		
		t1.start();
		/**
		 * 通知线程继续执行
		 */
		Thread.sleep(100);
		lock.lock();
		/**
		 * 必须放在两者之间使用
		 */
		condition.signal();
		lock.unlock();
		
	}
	

}
