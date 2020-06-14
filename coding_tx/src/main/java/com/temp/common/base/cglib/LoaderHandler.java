package com.temp.common.base.cglib;


import java.lang.reflect.Method;

/**
 * @program: anyest-rule-engine-new
 * @description: loader 之后每个服务的自己的处理类
 * @author: yuanchangyou
 * @create: 2020-03-14 00:39
 **/
public interface LoaderHandler {

    /***
     * loader 加载处理事件
     * @param method
     * @param params
     */
    void handler(Method method,Object[] params, Object result);
}
