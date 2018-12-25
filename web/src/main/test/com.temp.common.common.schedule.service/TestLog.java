package com.temp.common.common.schedule.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")

public class TestLog {
    private static final Logger logger = LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");
//        TestLog a = new TestLog();
        System.out.println("ok");
        logger.debug("jjjjj");
        logger.info("ok ----"+1);
    }

    @Test
    public void testLog() throws Exception {
        logger.warn("warn ------");
        logger.error("error  -----");
        logger.debug("debug  ok ---");
        logger.info("syno log ----"+1);
    }
}

