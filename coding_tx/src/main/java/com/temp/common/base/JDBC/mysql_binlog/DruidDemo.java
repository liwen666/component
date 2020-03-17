package com.temp.common.base.JDBC.mysql_binlog;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DruidDemo {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.42.136:3306/anyest3_1125?useUnicode=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        try {
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：
            String sql = "select * from testmaxwell";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            rs = pstmt.executeQuery();
            while (rs.next()) {
//                System.out.println(rs.getString());
                System.out.println(rs.getObject(1));
                System.out.println(rs.getObject(2));
                System.out.println(rs.getObject(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    @Test
    public void insertObj() {

//        create cast (varchar as timestamp) with inout as ASSIGNMENT;
//
//create cast (varchar as timestamptz) with inout as ASSIGNMENT;
//
//create cast (varchar as date) with inout as ASSIGNMENT;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.42.136:3306/test_data_sync?useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        try {
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：


            String sql = "insert into test_maxwell_data (time_stamp_test,name,time2) values (now(),'测试',now())";
//            String sql = "insert into testmaxwell (time,name) values (now(),'测试')";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            for(int i=1;i<1000;i++){
                Thread.sleep(1000);
                pstmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
    @Test
    public void column() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.42.136:3306/anyest3_1125?useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        try {
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：
            String sql = "SELECT column_name,column_comment,data_type,table_name \n" +
                    "\n" +
                    "FROM information_schema.columns \n" +
                    "\n" +
                    "WHERE data_type='timestamp'";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            rs = pstmt.executeQuery();
            while (rs.next()) {
//                System.out.println(rs.getString());
                System.out.println(rs.getObject(1));
                System.out.println(rs.getObject(2));
                System.out.println(rs.getObject(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}