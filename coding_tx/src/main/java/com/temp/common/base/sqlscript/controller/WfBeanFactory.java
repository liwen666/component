package com.temp.common.base.sqlscript.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class WfBeanFactory implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

    @Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
    	WfBeanFactory.applicationContext = context;
	}
	
	public static ApplicationContext getContext() {
		return WfBeanFactory.applicationContext;
	}
	
	public static Object getBean(String beanID) {
	    return getContext().getBean(beanID);
	}

}
