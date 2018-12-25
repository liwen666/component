package com.temp.common.common.schedule.service;

//import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import static org.junit.Assert.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-quartz-wf.xml")
public class BusiLogSchedulServiceTest {
//    @Test
    public void testSchedul() throws Exception {

    }
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/temp/common/common/schedule/service/spring-quartz-wf.xml");
    }
}