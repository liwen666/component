package com.temp.common.base.thread.concurrent.concurrentHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


class B{

    public static void main(String[] args) throws InterruptedException {
         Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
         Map<String, Integer> hashMap = new HashMap<String, Integer>();
        Thread thread1 = new Thread(new HashMapTestRunnable(map,hashMap));
        Thread thread2 = new Thread(new HashMapTestRunnable(map,hashMap));
        Thread thread3 = new Thread(new HashMapTestRunnable(map,hashMap));
        thread1.setName("abb");
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(map.size());
        System.out.println(hashMap.size());
        System.out.println(map.get("abb:100"));
        System.out.println(hashMap.get("abb:100"));
    }


    private static class HashMapTestRunnable implements Runnable {
        private ConcurrentHashMap<String, Integer> map;
       private HashMap<String, Integer> hashMap;
        public HashMapTestRunnable(Map<String, Integer> map, Map<String, Integer> hashMap) {
            this.map= (ConcurrentHashMap<String, Integer>) map;
            this.hashMap= (HashMap<String, Integer>) hashMap;
        }

        public void run() {
            for (int i = 0; i < 1000000; i++) {
                map.put(Thread.currentThread().getName() +":" + i ,  i+1);
//                hashMap.put(Thread.currentThread().getName() +":" + i ,  i+1);
            }
        }
    }

}
class TX{
    static final int HASH_BITS = 0x7fffffff;
    public static void main(String[] args) {
        String key="aaa";
        int hash = spread(key.hashCode());
        System.out.println(hash);
    }

    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
}