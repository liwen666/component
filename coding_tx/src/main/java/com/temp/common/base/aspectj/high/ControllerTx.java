package com.temp.common.base.aspectj.high;

import org.springframework.stereotype.Service;

@Service
public class ControllerTx {
    @myInterface(clzz = {MethodProxy.class,MethodProxy2.class})
    public  void say(){
        System.out.println("hello");
    }
}
