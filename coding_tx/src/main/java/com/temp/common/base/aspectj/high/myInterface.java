package com.temp.common.base.aspectj.high;

import com.temp.common.base.aspectj.User;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface myInterface {
    public String value() default "获取注解的值   aop";
    public Class[] clzz () default User.class;
}
