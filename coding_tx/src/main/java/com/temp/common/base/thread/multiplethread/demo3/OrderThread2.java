package com.temp.common.base.thread.multiplethread.demo3;
/**
 * 
 * 多线程共享同一个对象，，，根据对象的属性值进行交互
 * 通过对象的同步方式让线程有序
* <p>describe</p> 
* <p>OrderThread.java</p>
* <p></p>
* @author lw
* @date 2017年1月9日
* @version 1.0
* @link
*/
public class OrderThread2 {
	
	/**
	 * 共享数据a
	 */
	public  int a=0;
	/**
	 * 多线程共享数据   flag是一个读写的标记  
	 */
	public  boolean flag=false;
	public  void write(){
		/**
		 * 线程执行代码  有颠倒执行的可能  但是几率很低    只是一种可能
		 */
		a=1;
		
		for(int i=0;i<10000000;i++){
			a++;
		}
		flag=true;
		System.out.println("写完了");
		
	}
	
	public synchronized void read(){
		boolean f= true;
		
		/**
		 * 定义一个死循环  判断写的线程是否写完，写完了开始执行读   内部结束循环
		 */
		while(f){
			if(flag){
				int i=a+1;
				System.out.println("读到的结果是-----"+i);
				f=false;
				flag=false;
			}
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		
		OrderThread2 o = new OrderThread2();
		Thread1 t1 = new Thread1(o);
		Thread2 t2 = new Thread2(o);
		Thread2 t3 = new Thread2(o);
		Thread1 t4 = new Thread1(o);
		/**
		 * 线程不能重复启动
		 */
//		for (int i=0;i<3;i++){
			t1.start();
//			t1.start();
			t2.start();
			t3.start();
			/**
			 * 这种方式是控制多线程执行同一个方法。
			 * 此处是让线程t3执行到循环体   
			 * 然后执行T4才能让T3顺利执行下去
			 * 只是一种思想，不做实际开发
			 */
			Thread.sleep(1000);
			/**
			 * 如果时启动两个线程写
			 * 数据将会被多线程修改，数据不正确
			 */
			t4.start();
			
//		}
		
		
		
	}
	public static class Thread1 extends Thread{
		public OrderThread2 o= new OrderThread2();

		public Thread1(OrderThread2 o) {
			super();
			this.o = o;
		}

		@Override
		public void run() {
			o.write();
		}
		
		
	}
	
	public static class Thread2 extends Thread{
		public OrderThread2 o= new OrderThread2();
		
		public Thread2(OrderThread2 o) {
			super();
			this.o = o;
		}

		@Override
		public void run() {
			o.read();
		}
		
		
	}
}
