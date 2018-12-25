package com.temp.common.common.schedule.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:com/temp/common/common/schedule/service/spring-quartz-wf.xml")
public class BusiLogSchedulServiceTest {
    @Test
    public void testSchedul() throws Exception {

    }
//    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-quartz-wf.xml");
//    }
}