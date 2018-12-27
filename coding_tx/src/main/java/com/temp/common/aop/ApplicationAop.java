package com.temp.common.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationAop {
    public static void main(String[] args) {
            ApplicationContext ctx = new ClassPathXmlApplicationContext("com/temp/common/aop/spring_aop.xml");
        System.out.println(ctx.getBean(DemoController.class));
        DemoController bean = ctx.getBean(DemoController.class);
        System.out.println(bean.getPageUri("target"));
        System.out.println(ApplicationContextText.applicationContext);



    }
}
