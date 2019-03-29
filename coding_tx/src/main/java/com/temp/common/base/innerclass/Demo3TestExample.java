package com.temp.common.base.innerclass;

import com.temp.common.base.innerclass.demo1.Example;
import com.temp.common.base.innerclass.demo1.InterfaceTest;
import com.temp.common.base.innerclass.demo3.Example3;

public class Demo3TestExample extends Example{
    public static void main(String args[]) {
        Example3 e3 = new Example3();
        e3.increment();;
        e3.getCallbackReference().increment();
    }
}