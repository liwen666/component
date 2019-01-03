package com.temp.common.aop;

import com.temp.common.base.springannotation.base.MyComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyProxyController {
    private static Logger logger = LoggerFactory.getLogger(MyProxyController.class);

    public String getPageUri(String userName){
        System.out.println("自定义");
        logger.debug("debug====={}",userName);
        logger.info("info==========用户：{}",userName);
        return "login"+"---------------------"+userName;
    }
}
