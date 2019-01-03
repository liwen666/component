package com.temp.common.base.reference.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(CglibProxy.class);
    private Enhancer enhancer = new Enhancer();
    public <T> T getProxy(Class <T> clazz){
     //设置需要创建子类的类  
     enhancer.setSuperclass(clazz);  
     enhancer.setCallback(this);
     //通过字节码技术动态创建子类实例  
     return (T)enhancer.create();
    }  
    @Override
    //实现MethodInterceptor接口方法  
    public Object intercept(Object obj, Method method, Object[] args,  
      MethodProxy proxy) throws Throwable {
        long first = System.currentTimeMillis();
        System.out.println("前置代理" +obj.getClass().getName());
     //通过代理类调用父类中的方法  
     Object result = proxy.invokeSuper(obj, args);  
     System.out.println("后置代理");
        logger.info("执行{} 类的方法  {} 耗时：{}",obj.getClass().getName(),method.getName(),System.currentTimeMillis()-first);
        return result;
    }
   }  