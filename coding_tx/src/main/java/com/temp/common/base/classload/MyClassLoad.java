package com.temp.common.base.classload;

public class MyClassLoad extends ClassLoader {


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
    protected void say(){
        System.out.println("method excute");
    }
}
