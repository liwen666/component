package com.temp.common.base.thread.concurrent.demo2;
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
				
				System.out.println(Thread.currentThread().getName()+"----线程中断");
				break;
			}
			System.out.println(Thread.currentThread().getName()+"-------线程运行中");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("线程进入循环体内，if  语句块之后被中断的操作");
				e.printStackTrace();
				/**
				 * 线程被中断捕获之后需要再次被中断才会生效
				 */
				Thread.currentThread().interrupt();
			}
			/**
			 * 将cpu执行权放弃  然后再次竞争
			 */
			Thread.yield();
			System.out.println(Thread.currentThread().getName()+"-------线程运行中");
			
		}
	}
	

}
