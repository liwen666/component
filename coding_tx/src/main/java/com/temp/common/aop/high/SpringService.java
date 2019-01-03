package com.temp.common.aop.high;

import com.temp.common.aop.MyProxyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringService {
    @Autowired
    private MyProxyController myProxyController;
    @Autowired
    private SpringExtendtion springExtendtion;
    @Autowired
    private MySpringService mySpringService;
    @Autowired
    private MySpringServiceChildren mySpringServiceChildren;

    public void excute(){
        System.out.println("执行自定义bean  manager by spring");
        myProxyController.getPageUri("manager my");
        springExtendtion.register();
        mySpringService.setName();
        mySpringService.findAge();

        mySpringServiceChildren.setName();
    }
}
