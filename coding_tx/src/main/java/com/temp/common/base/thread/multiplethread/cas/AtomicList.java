//package com.temp.common.base.thread.multiplethread.cas;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * AtomicReference  的测试
//* <p>describe</p>
//* <p>Atomic1.java</p>
//* <p></p>
//* @author lw
//* @date 2017年1月10日
//* @version 1.0
//* @link
//*/
//public class AtomicList {
//	public static AtomicReference<List<String>> atomicStr= new AtomicReference<List<String>>();
////	public static AtomicReference<String> atomicStr= new AtomicReference<String>("abc");
//	public static void main(String[] args) throws InterruptedException {
//		for(int i =0;i<10;i++){
////			final int num=i;
//			 int num=i;
//			new Thread(){
//				public void run(){
//					try {
//						Thread.sleep(Math.abs((int)(Math.random()*100)));
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//
//					if(atomicStr.compareAndSet("abc", "def")){
//						System.out.println("Thread:"+Thread.currentThread().getId()+"Change---------");
//					}else{
//						System.out.println("Thread:"+Thread.currentThread().getId()+"fail---------");
//					}
//
//				}
//			}.start();
//
//			System.out.println("++++++++++"+num);
//		}
//		//因为线程 不阻塞所以不睡眠  获取不到 最终值
//		//Thread.sleep(1000);
//		System.out.println(atomicStr);
//	}
//}
