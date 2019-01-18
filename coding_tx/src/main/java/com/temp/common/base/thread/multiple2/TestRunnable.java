package com.temp.common.base.thread.multiple2;

/**
 * 这个是线程 共享资源 锁来进行两个线程之间的通信的
* <p>describe</p> 
* <p>TestRunnable.java</p>
* <p></p>
* @author lw
* @date 2017年1月5日
* @version 1.0
* @link
 */
public class TestRunnable implements Runnable {

    private int time=1;
    private SourceA s;
    private String id = "002";
    public TestRunnable(SourceA s){
        this.s = s;
    }
    public void setTime(int time) {
        this.time = time;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("i will sleep"+ time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        synchronized(s){
            s.notify();
            System.out.println("我唤醒了001！");
            System.out.println("我存入了id"+id);
            s.setSource(id);
        }
    }

}