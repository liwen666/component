package com.temp.common.aop;

import com.temp.common.base.springannotation.base.MyComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@MyComponent
//@Controller
public class DemoController {
    @Autowired
//    private MyProxyController myProxyController;
    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    public String getPageUri(String userName){
//        myProxyController.getPageUri("实现自定义bean的加载");
        logger.debug("debug====={}",userName);
        logger.info("info==========用户：{}",userName);
        return "login"+"---------------------"+userName;
    }
}
