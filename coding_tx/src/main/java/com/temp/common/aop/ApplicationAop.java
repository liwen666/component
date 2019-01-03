package com.temp.common.aop;

import com.temp.common.base.reference.TempBeanFactory;
import com.temp.common.base.reference.cglib.CglibProxy;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationAop {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/temp/common/aop/spring_aop.xml");
        DefaultListableBeanFactory parentBeanFactory = (DefaultListableBeanFactory) ctx.getBeanFactory();


        parentBeanFactory.registerSingleton("myController",
                TempBeanFactory.getBean(CglibProxy.class)
                        .getProxy(MyProxyController.class));
        MyProxyController bean3 = ctx.getBean(MyProxyController.class);


        System.out.println(bean3.getPageUri("替换application"));


        System.out.println(ctx.getBean(DemoController.class));
        DemoController bean = ctx.getBean(DemoController.class);
        System.out.println(bean.getPageUri("target"));
        System.out.println(ApplicationContextText.applicationContext);
        DemoController bean1 = parentBeanFactory.getBean(DemoController.class);
        System.out.println( bean1.getPageUri("ME"));
        CglibProxy bean2 = TempBeanFactory.getBean(CglibProxy.class);
        DemoController proxy = bean2.getProxy(bean1.getClass());
        proxy.getPageUri("meet you");





    }
}
