package com.temp.common.transaction.controller;
import com.temp.common.transaction.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:com/temp/common/transaction/applicationContextOracle.xml")
@Controller
public class TestController {   
    @Autowired
    private TestService testService;
    //MySQL的数据库引擎必须是InnoDB，否则无法回滚
    @Test
    public void test(){
        testService.test();
    }

    @Test
    public void test2(){
        testService.update();
    }

    public void test3(){
        testService.test3();
    }
    public static void main(String[] args) {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("com/temp/common/transaction/applicationContextOracle.xml");
    	System.out.println(ctx);
    	System.out.println(ctx.getBean("masterJdbcTemplate"));
    	
    	JdbcTemplate masterJdbcTemplate = (JdbcTemplate) ctx.getBean("masterJdbcTemplate");
    	System.out.println(masterJdbcTemplate.queryForObject("select 1 from dual",Integer.class));
     masterJdbcTemplate.execute("insert into  master (name) values('master')");
    	
    	
    	
	}
}