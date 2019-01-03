package com.temp.common.base.aspectj.high;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodProxy implements  MethodMy{
    public void excute(Object obj,Method md) throws InvocationTargetException, IllegalAccessException {
        System.out.println("自定义方法注解      启动");
            md.invoke(obj,null);
    }
  public void excute(Object obj, ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("自定义方法注解    MethodProxy  启动");
             pjp.proceed();
    }
}
