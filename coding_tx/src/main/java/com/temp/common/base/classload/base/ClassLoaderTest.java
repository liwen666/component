package com.temp.common.base.classload.base;

import com.temp.common.base.classload.DiskClassLoadExt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        // TODO Auto-generated method stub
        //创建自定义classloader对象。
        DiskClassLoader diskLoader = new DiskClassLoader("D:\\component\\component\\coding_tx\\target\\classes");
        try {
            //加载class文件
//            Class c = diskLoader.loadClass("com.temp.common.base.classload.base.Test");
            Class<?> c = diskLoader.findClass("com.temp.common.base.classload.base.Test");
            //这种自定义的方式无法对同一个类加载两次
            Class<?> c2 = diskLoader.findClass("com.temp.common.base.classload.base.Test");
            ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
            classLoader.loadClass(c.getName());
//            Class<?> abc = classLoader.loadClass("abc");
//            System.out.println(abc.getName());
            //调用父类的load方法，如果没有，由父类加载。其实这里这个class会被加载两次
            Class<?> aClass = diskLoader.loadClass("com.temp.common.base.classload.base.Test");
            diskLoader.loadClass("com.temp.common.base.classload.base.ClassLoaderTest");

            if(c != null){
                try {
                    Object obj = c.newInstance();
                    Method method = c.getDeclaredMethod("say",null);
                    //通过反射调用Test类的say方法
                    method.invoke(obj, null);
                } catch (InstantiationException | IllegalAccessException 
                        | NoSuchMethodException
                        | SecurityException | 
                        IllegalArgumentException | 
                        InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        DiskClassLoader diskLoaderOther = new DiskClassLoader("D:\\component\\component\\coding_tx\\src\\main");
        Class<?> testClassLoad = diskLoaderOther.findClass("TestClassLoad");
        Object o = testClassLoad.newInstance();
        Method[] declaredMethods = testClassLoad.getDeclaredMethods();
        for(Method m:declaredMethods){
            System.out.println(m.getName());
            //注意这里的方法必须是public 的否则无法通过执行
            //可以通过反射执行类的私有方法
            //通过自定义类加载器来加载指定的class文件
            m.setAccessible(true);
            m.invoke(o,null);


        }
    }
}