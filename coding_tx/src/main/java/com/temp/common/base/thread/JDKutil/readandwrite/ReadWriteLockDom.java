package com.temp.common.base.thread.JDKutil.readandwrite;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
* <p>describe</p> 
* <p>ReadWriteLockDom.java</p>
* <p></p>
* @author lw
* @date 2017年1月11日
* @version 1.0
* @link
*/
public class ReadWriteLockDom  implements Runnable{

	 private  static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	 /**
	  * 读使用读锁
	  * 写使用写锁
	  * 性能能够提高很多
	  */
	 private  static Lock readLock = lock.readLock();
	 private static Lock writeLock = lock.writeLock();
	 
	 
	 @Override
		public void run() {
			
		}
		
}
