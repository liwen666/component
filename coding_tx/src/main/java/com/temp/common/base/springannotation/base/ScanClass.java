package com.temp.common.base.springannotation.base;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan(basePackages="com.temp")
//@Component
public class ScanClass {
    public void print() {
        System.out.println("scanClass===========");
    }
}
