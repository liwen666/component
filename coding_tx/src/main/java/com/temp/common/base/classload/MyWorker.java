package com.temp.common.base.classload;

public class MyWorker implements Runnable {
    MyClassLoad classLoad = new MyClassLoad();

    @Override
    public void run() {
        try {
            Thread.currentThread().getContextClassLoader().loadClass("com.temp.common.base.classload.dddd");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        try {
            //加载之后被加载对象交给主线程加载器
            classLoad.loadClass("com.temp.common.base.log.WorkerTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        System.out.println(Thread.currentThread().getName());
    }
}
