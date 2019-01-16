package com.temp.common.base.aspectj.high;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class AopInterceptor implements ApplicationContextAware{
    private ApplicationContext applicationContext;
    @Pointcut("execution(* com.temp.common.base.aspectj.high.ControllerTx.*(..))")
    public void controller() {
    }
    @Around("controller()")
    public Object  introcepter(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("拦截到了" + pjp.getSignature().getName() +"方法...");
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        System.out.println(targetMethod.getName()+"-------------------");
        Class<?> declaringClass = targetMethod.getDeclaringClass();
        System.out.println(declaringClass.getName());
        System.out.println(applicationContext.getBean("controllerTx"));
        Annotation[] annotations = targetMethod.getAnnotations();
        System.out.println("---------------------");
        for (Annotation a:annotations){
            System.out.println(a.annotationType().getName());
           if(a instanceof myInterface){
               myInterface my = (myInterface) a;

               System.out.println(my.value());
               System.out.println(my.clzz());
//               MethodProxy o = (MethodProxy) my.clzz().newInstance();
               for(Class c:my.clzz()){
                   MethodMy o = (MethodMy) c.newInstance();
//                   o.excute(applicationContext.getBean(targetMethod.getDeclaringClass()),targetMethod);
//                     上面的方法死循环
                   o.excute(targetMethod,pjp);
               }

//               o.excute(applicationContext.getBean(targetMethod.getDeclaringClass()),targetMethod);

               //这种再次代理执行方法会掉入死循环

               System.out.println("---------------------");
           }
        }

        System.out.println(targetMethod.getName());
        Class clazz = targetMethod.getClass();
        System.out.println(clazz.getName());


//        return pjp.proceed();
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}