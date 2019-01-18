package com.temp.common.base.thread.multiplethread.cas;//package com.temp.code.thread.multiplethread.cas;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicIntegerArray;
//
///**性能测试
//* <p>describe</p>
//* <p>Atomic1.java</p>
//* <p></p>
//* @author lw
//* @date 2017年1月10日
//* @version 1.0
//* @link
//*/
//public class VectorExample {
//    public static final int ELEMENT_NUM = 100;
//    private static final int TASKS_NUM = 4;
//    public static void main(String[] argvs) {
//
//        ExecutorService exec = Executors.newFixedThreadPool(TASKS_NUM);
//
//        final LockFreeVector<String> vectorStr = new LockFreeVector<String>();
//
//        for (int i = 0; i < TASKS_NUM; ++i) {
//            exec.submit(new VectorAdder(vectorStr));
//            if (i%2 == 0)exec.submit(new VectorRemover(vectorStr));
//        }
//
//        exec.shutdown();
//
//        // Wait until all the threads in pool to finish.
//        try {
//            while(!exec.awaitTermination(600, TimeUnit.SECONDS));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // If the Vector is empty now, the program will exit.
//        if (vectorStr.isEmpty())
//        {
//            System.out.println("The Vector is empty, so I will exit!");
//            System.exit(1);
//        }
//
//        // Print all the elements in List.
//        for (String str : vectorStr)
//            System.out.println(str);
//
//        // Let's try get() and set() here.
//        String str = vectorStr.get(0);
//        if ( null == str)
//            vectorStr.set(0, "0");
//
//        System.out.println("The Vector size now: " + vectorStr.size());
//    }
//}
///*
// *  The VectorAdder class will add elements to the given Vector.
// */
//class VectorAdder implements Runnable {
//    LockFreeVector<String> taskVec;
//    private int count = 0;
//
//    public VectorAdder(LockFreeVector<String> taskVector)
//    {
//        taskVec = taskVector;
//    }
//
//    public void run(){
//        while (count < VectorExample.ELEMENT_NUM)
//        {
//            taskVec.pushBack(Integer.toString(count++));
//            System.out.println(Thread.currentThread()+ " Pushed: " + count);
//        }
//        Thread.yield();
//    }
//}
//
///*
// *  The VectorRemover class will remove the existed elements from the given Vector.
// */
//class VectorRemover implements Runnable {
//    LockFreeVector<String> taskVec;
//
//    public VectorRemover(LockFreeVector<String> taskVector)
//    {
//        taskVec = taskVector;
//    }
//
//    public void run(){
//        int times = 0;
//
//        while (times < VectorExample.ELEMENT_NUM)
//        {
//            String popped = taskVec.popBack();
//            System.out.println(Thread.currentThread()+ "Popped: " + popped);
//            times++;
//        }
//        Thread.yield();
//    }
//}