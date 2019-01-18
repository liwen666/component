package com.temp.common.base.thread.multiple2;
public class test {
	public static void main(String[] args) throws InterruptedException {
		  SourceA s = new SourceA();
	        TestThread tt = new TestThread(s);
	        TestRunnable tr = new TestRunnable(s);
	        Thread t = new Thread(tr);
	        System.out.println("调用线程1");
	        tt.start();
	        Thread main = Thread.currentThread();
//	        main.sleep(1);//确定线程一一定先启动
	        System.out.println("调用线程2");
	        t.start();
//	        main.sleep(3);//确定两个线程已经结束
	        tt.join();
	        t.join();
	        s.getSource();
	}
      
}