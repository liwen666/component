package com.temp.common.common.schedule.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-wf_web.xml")
public class TestContext {
    @Autowired
    ApplicationContext ctx;

    @Test
    public void getBean() throws Exception {
        System.out.println(ctx);
        System.out.println(ctx.getBean("processEngine"));
        System.out.println(ctx.getBean("busiDataSource"));
        DataSource dataSource = (DataSource) ctx.getBean("busiDataSource");
        Connection connection = dataSource.getConnection();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select 111 from dual");
//        do {
//            先做上面的代码，然后在执行resultSet.next，行不通在这里
//            resultSet.getInt(1);
//
//        }while (resultSet.next());
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }


    }

//    3.1 读取方法1 – 通过索引来遍历读取
//    while(rs.next()){
//        int id = rs.getInt(1);
//        String name = rs.getString(2);
//        String gender = rs.getString(3);
//        System.out.println("id:"+id+" 姓名："+name+" 性别："+gender);
//    }
//1
//        3.2 读取方法2 – 通过字段名称来读取
//    强调一下，这个传入的字段名称可以不区分大小写，因为在mysql中就是不区分的
//
//    while(rs.next()){
//        int id = rs.getInt("id");
//        String name = rs.getString("name");
//        String gender = rs.getString("gender");
//        System.out.println("id:"+id+" 姓名："+name+" 性别："+gender);
//    }

    @Test
    public void teContex() throws Exception {


    }
}
