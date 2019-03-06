package com.temp.common.jni;

public class Test {
    public static void main(String[] args) {
        MyJniMethod myjni = new MyJniMethod();
        int getadd = myjni.getadd(1);
        System.out.println(getadd);
    }
}
