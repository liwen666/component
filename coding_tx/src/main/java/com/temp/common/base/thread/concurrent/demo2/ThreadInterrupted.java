package com.temp.common.base.thread.concurrent.demo2;
/**
 * 线程中断停止线程的运作   安全 可靠    线程被中断后，循环体中的代码会执行完。
 * 数据安全
* <p>describe</p> 
* <p>ThreadInterrupted.java</p>
* <p></p>
* @author lw
* @date 2017年1月6日
* @version 1.0
* @link
*/
public class ThreadInterrupted {
	public static void main(String[] args) {
		Thread1 t1 = new Thread1();
		Thread t = new Thread(t1);
		t.setName("t1");
		t.start();
		try {
			Thread.currentThread();
			Thread.sleep(500);
			t.interrupt();   //此方法不具有阻塞作用
			System.out.println("线程被中断");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("main-------------");
			e.printStackTrace();
		}
	}

}
