package com.temp.common.aop.high;

import com.temp.common.aop.ApplicationContextText;
import com.temp.common.aop.DemoController;
import com.temp.common.aop.MyProxyController;
import com.temp.common.base.reference.TempBeanFactory;
import com.temp.common.base.reference.cglib.CglibProxy;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationAop {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/temp/common/aop/spring_aop.xml");
        DefaultListableBeanFactory parentBeanFactory = (DefaultListableBeanFactory) ctx.getBeanFactory();



        SpringService bean3 = ctx.getBean(SpringService.class);

        bean3.excute();







    }
}
