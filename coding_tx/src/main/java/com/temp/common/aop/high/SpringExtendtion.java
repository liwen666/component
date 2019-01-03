package com.temp.common.aop.high;

import com.temp.common.aop.MyProxyController;
import com.temp.common.base.reference.TempBeanFactory;
import com.temp.common.base.reference.cglib.CglibProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 完美的将自定义bean交给了spring 管理
 * 自定义了cglib 代理了对象
 */
public class SpringExtendtion implements ApplicationContextAware{
    public void  register(){
        System.out.println("注册bean");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ClassPathXmlApplicationContext ctx = (ClassPathXmlApplicationContext) applicationContext;
        ConfigurableListableBeanFactory beanFactory = ctx.getBeanFactory();
        try {
            beanFactory.registerSingleton("myProxyController",
                    TempBeanFactory.getBean(CglibProxy.class)
                            .getProxy(MyProxyController.class));


            /**
             * 在这个地方将bean注册之后spring不会再次注册这里可以替换自己的注解驱动，将bean注入
             */
            beanFactory.registerSingleton("mySpringService",
                    TempBeanFactory.getBean(CglibProxy.class)
                            .getProxy(MySpringService.class));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println("开始注册");
    }
}
