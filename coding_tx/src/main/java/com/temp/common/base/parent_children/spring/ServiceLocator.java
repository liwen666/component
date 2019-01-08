package com.temp.common.base.parent_children.spring;

import com.temp.common.base.parent_children.ParentInterface;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ServiceLocator implements ApplicationContextAware{
    /**
     * 用于保存接口实现类名及对应的类
     */
    private Map<String, ParentInterface> map;

    /**
     * 获取应用上下文并获取相应的接口实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //根据接口类型返回相应的所有bean
         map = applicationContext.getBeansOfType(ParentInterface.class);
    }

    public Map<String, ParentInterface> getMap() {
        return map;
    }
}