package com.temp.common.base.reference.cglib;

import com.temp.common.base.reference.TempBeanFactory;

public class DoCGLib {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
     CglibProxy proxy = new CglibProxy();  
     //通过生成子类的方式创建代理类  
     SayHello proxyImp = proxy.getProxy(SayHello.class);
        SayHello factoryBean = TempBeanFactory.getBean(proxyImp.getClass());
        proxyImp.say();
        factoryBean.say();
        TempBeanFactory.setBean(proxyImp.getClass(),proxyImp);
        SayHello factoryBean2 = TempBeanFactory.getBean(proxyImp.getClass());
        factoryBean2.say();

        System.out.println("factory  bean count :"+TempBeanFactory.beanCount());



    }  
   }  