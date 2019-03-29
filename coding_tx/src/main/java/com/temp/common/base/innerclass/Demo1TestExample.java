package com.temp.common.base.innerclass;

import com.temp.common.base.innerclass.demo1.Example;
import com.temp.common.base.innerclass.demo1.InterfaceTest;

public class Demo1TestExample extends Example{
    public static void main(String args[]) {
        Example a = new Example();
        InterfaceTest in = a.getIn();
        in.increment();
        a.test();
        Demo1TestExample dm = new Demo1TestExample();
        dm.protectMd(Demo1TestExample.class.getName());

//        a.
    }
}