package com.temp.common.base.thread.pooldesign.jdk.fork;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
* <p>describe</p> 
* <p>CountTask.java</p>
* <p></p>
* @author lw
* @date 2017年1月16日
* @version 1.0
* @link
*/
public class CountTask extends RecursiveTask<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3325199233436894658L;
	//设定任务的大小   此处以  加法为例  
	private static final int THRESHOLD= 10000;
	private long start;
	private long end;
	
	
	public CountTask(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}


	@Override
	protected Long compute() {
		long sum =0;
		//设定任务是否达到分解的 规定大小
		boolean canCompute= (end-start)<THRESHOLD;
		if (canCompute){
			for (long i=start;i<=end;i++){
				sum +=1;
			}
		}else {
			//如果不能计算   分解成100个小任务
			long step = (start+end)/100;
			ArrayList<CountTask> subTasks= new ArrayList<CountTask>();
			long pos = start;
			for(int i=0;i<100;i++){
				long lastOne = pos+step;
				if(lastOne>end)lastOne=end;
				CountTask subTask = new CountTask(pos, lastOne);
				pos += step+1;
				subTasks.add(subTask);
				//把子任务推向线程池  
				subTask.fork();
				
				
			}
			for(CountTask t:subTasks){
				//并等待线程结束
				sum+= t.join();
			}
		}
		
		return sum;
	}
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		CountTask task = new CountTask(0, 200000L);
		ForkJoinTask<Long> result = forkJoinPool.submit(task);
		long res;
		try {
			res = result.get();
			 System.out.println("sum  :  "+res);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
