package com.temp.common.base.thread.concurrent.demo6;
/**
* <p>describe</p> 
* <p>SchynThread.java</p>
* <p></p>
* @author lw
* @date 2017年1月6日
* @version 1.0
* @link
*/
public class SchynThread implements Runnable{
	static int  i =0;
	static SchynThread instance = new SchynThread();
	@Override
//	public static synchronized void run() {  相当于将锁加在类上    一定生效
	public void run() {
		for(int j=0;j<10000;j++){
			/**
			 * 1.实例对象锁。两个线程共享数据i
			 * 使用同步数据安全
			 * 2. synchronized 放在方法上表示  当前实例锁
			 */
			synchronized (instance) {
				String name = Thread.currentThread().getName();
//				System.out.println(name);

				i++;
			}
//			i++;
		}

	}
	
	public static void main(String[] args) throws InterruptedException {
		/**
		 * 线程共享同一个对象才能  让   synchronized  加在方法上生效  使用方法同步
		 * 下面两个线程在同一个对象上工作
		 */
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		/**
		 * 等待线程结束打印结果
		 */
//		Thread.sleep(100);
		System.out.println(i);
		
	}
}
