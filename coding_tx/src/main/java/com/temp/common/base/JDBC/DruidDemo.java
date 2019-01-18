package com.temp.common.base.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.alibaba.druid.pool.DruidDataSource;

public class DruidDemo {
    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.42.136:3306/hqbpmn?useUnicode=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        try{
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：
            String sql = "select * from act_id_user";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("id_"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{

        }

    }

}