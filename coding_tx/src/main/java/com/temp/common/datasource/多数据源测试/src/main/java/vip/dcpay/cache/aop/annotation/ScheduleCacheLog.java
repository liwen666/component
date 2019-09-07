package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Description:    java类作用描述
* author:     lw
* date:     2019/5/26 12:41
* Version:        1.0
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduleCacheLog {
	String description() default "";
}
