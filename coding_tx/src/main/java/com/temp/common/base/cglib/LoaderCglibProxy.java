package com.temp.common.base.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/****
 * @author yuanchangyou
 *
 * loaderCglib 代理，用来处理 每个服务监听到 加载信息变化而做的改变
 *
 * @date 2020-02-13
 */
@Slf4j
public class LoaderCglibProxy implements MethodInterceptor {


    private String tenanId;

    private LoaderHandler loaderHandler;


    /**
     * 创建代理对象方法
     *
     * @param target        代理对象
     * @param args          对应的构造器参数类型
     *
     *                          例：有构造器如下
     *                          public Person(name,age){...} name为String.class age为int.class
     *                          写入name的类型与age的类型
     *
     *                          则：new Class[]{String.class,int.class}
     *
     * @param argsValue     对应的构造器参数值
     *
     *                          例:如此创建对象 new Person("name",23) 用以下方式传入：new Object[]{"name",23}
     *
     * @param <T>           <泛型方法>
     * @return              返回跟代理对象类型
     */
    public <T> T getInstance(T target,LoaderHandler loaderHandler,String tenantId,Class[] args,Object[] argsValue){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        this.loaderHandler = loaderHandler;
        this.tenanId = tenantId;
        return (T) enhancer.create(args,argsValue);
    }
 
    /**
     * 创建代理对象方法
     *
     * @param target        代理对象
     * @param <T>           <泛型方法>
     * @return              返回跟代理对象类型
     */
    public <T> T getInstance(T target){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }
 


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        try{
            PropertiesThreadLocalHolder.addProperties("tenanId",tenanId);
            result = methodProxy.invokeSuper(o,objects);
            loaderHandler.handler(method,objects,result);
        }catch(Exception e){
            e.printStackTrace();
            log.error("Error invoke method:{}.{},Exception:{}",method.getDeclaringClass().getCanonicalName(),method.getName(),e.getMessage());
            throw e;
        }finally {
//            TenantIdProvider.clearTenantId();
        }
        return result;
    }
}