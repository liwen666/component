package com.temp.common.base.classload.high;

import com.temp.common.base.classload.base.DiskClassLoader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, InterruptedException {
        TargetClass tc = new TargetClass();
        Thread thread = Thread.currentThread();
        ClassLoadDynamic myLoad = new ClassLoadDynamic("D:\\component\\component\\coding_tx\\target\\classes");
//        while (true){
//            thread.sleep(1000);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class<?> aClass = myLoad.findClass("com.temp.common.base.classload.high.TargetClass");
        Field[] declaredFields1 = myLoad.getClass().getDeclaredFields();
        for (Field f : declaredFields1) {
            f.setAccessible(true);
            System.out.println(f.getName());
//            通过反射将字段的值修改；
            System.out.println(f.get(myLoad));
            f.set(myLoad,null);
            System.out.println(f.get(myLoad));
        }

        Field[] fields = myLoad.getClass().getFields();
        for (Field f : fields) {
            f.setAccessible(true);
            System.out.println(f.getName());
        }
//        myLoad.getClass().
//        Field[] declaredFields1 = myLoad.getClass().getDeclaredFields();
//        Class<?> aClassa = myLoad.findClass("com.temp.common.base.classload.high.TargetClass");

        Object obj = aClass.newInstance();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            System.out.println(f.get(obj));
//            }

//            System.out.println(aClass.getDeclaredFields()[0].setAccessible(true)|aClass.getDeclaredFields()[0].get(aClass.newInstance()));
            System.out.println(tc.getName());
        }

    }

}