package com.temp.common.base.reference;

import java.util.HashMap;
import java.util.Map;

public class TempBeanFactory {
    private static Map<Class,Object> hashMap = new HashMap<Class,Object>();

    public static <T> T getBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        Object o = hashMap.get(clazz);
        if(o ==null&&clazz.getName().contains("com.temp.common")){
            //此工厂只保存指定目录下的类
            hashMap.put(clazz,clazz.newInstance());
        }
        return (T) hashMap.get(clazz);
    }
    public static <T> void setBean(Class<T> clazz,Object t) throws IllegalAccessException, InstantiationException {
            hashMap.put(clazz,t);
    }
    public static Integer beanCount(){
        return hashMap.size();
    }
}
