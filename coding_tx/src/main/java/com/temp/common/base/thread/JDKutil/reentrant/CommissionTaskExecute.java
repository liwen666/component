package com.temp.common.base.thread.JDKutil.reentrant;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>describe</p>
 * <p></p>
 *
 * @author lw
 * @link
 */
@Data
@NoArgsConstructor
public class CommissionTaskExecute implements Runnable {
    public static ReentrantLock lock= new ReentrantLock();
     public  static final CountDownLatch end = new CountDownLatch(10);
//    private AtomicReference<Set<String>> userIdSet = new AtomicReference<>();
    private AtomicReference<String> userId = new AtomicReference<String>("");
    private AtomicInteger integerAtomicReference = new AtomicInteger();
    private List<List<String>> lists;
    private List<String>userIds = new ArrayList<>();
    private Jedis jedis;

    public CommissionTaskExecute(List<List<String>> lists, Jedis jedis) {
        this.lists = lists;
        this.jedis = jedis;
    }

    @Override
    public void run() {
//        userId.get
        String name = Thread.currentThread().getName();
        int i = Integer.parseInt(name.substring(name.lastIndexOf("-") + 1, name.length()));
        System.out.println(name +"--->线程开启 执行数组"+i);
        List<String> strings = lists.get(i-1);
        System.out.println(name+"-------"+ JSON.toJSONString(strings));
        for (String key :strings ) {
            integerAtomicReference.incrementAndGet();
            lock.lock();
            Set<String> hkeys = jedis.hkeys(key);
            lock.unlock();
//            System.out.println("key-------------------->"+key+"--------hkey--------->"+hkeys+"---------------------------->"+name);
            for (String ke : hkeys) {
//                List<String> devices = jedis.hmget(key, ke);
//                if (devices != null && devices.size() > 0) {
//                    for (String device : devices) {
//                        JSONObject obj = JSONObject.parseObject(device);
//                        if (obj.getBoolean("online"))
//                            userIds.add(key.split(":")[2]);
//                    }
//                }
            }

        }
        System.out.println("thread "+Thread.currentThread().getName()+" complete!");
        end.countDown();

        System.out.println(name+"----complete");
        /**
         * 一个线程可以达成多个条件
         */
//			end.countDown();
//			end.countDown();
//			end.countDown();


    }
//    public static void main(String[] args) throws InterruptedException {
//        ExecutorService exec = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            exec.submit(taskExecute);
//        }
//        /**
//         * 等待条件达成
//         * 至少要end.countDown();10次
//         */
//        end.await();
//        /**
//         * 火箭发射
//         */
//        System.out.println("fire!");
//        exec.shutdown();
//
//    }
}
