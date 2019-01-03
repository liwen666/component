package com.temp.common.base.aspectj;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogInterceptor {
//    @Pointcut("execution(public * *.*.aspectj..*.save(..))")
    @Pointcut("execution(public * com.temp.common.base.aspectj.*.save(..))")
    public void myMethod(){};
    
    /*@Before("execution(public void com.oumyye.dao.impl.UserDAOImpl.save(com.oumyye.model.User))")*/
    @Before("myMethod()")
    public void before() {
        System.out.println("method staet");
    } 
    @After("myMethod()")
    public void after() {
        System.out.println("method after");
    } 
//    @AfterReturning("execution( * *.aspectj..*.*(..))")
    @AfterReturning("execution( * com.temp.common.base.aspectj.*.save(..))")
    public void AfterReturning() {
        System.out.println("method AfterReturning");
    } 
    @AfterThrowing("execution(public * *.aspectj..*.*(..))")
    public void AfterThrowing() {
        System.out.println("method AfterThrowing");
    } 
}