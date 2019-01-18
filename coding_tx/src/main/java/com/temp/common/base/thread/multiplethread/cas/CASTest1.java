package com.temp.common.base.thread.multiplethread.cas;
/**
 * 
 * 无锁算法  ****************************
 * 无锁
 * 无法保证数据一致。只能通过内存偏移地址拿数据才能保证数据一致性
 * ******************************
* <p>describe</p> 
* <p>VolatileTest.java</p>
* <p></p>
* @author lw
* @date 2017年1月9日
* @version 1.0
* @link
*/
public class CASTest1 {
	public static void main(String[] args) throws InterruptedException {
		long before = System.currentTimeMillis();

		Thread t1= new Thread1();
		Thread t2 = new Thread1();
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(Thread1.i);
		System.out.println(System.currentTimeMillis()-before);
		
	}
public static class Thread1 extends Thread{
	private  volatile static  int i;

	@Override
	public void run() {
		for(int a=0;a<100000;a++){
		/**
		 * 这第二种方式也不能保证数据的正确性
		 */
			while (true){
				if(cas(i,i+1)){

					/**
					 * break很重要
					 */
					break;
				}
			}
			i++;
//			synchronized (Thread1.class){
//			i++;}
		}

	}
//	@Override
//	public void run() {
//		for(int a=0;a<10000;a++){
//			/**
//			 * 这第二种方式也不能保证数据的正确性
//			 */
//			while (true){
//				if(cas(i,i+1)){
//					i++;
//					/**
//					 * break很重要
//					 */
//					break;
//				}
//			}
//		}
//
//	}

	private boolean cas(int i2, int j) {
		if(i2+1==j){
			return true;
					};
		
		return false;
	}

	public static int getI() {
		return i;
	}

	public static void setI(int i) {
		Thread1.i = i;
	}
	
	
	
}
}
