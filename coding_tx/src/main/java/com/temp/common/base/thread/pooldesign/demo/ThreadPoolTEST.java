package com.temp.common.base.thread.pooldesign.demo;

import java.util.List;
import java.util.Vector;


/**
* <p>describe</p> 
* <p>ThreadPoolTEST.java</p>
* <p></p>
* @author lw
* @date 2017年1月13日
* @version 1.0
* @link
*/
public class ThreadPoolTEST {
	private static ThreadPoolTEST instance=null;
	
	//空闲的线程队列
	private List<Worker> idleThreads;
	//已有的线程总数
	private int threadCount;
	private boolean isShutDown=false;
	public ThreadPoolTEST() {
		this.idleThreads= new Vector(5);
		threadCount=0;
	}
	public static ThreadPoolTEST getInstance() {
		if(instance==null){
			instance= new ThreadPoolTEST();
		}
		return instance;
	}
	/**
	 * 空闲线程的回收
	 * @param repoolingThread
	 */
	protected synchronized void repool(Worker repoolingThread){
		if(!isShutDown){
			idleThreads.add(repoolingThread);
		}
		else{
			repoolingThread.shutDown();
		}
		
	}
	public synchronized void shutdown(){
		isShutDown=true;
		for(int threadIndex=0;threadIndex<idleThreads.size();threadIndex++){
			Worker idleThread = (Worker)idleThreads.get(threadIndex);
			idleThread.shutDown();
		}
	}
	public synchronized void start(Runnable target){
		Worker thread = null;
		if(idleThreads.size()>0){
			int lastIndex = idleThreads.size()-1;
			thread= idleThreads.get(lastIndex);
			thread.setTarget(target);
			
		}else{
			threadCount++;
			thread = new Worker(target,"Pthread #"+threadCount,this);
			thread.start();
		}
	}
	public static void setInstance(ThreadPoolTEST instance) {
		ThreadPoolTEST.instance = instance;
	}
	public List<Worker> getIdleThreads() {
		return idleThreads;
	}
	public void setIdleThreads(List<Worker> idleThreads) {
		this.idleThreads = idleThreads;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public boolean isShutDown() {
		return isShutDown;
	}
	public void setShutDown(boolean isShutDown) {
		this.isShutDown = isShutDown;
	}
	
	
	

}
