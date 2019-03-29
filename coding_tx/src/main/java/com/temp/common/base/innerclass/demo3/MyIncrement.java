package com.temp.common.base.innerclass.demo3;

public class MyIncrement {
    public void increment() {

        System.out.println("父类方法调用");

    }

    static void f(MyIncrement f) {

        f.increment();

    }
}