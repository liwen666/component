package com.temp.common.base.JDBC.greeplum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcUtils {

    // 1、Postgresql
    private static String postgresql_driver;
    private static String postgresql_url;
    private static String postgresql_user;
    private static String postgresql_password;
        
    // 2、Greenplum
    private static String greenplum_driver;
    private static String greenplum_url;
    private static String greenplum_user;
    private static String greenplum_password;

    // 1、Postgresql
    static {
        postgresql_driver = ResourceBundle.getBundle("db").getString("posgresql_driver");
        postgresql_url = ResourceBundle.getBundle("db").getString("posgresql_url");
        postgresql_user = ResourceBundle.getBundle("db").getString("posgresql_user");
        postgresql_password = ResourceBundle.getBundle("db").getString("posgresql_password");
    }

    // 2、Greenplum
    static {
        greenplum_driver = ResourceBundle.getBundle("db").getString("greenplum_driver");
        greenplum_url = ResourceBundle.getBundle("db").getString("greenplum_url");
        greenplum_user = ResourceBundle.getBundle("db").getString("greenplum_user");
        greenplum_password = ResourceBundle.getBundle("db").getString("greenplum_password");
    }    


    // 1、Postgresql
    public static Connection getPostgresqlConnection() throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName(postgresql_driver);
        // System.out.println("测试加载数据库成功");
        Connection con = DriverManager.getConnection(postgresql_url, postgresql_user, postgresql_password);
        // System.out.println("测试数据库链接成功");
        return con;
    }

    // 2、Greenplum
    public static Connection getGreenplumConnection() throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName(greenplum_driver);
        //System.out.println("测试加载数据库成功");
        Connection con = DriverManager.getConnection(greenplum_url, greenplum_user, greenplum_password);
        //System.out.println("测试数据库链接成功");
        return con;
    }

    public static void main(String[] args) {
            try {
                JdbcUtils.getPostgresqlConnection();
                System.out.println("汇聚数据区连接成功.....");
                System.out.println("=======================================");
                
                JdbcUtils.getGreenplumConnection();
                System.out.println("核心数据区连接成功.....");
                System.out.println("=======================================");
                
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}