package com.temp.common.base.thread.multiplethread.demo4;
/**
 * 线程的可见性测试
* <p>describe</p> 
* <p>VisibilityTest.java</p>
* <p></p>
* @author lw
* @date 2017年1月9日
* @version 1.0
* @link
*/
public class VisibilityTest {
	public static void main(String[] args) throws InterruptedException {
		Thread1 t= new Thread1();
		t.start();
//		Thread.sleep(100);
//		t.setFlag(true);
		Thread.sleep(100);
//		t.setStop(true);
	}

}

class Thread1 extends Thread{
	/**
	 * 线程的标志位  默认为false
	 */
	private boolean flag;
	
	/**
	 * 将变量加上 volatile 之后每次访问变量都会读取一次值
	 */
	private volatile boolean stop;
	private int i;
	 
	

	@Override
	public void run() {
		/**
		 * flag=true 表示结束循环线程停止
		 * 此处的循环每次执行都会读取  flag的值  然后进行下一步
		 */
		while(!stop){
			i++;
			
			
		}
		System.out.println("线程停止-----i="+i);
	}



	public boolean isFlag() {
		return flag;
	}



	public void setFlag(boolean flag) {
		this.flag = flag;
	}



	public boolean isStop() {
		return stop;
	}



	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	
	
	
	
}
