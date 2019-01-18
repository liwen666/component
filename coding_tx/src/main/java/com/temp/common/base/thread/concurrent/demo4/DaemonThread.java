package com.temp.common.base.thread.concurrent.demo4;

/**
 * 守护线程
 * <p>
 * describe
 * </p>
 * <p>
 * DaemonThread.java
 * </p>
 * <p>
 * </p>
 * 
 * @author lw
 * @date 2017年1月6日
 * @version 1.0
 * @link
 */
public class DaemonThread {
	public static class DaemonT extends Thread {

		@Override
		public void run() {

			while (true) {
				System.out.println("I am alive ");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

		public static void main(String[] args) throws InterruptedException {
			Thread t = new DaemonT();
			/**
			 * 必须在线程开启前设置
			 */
			t.setDaemon(true);
			t.start();
			/**
			 * 只要又线程不结束，守护线程就不结束
			 */
			Thread.sleep(2000);

		}

	}

}
