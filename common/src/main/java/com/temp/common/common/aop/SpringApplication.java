package com.temp.common.common.aop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/temp/common/common/aop/spring-quartz-wf.xml");
        System.out.println(ctx);
    }

}
