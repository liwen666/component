package com.temp.common.base.innerclass;

import com.temp.common.base.innerclass.demo2.Example1;
import com.temp.common.base.innerclass.demo2.Example2;

public class DemoMainExample {
    private class test1 extends Example1 {
        public String name() {
            return super.name();
        }
    }

    private class test2 extends Example2 {
        public int age() {
            return super.age();
        }
    }

    public String name() {
        return new test1().name();
    }

    public int age() {
        return new test2().age();
    }

    public static void main(String args[]) {

        DemoMainExample mi = new DemoMainExample();

        System.out.println("姓名:" + mi.name());

        System.out.println("年龄:" + mi.age());

    }

}