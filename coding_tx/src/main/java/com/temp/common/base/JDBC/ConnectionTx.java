package com.temp.common.base.JDBC;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mysql.jdbc.Driver;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class ConnectionTx {
    //    private Driver
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
//        System.out.println(DriverManager.getDrivers());
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.42.136:3306/hqbpmn", "root", "root");

        System.out.println(connection+"000");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * FROM act_id_user");
        while (resultSet.next()){
            String id_ = resultSet.getString("id_");
            System.out.println(id_);

        }

    }
    static  class  OracleConnerct{
        public static void main(String[] args) throws SQLException, ClassNotFoundException {
            Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "lw_bpmn", "1");
            System.out.println(connection+"000");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * FROM act_id_user");
            while (resultSet.next()){
                String id_ = resultSet.getString("id_");
                System.out.println(id_);

            }
        }
    }
    static class DataSourceTx{
        public static void main(String[] args) throws IOException, SQLException {
            DruidDataSource druidDataSource = new DruidDataSource();
            Properties prop = new Properties();
                //读取属性文件a.properties
                InputStream in = new BufferedInputStream(new FileInputStream("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\JDBC\\jdbc.properties"));
                prop.load(in);
            druidDataSource.setConnectProperties(prop);
            DruidPooledConnection connection = druidDataSource.getConnection();
            System.out.println(connection+"000");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * FROM act_id_user");
            while (resultSet.next()){
                String id_ = resultSet.getString("id_");
                System.out.println(id_);

            }
        }
    }
}
