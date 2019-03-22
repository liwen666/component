package com.temp.common.base.thread.concurrent.concurrentHashMap;
 
import java.util.concurrent.ConcurrentHashMap;
 
import org.apache.hadoop.io.IntWritable;
 
/**
 * 
 * @author Administrator
 * 
 */
public class ConcurrencyHashMapTest9 {
 
	public ConcurrencyHashMapTest9() {
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int thread_Num = 10;
 
			// case 1
			ConcurrentHashMap<Integer, Integer> pMsgs = new ConcurrentHashMap<Integer, Integer>();
			for (int i = 0; i < 10000; i++)
				pMsgs.put(i, i);
			System.out.println(pMsgs.get(5));
			Thread[] tGroup = new Thread[thread_Num];
			for (int i = 0; i < thread_Num; i++) {
				tGroup[i] = new Thread(new Worker(pMsgs));
				tGroup[i].start();
			}
			for (int i = 0; i < thread_Num; i++) {
				tGroup[i].join();
			}
 
			System.out.println(pMsgs.get(5));
			System.out.println(pMsgs.get(9999));

			// case 2
			ConcurrentHashMap<Integer, IntWritable> pMsgs1 = new ConcurrentHashMap<Integer, IntWritable>();
			for (int i = 0; i < 10000; i++)
				pMsgs1.put(i, new IntWritable(i));
 
			Thread[] tGroup1 = new Thread[thread_Num];
			for (int i = 0; i < thread_Num; i++) {
				tGroup1[i] = new Thread(new Worker1(pMsgs1));
				tGroup1[i].start();
			}
 
			for (int i = 0; i < thread_Num; i++) {
				tGroup1[i].join();
			}
 
			System.out.println(pMsgs1.get(5));
 
			// case 3
			ConcurrentHashMap<Integer, IntWritable> pMsgs2 = new ConcurrentHashMap<Integer, IntWritable>();
			for (int i = 0; i < 10000; i++)
				pMsgs2.put(i, new IntWritable(i));
			Thread[] tGroup2 = new Thread[thread_Num];
			for (int i = 0; i < thread_Num; i++) {
				tGroup2[i] = new Thread(new Worker2(pMsgs2));
				tGroup2[i].start();
			}
 
			for (int i = 0; i < thread_Num; i++) {
				tGroup2[i].join();
			}
			System.out.println(pMsgs2.get(5));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
 
class Worker implements Runnable {
	ConcurrentHashMap<Integer, Integer> pMsgs;
 
	public Worker(ConcurrentHashMap<Integer, Integer> pMsgs) {
		this.pMsgs = pMsgs;
	}
 
	@Override
	public void run() {
		Integer value = pMsgs.get(5);   //这里的value是Integer，是基本类型，相当于重新开辟一块内存，类似C语言的形参,因此修改无法对原值产生影响
		synchronized (value) {
			value = value + 1;
		}
	}
}
 
class Worker1 implements Runnable {
	ConcurrentHashMap<Integer, IntWritable> pMsgs;
 
	public Worker1(ConcurrentHashMap<Integer, IntWritable> pMsgs) {
		this.pMsgs = pMsgs;
	}
 
	@Override
	public void run() {
		IntWritable value = pMsgs.get(5);  //这里仅仅是一个指针，因为value是一个对象，因此执行结果与Worker2相同
		synchronized (value) {  //在对象上上锁，，对象是一个引用，，与值无关
			value.set(value.get() + 1);
		}
	}
}
 
class Worker2 implements Runnable {
	public static Object mutex = new Object();
	ConcurrentHashMap<Integer, IntWritable> pMsgs;
 
	public Worker2(ConcurrentHashMap<Integer, IntWritable> pMsgs) {
		this.pMsgs = pMsgs;
	}
 
	@Override
	public void run() {
		synchronized (mutex) {

			IntWritable value = pMsgs.get(5);  //这里并发下的效果应该没有Worker1好，因为其锁的粒度更低!
			// System.out.println(Thread.currentThread().getName() + " value:"+ value);
			value.set(value.get() + 1);
		}
	}
}