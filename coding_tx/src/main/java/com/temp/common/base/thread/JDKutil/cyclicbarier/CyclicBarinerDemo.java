package com.temp.common.base.thread.JDKutil.cyclicbarier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 例如司令和士兵
* <p>describe</p> 
* <p>CountDownDome.java</p>
* <p></p>
* @author lw
* @date 2017年1月11日
* @version 1.0
* @link
*/
public class CyclicBarinerDemo  {
//	static final  CyclicBarrier cb = new CyclicBarrier(10);
	public static class Soldier implements Runnable{
		private String soldier;
		private final CyclicBarrier cyclic;
		
		
		public Soldier(String soldier, CyclicBarrier cyclic) {
			super();
			this.soldier = soldier;
			this.cyclic = cyclic;
		}


		@Override
		public void run() {
			try{
				//等待所有士兵到齐
				cyclic.await();
				cyclic.await();
				doWork();
				//等待所有士兵完成工作
				cyclic.await();
				cyclic.await();
			}catch(InterruptedException e){
				System.out.println(soldier+"中断");
				e.printStackTrace();
				
			} catch (BrokenBarrierException e) {
				System.out.println(soldier+"BrokenBarrierException");
				e.printStackTrace();
			}
			
			
		}


		private void doWork() {
			try {
				Thread.sleep(Math.abs(new Random().nextInt()%10000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(soldier+"任务完成！");
			
			
		}
		
		
	}
	/**
	 * 只能是内部类  不能定义内部类的类
	 * 否在main方法无法识别
	* <p>describe</p> 
	* <p>CyclicBarinerDemo.java</p>
	* <p></p>
	* @author lw
	* @date 2017年1月12日
	* @version 1.0
	* @link
	 */
	public  static class BarrierRun implements Runnable{
		boolean flag;
		int N;
		

		public BarrierRun(boolean flag, int n) {
			super();
			this.flag = flag;
			N = n;
		}


		@Override
		public void run() {
			if(flag){
				System.out.println("司令：士兵"+N+"个任务完成！");
			}else{
				System.out.println("司令：士兵"+ N+"集合完成！");
				flag=true;
			}
			
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		final int N= 11;
		Thread[] allsoldier = new Thread[N];
		boolean flag= false;
		/**
		 * N表示计数  （参与者）
		 * 后面的runnable便是计数达到后执行的动作
		 */
		CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag,N));
		System.out.println("集合队伍");
		for(int i=0;i<11;i++){
			System.out.println("士兵"+i+"报道");
			allsoldier[i]= new Thread(new Soldier("士兵"+i, cyclic));
			allsoldier[i].start();
			/**
			 * 表示有10个参与者  有一个被中断则其他的线程都会抛出 栅栏坏掉的异常
			 * BrokenBarrierException
			 */
//			if(i==5){
//				allsoldier[0].interrupt();
//			}
		}
	}
}