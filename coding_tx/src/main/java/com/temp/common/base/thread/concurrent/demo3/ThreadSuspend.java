package com.temp.common.base.thread.concurrent.demo3;
/**
 * 。
 * 线程的挂起   为线程起个名字有利于排查错误
 * suspend  and resume  线程的挂起和启动。这两种方式不安全
 * resume  can't  must perform (执行)
 * 线程被挂起，持有锁的话，resume先执行，  那么线程就不会被唤醒， 锁和线程被冻结
* <p>describe</p> 
* <p>ThreadInterrupted.java</p>
* <p></p>
* @author lw
* @date 2017年1月6日
* @version 1.0
* @link
*/
public class ThreadSuspend {
	public static Object o = new Object();
	static ChangeObjectThread t1 = new ChangeObjectThread("t1");
	static ChangeObjectThread t2 = new ChangeObjectThread("t2");
	public static class ChangeObjectThread extends Thread{

		public ChangeObjectThread(String string) {
			/**
			 * 为线程起名字，为父类属性设置就行
			 */
			super.setName(string);  
		}

		@Override
		public void run() {
			synchronized (o) {
				System.out.println("in---"+getName());
				Thread.currentThread().suspend();
			}
		}
		
		/**
		 * resume  和 suspend  是向前版本的兼容，不推荐使用
		 * @param args
		 * @throws InterruptedException
		 */
		public static void main(String[] args) throws InterruptedException {
			t1.start();
			Thread.sleep(1000);
			t2.start();
			t1.resume();  //此处线程1被唤醒
			/**
			 * 此处线程二不一定被唤醒。resume  possible (可能) 
			 */
			t2.resume();
			/**
			 * 表示等待线程结束的意思
			 * 当前线程会等待在 join线程上 
			 * 一直到join线程结束，其他线程才一起执行，  默认jion结束调用了notifyall
			 */
			t1.join();//
			System.out.println("-------");
			t2.join();
			System.out.println("-------");
		}
	}
	
}
