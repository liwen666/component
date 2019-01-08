package com.temp.component.dubbo.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {
    private final static Logger logger = LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args) {
        logger.debug("debug");
        logger.info("info");
        logger.error("error");
        logger.warn("warn");

    }
}
