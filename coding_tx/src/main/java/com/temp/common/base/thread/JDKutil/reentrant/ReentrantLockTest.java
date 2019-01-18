package com.temp.common.base.thread.JDKutil.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**重入锁
 * 解释：就是一个线程拿到锁后执行一段代码   然后调用其他需要锁的程序      因为自己有所所以不需要再次拿锁 可防止死锁的现象
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
	public static int i=0;
	@Override
	public  void run() {
		System.out.println(Thread.currentThread().getName());
		//循环体内部是J 不是i
		/**
		 * 开启多线程对i进行加1000000次  结果为2000000  没有锁结果小于200000
		 * 
		 */
		for(int j=0;j<1000000;j++){
			/**
			 * lock.lock()如果使用两次，，需要最后释放两次
			 */
//			lock.lock();
			lock.lock();
			try{
				i++;
			}finally{
				lock.unlock();
			}
			
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ReentrantLockTest t = new ReentrantLockTest();
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
	

}
