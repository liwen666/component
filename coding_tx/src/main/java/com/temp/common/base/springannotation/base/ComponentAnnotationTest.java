package com.temp.common.base.springannotation.base;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ComponentAnnotationTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(ComponentAnnotationTest.class);
        annotationConfigApplicationContext.refresh();
        InjectClass injectClass = annotationConfigApplicationContext.getBean(InjectClass.class);
        injectClass.print();
        ScanClass injectCla = annotationConfigApplicationContext.getBean(ScanClass.class);
        System.out.println(injectCla);
        injectCla.print();
    }
    @MyComponent
    public static class InjectClass {
        public void print() {
            System.out.println("hello world");
        }
    }

//    @Component
//    public static class ScanClass {
//        public void print() {
//            System.out.println("scanClass===========");
//        }
//    }

}
