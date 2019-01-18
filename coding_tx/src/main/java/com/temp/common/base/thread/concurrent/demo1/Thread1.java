package com.temp.common.base.thread.concurrent.demo1;
/**
* <p>describe</p> 
* <p>Thread1.java</p>
* <p></p>
* @author lw
* @date 2017年1月6日
* @version 1.0
* @link
*/
public class Thread1 extends Thread{

	@Override
	public void run() {
		while (true) {
			if(Thread.currentThread().isInterrupted()){
				/**
				 * 线程被中断后不能再使用sleep
				 */
//				try {
//					Thread.currentThread();
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				System.out.println(Thread.currentThread().getName()+"----线程中断");
				System.out.println(Thread.currentThread().getName()+"----线程中断");
				System.out.println(Thread.currentThread().getName()+"----线程中断");
				break;
			}
			Thread.yield();
			System.out.println(Thread.currentThread().getName()+"-------线程运行中");
			
		}
	}
	

}
