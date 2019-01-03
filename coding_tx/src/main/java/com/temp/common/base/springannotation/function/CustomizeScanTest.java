package com.temp.common.base.springannotation.function;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.temp")
public class CustomizeScanTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(CustomizeScanTest.class);
        annotationConfigApplicationContext.refresh();
//        ScanClass1 injectClass = annotationConfigApplicationContext.getBean(ScanClass1.class);
//        System.out.println(injectClass);
//        injectClass.print();
        ScanClass injectClass = annotationConfigApplicationContext.getBean(ScanClass.class);
        System.out.println(injectClass);
        injectClass.print();
    }
}