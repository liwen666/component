package com.temp.common.base.thread.multiplethread.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampReference  的测试  模拟
 * 
 * 为数据的修改加上时间戳 防止CAS 操作有误   
 * 例如  冲10元钱    两个线程   钱如果被花掉就会充值两次   0-->10---->0---->10
 *                                   两个0不是同一个   只能用时间戳区分
* <p>describe</p> 
* <p>Atomic1.java</p>
* <p></p>
* @author lw
* @date 2017年1月10日
* @version 1.0
* @link
*/
public class Atomic3 {
	public static AtomicStampedReference<Integer> money= new AtomicStampedReference<Integer>(19, 100);//第一个值是要操作的值，第二个是时间戳的初始值
	public static void main(String[] args) throws InterruptedException {
		for(int i =0;i<2;i++){
			/**
			 * 此处的timestamp的值是根据  money获得的 所有值是可以变化的
			 */
			int timestamp=money.getStamp();
			System.out.println("timestamp-----"+timestamp);
			new Thread(){
				public void run(){
					while (true){
							System.out.println("检查账户");
						while(true){
							Integer m =money.getReference();
							if(m<20){
								if(money.compareAndSet(m, m+20, timestamp, timestamp+1)){
									System.out.println(m);
									System.out.println("余额小于20元，充值成功，余额："+money.getReference());
									break;
								};
							}else{
								System.out.println("余额大于20元，不用充值");
								break;
							}
						}

					}

					
				}
			}.start();
			
		}
		
		/*
		 * 用户的消费行为
		 */
		new Thread(){
			public void run(){
				for(int i=0;i<10;i++){
					while(true){
						int timestamp = money.getStamp();
						Integer m =money.getReference();
						if(m>10){
							if(money.compareAndSet(m, m-10, timestamp, timestamp+1)){
								System.out.println(m);
								System.out.println("成功消费10元： 余额"+money.getReference());
								System.out.println(timestamp+"时间标志位");
								break;
							};
						}else{
							System.out.println("余额不足！");
							break;
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
}
