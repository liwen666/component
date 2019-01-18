package com.temp.common.base.thread.multiplethread.demo2;
/**
 * 线程同步在一个锁上  一会一次执行   只有锁的线程先执行  没有会等待  
* <p>describe</p> 
* <p>PendThread.java</p>
* <p></p>
* @author lw
* @date 2017年1月9日
* @version 1.0
* @link
*/
public class PendThread  {
	public static Object o= new Object();
	public static void main(String[] args) throws InterruptedException {
		//***************传入目标执行run*********************
		Thread1 t= new Thread1();
		Thread t1 = new Thread(t,"t1");
		Thread t2 = new Thread(t,"t2");
		t1.start();//开启线程  执行run方法    和电泳run方法不一样
		t2.start();
		//**************不传目标执行run   *********************
		t2.join();//无参数表示知道T2执行完才会向下执行
		/**
		 * join可以设置线程的等待时间   毫秒
		 */
		Thread2 tn = new Thread2();
		tn.setName("tn");
		tn.start();
	}
	/**
	 * 静态类的目的是让main  方法可以new  对象
	 * 一个类的静态方法只能访问它的静态成员
	* <p>describe</p> 
	* <p>PendThread.java</p>
	* <p></p>
	* @author lw
	* @date 2017年1月9日
	* @version 1.0
	* @link
	 */
	public static class Thread1 extends Thread{

		@Override
		public void run() {
			synchronized(o){
				for (int i=0;i<2;i++){
					System.out.println( Thread.currentThread().getName()+"-----");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
			
		}
		
		
	}
	public static class Thread2 extends Thread{
		
		
		@Override
		public void run() {
			synchronized(o){
				for (int i=0;i<10;i++){
					System.out.println( Thread.currentThread().getName()+"-----");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
			
		}
		
		
	}
	

}
