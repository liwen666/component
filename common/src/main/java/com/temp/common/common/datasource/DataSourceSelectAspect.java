package com.temp.common.common.datasource;

import com.temp.common.common.datasource.annotation.DataSourceSelect;
import com.temp.common.common.datasource.config.HandlerDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Description:    java类作用描述
 * author:     lw
 * date:     2019/5/26 12:22
 * Version:        1.0
 */
//@Component
@Aspect
@Slf4j
public class DataSourceSelectAspect {



    private long currentTime = 0L;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(jrx.anyest.meta.datasource.annotation.DataSourceSelect)")
    public void logPointcut() {
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation annotation = method.getAnnotation(DataSourceSelect.class);
        String description = null;
        if (null!=annotation) {
            description = ((DataSourceSelect) annotation).description();
        }
        Object result = null;
        currentTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("["+description  +"]:  "+ e.getMessage(), e);
            return description+" 异常";
        }
        return result;
    }

    @Pointcut("execution(* jrx.anyest..*Controller.*(..))")
    private void anyMethod(){}//定义一个切入点

    @Around("anyMethod()")
    public Object dataSourceChange(ProceedingJoinPoint joinPoint)
    {
        Object result = null;
        try {
            String tenantId = "default";
            log.info("设置数据源:{}",tenantId);
            HandlerDataSource.setDataSource(tenantId);

            result =  joinPoint.proceed();
        } catch (Throwable throwable) {
            try {
                result =  joinPoint.proceed();
            } catch (Throwable throwable1) {
                throwable1.printStackTrace();
            }
            throwable.printStackTrace();
        }finally {
            HandlerDataSource.clearDataSource();
        }
        return result;

    }
}
