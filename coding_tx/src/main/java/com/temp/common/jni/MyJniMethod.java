package com.temp.common.jni;

public class MyJniMethod {

    static {
        System.loadLibrary("Project3");
    }

    public native void add(int add);

    public native void print(int i);

    public native int getadd(int add);
}