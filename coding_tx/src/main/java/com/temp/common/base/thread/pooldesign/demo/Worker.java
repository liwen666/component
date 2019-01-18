package com.temp.common.base.thread.pooldesign.demo;

/**
* <p>describe</p> 
* <p>Worker.java</p>
* <p></p>
* @author lw
* @date 2017年1月13日
* @version 1.0
* @link
*/
public class Worker extends  Thread{
	/**
	 * 对象的关联  一对多的关系 每个worker  都分装了同一个pool这样可以拿pool对worker进行操作
	 */
	private ThreadPoolTEST pool;
	private Runnable target;
	private boolean isShutDown=false;
	private boolean isIdle=false;
	

	public Worker(Runnable target, String string, ThreadPoolTEST threadPoolTEST) {
		super(string);
		this.pool=threadPoolTEST;
		this.target=target;
		
	}
	
	public ThreadPoolTEST getPool() {
		return pool;
	}

	public void setPool(ThreadPoolTEST pool) {
		this.pool = pool;
	}

	public boolean isShutDown() {
		return isShutDown;
	}

	public void setShutDown(boolean isShutDown) {
		this.isShutDown = isShutDown;
	}

	public boolean isIdle() {
		return isIdle;
	}

	public void setIdle(boolean isIdle) {
		this.isIdle = isIdle;
	}

	public Runnable getTarget() {
		return target;
	}

	@Override
	public void run() {
		/**
		 * 为线程池工作的线程不是关闭状态就会执行任务 target
		 */
		while (!isShutDown){
			isIdle=false;
			if(target!=null){
				target.run();
			}
			/**
			 * 任务结束了   标记为空闲状态
			 */
			isIdle=true;
			try{
				/**
				 * 将空闲线程回收
				 */
				pool.repool(this);
				synchronized (this) {
					wait();
				}
			}catch(InterruptedException e){
				
			}
			isIdle=false;
		}
		
	}
	

	public void shutDown() {
		isShutDown=true;
		notifyAll();
	}

	public void setTarget(Runnable newtarget) {
		target= newtarget;
		notifyAll();
		
	}


}
