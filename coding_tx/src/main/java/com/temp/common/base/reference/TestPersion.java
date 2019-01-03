package com.temp.common.base.reference;

import com.temp.common.Other;

public class TestPersion {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        PersionService bean = TempBeanFactory.getBean(PersionService.class);
        System.out.println(bean.getName());
        bean.setName("change");
        System.out.println(TempBeanFactory.getBean(PersionService.class).getName());
        PersionService2 bean2 = TempBeanFactory.getBean(PersionService2.class);




    }
}
