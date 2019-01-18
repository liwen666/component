package com.temp.common.base.thread.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MsgFuture<V> implements java.util.concurrent.Future<V>,Runnable {
 
    private final static Logger logger = LoggerFactory.getLogger(MsgFuture.class);
 
    /**
     * 全局的空对象，如果Future获取到值了，那么一定不是NULL
     */
    private final static Object NULL = new Object();
    /**
     * 主锁
     */
    private final ReentrantLock reentrantLock = new ReentrantLock();
 
    /**
     * 条件，利用它的condition.await(left, TimeUnit.MILLISECONDS)和notifyAll方法来实现阻塞、唤醒
     */
    private final Condition condition = reentrantLock.newCondition();
 
    private int timeout;
 
    private volatile Object result = NULL;
 
    private long sendTime;
 
    public MsgFuture(int timeout, long sendTime) {
        this.timeout = timeout;
        this.sendTime = sendTime;
    }
 
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }
 
    public boolean isCancelled() {
        return false;
    }
 
    public boolean isDone() {
        return this.result != NULL;
    }
 
    /**
     * 获取future结果
     * @return
     * @throws InterruptedException
     */
    public V get() throws InterruptedException {
        logger.debug("sendTime:{}",sendTime);
        try {
            return get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            logger.error("获取future结果异常", e);
        }
        return null;
    }
 
    /**
     * 获取结果，如果到达timeout还未得到结果，则会抛出TimeoutException
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @SuppressWarnings("all")
    public V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        long left = getLeftTime(timeout, unit);
        if(left < 0){
            //已经没有剩余时间
            if(isDone()){
                return (V)this.result;
            }else{
                //timeout
                throw new TimeoutException("返回超时，后续的响应将会被丢弃abort");
            }
        }else{
 
            reentrantLock.lock();
            try {
                //获取锁后先判断是否已经完成，防止无意义的await
                if(isDone()){
                    return (V)this.result;
                }
                logger.debug("剩余等待时间  await "+left+" ms");
                condition.await(getLeftTime(timeout, unit), TimeUnit.MILLISECONDS);
            }finally {
                reentrantLock.unlock();
            }
            if(isDone()){
                return (V)this.result;
            }
 
            throw new TimeoutException("未获取到结果");
        }
    }
 
    /**
     * 设置结果值result，唤醒condition {@link #get(long, TimeUnit)}
     * @param result
     */
    public synchronized void setResult(Object result) {
        reentrantLock.lock();
        try {
            this.result = result;
            condition.signalAll();
        }finally {
            reentrantLock.unlock();
        }
 
    }
 
    /**
     * 计算剩余时间
     * @param timeout
     * @param unit
     * @return
     */
    private long getLeftTime(long timeout, TimeUnit unit){
        long now = System.currentTimeMillis();
        timeout = unit.toMillis(timeout); // 转为毫秒
        return timeout - (now - sendTime);
    }
 
    public static void main(String[] args) {
        MsgFuture m = new MsgFuture(100,1L);
        System.out.println(m.isDone());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            long now = System.currentTimeMillis();
            setResult(new String("000000"));
            logger.info("任务完成，剩余时长  {} " ,TimeUnit.MILLISECONDS.toMillis(timeout)-(now-sendTime));
            logger.info("task is done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}