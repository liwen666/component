package com.temp.common.base.thread.concurrent.concurrentHashMap;
 
import org.apache.hadoop.io.IntWritable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 大屎蛋教程网-dashidan.com
 * HashMap与ConcurrentHashMap的区别于应用场景
 * Created by 大屎蛋 on 2018/5/18.
 */
public class Demo1 {
 
    public static void main(String[] args) throws InterruptedException {
        /** 全局HashMap*/
//        HashMap<Integer, Integer> hashMap = new HashMap();
        HashMap<Integer, IntWritable> hashMap = new HashMap();
//        Map<Integer, Integer> hashMap = new ConcurrentHashMap<>();
//        Map<Integer, IntWritable> hashMap = new ConcurrentHashMap<>();
        hashMap.put(0, new IntWritable(0));
 
        /** 多线程编辑100次*/
        for (int i = 0; i < 20; i++) {
            new Thread(new EditThread(hashMap)).start();
        }
        Thread.sleep(2000);
        /** 输出最终结果*/
        System.out.println("Demo1 main value " + hashMap.get(0));
    }
}
 
class EditThread implements Runnable {
 
    Map<Integer, IntWritable> hashMap;
 
    public EditThread(Map<Integer, IntWritable> hashMap) {
        this.hashMap = hashMap;
    }
 
    @Override
    public void run() {
        IntWritable integer = hashMap.get(0);
        for (int i=0;i<1000;i++) {
//            synchronized (integer) {
//                integer.set( hashMap.get(0).get() + 1);
//            }
            hashMap.put(0,new IntWritable(i+1));
        }
    }
}
