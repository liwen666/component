package com.temp.common.base.aspectj.high;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface MethodMy {
    public void excute(Object obj,Method md) throws InvocationTargetException, IllegalAccessException ;
    void excute(Object obj, ProceedingJoinPoint pjp) throws Throwable;
}
