package com.temp.common.base.classload;

import com.temp.common.base.classload.base.DiskClassLoader;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoad myClassLoad = new MyClassLoad();
        ClassLoader classLoader = Test.class.getClassLoader();
        System.out.println(classLoader.getClass().getName());
        ClassLoader parent = classLoader.getParent();
        System.out.println(parent.getClass().getName());
//        这里面定义的加载器加载的类都在主线程的加载器中；
//        Class<?> aClass = classLoader.loadClass("com.temp.common.base.classload.TargetClass");
        Class<?> aClass1 = myClassLoad.loadClass("com.temp.common.base.classload.TargetClass");
        myClassLoad.say();
//        classLoader.findClass("com.temp.common.base.classload.TargetClass");
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader.getClass().getName());

        Thread myThread = new Thread(new MyWorker());
        myThread.setName("myThread");
//        myThread.start();

       DiskClassLoader dcl = new DiskClassLoader("D:\\component\\component\\coding_tx\\target\\classes");
        DiskClassLoadExt diskLoader = new DiskClassLoadExt("D:\\component\\component\\coding_tx\\target\\classes");
        Class<?> aClass = diskLoader.findClass("com.temp.common.base.classload.base.Test");
        ClassLoader parent1 = diskLoader.getParent();
        System.out.println("parent    "+parent1.getClass().getName());
        System.out.println(aClass.getName());


    }
}
