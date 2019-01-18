package com.temp.common.base.thread.multiplethread.demo3;
/**
* <p>describe</p> 
* <p>OrderThread.java</p>
* <p></p>
* @author lw
* @date 2017年1月9日
* @version 1.0
* @link
*/
public class OrderThread {
	
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
	public  void read(){
		boolean f= true;
		
		/**
		 * 定义一个死循环  判断写的线程是否写完，写完了开始执行读   内部结束循环
		 */
		while(f){
			if(flag){
				int i=a+1;
				System.out.println("读到的结果是-----"+i);
				f=false;
			}
		}
		
	}
	public static void main(String[] args) {
		
		OrderThread o = new OrderThread();
		Thread1 t1 = new Thread1(o);
		Thread2 t2 = new Thread2(o);
		/**
		 * 线程不能重复启动
		 */
//		for (int i=0;i<3;i++){
			t1.start();
//			t1.start();
			t2.start();
//		}
		
		
		
	}
	public static class Thread1 extends Thread{
		public OrderThread o= new OrderThread();

		public Thread1(OrderThread o) {
			super();
			this.o = o;
		}

		@Override
		public void run() {
			o.write();
		}
		
		
	}
	
	public static class Thread2 extends Thread{
		public OrderThread o= new OrderThread();
		
		public Thread2(OrderThread o) {
			super();
			this.o = o;
		}

		@Override
		public void run() {
			o.read();
		}
		
		
	}
}
