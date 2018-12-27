package com.temp.common.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AopUtil {
    Logger logger = LoggerFactory.getLogger(AopUtil.class);
    public void PrintLogInfo(Class clazz){
        logger.info ("  加载类   ：-->{}",clazz.getName());
    }


}
