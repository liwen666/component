package com.temp.common.base.JDBC;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mysql.jdbc.Driver;
import oracle.sql.BLOB;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class ConnectionTx {
    //    private Driver
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
//        System.out.println(DriverManager.getDrivers());
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.42.136:3306/hqbpmn", "root", "root");

        System.out.println(connection + "000");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * FROM act_id_user");
        while (resultSet.next()) {
            String id_ = resultSet.getString("id_");
            System.out.println(id_);

        }

    }

    static class OracleConnerct {
        public static void main(String[] args) throws SQLException, ClassNotFoundException {
            Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "lw_bpmn", "1");
            System.out.println(connection + "000");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * FROM act_id_user");
            while (resultSet.next()) {
                String id_ = resultSet.getString("id_");
                System.out.println(id_);

            }
        }
    }

    static class DataSourceTx {
        public static void main(String[] args) throws IOException, SQLException {
            DruidDataSource druidDataSource = new DruidDataSource();
            Properties prop = new Properties();
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream(new FileInputStream("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\JDBC\\jdbc.properties"));
            prop.load(in);
            druidDataSource.setConnectProperties(prop);
            DruidPooledConnection connection = druidDataSource.getConnection();
            System.out.println(connection + "000");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * FROM act_id_user");
            while (resultSet.next()) {
                String id_ = resultSet.getString("id_");
                System.out.println(id_);

            }
        }
    }

    static class DataSourceBlobTx {
        public static void main(String[] args) throws SQLException, IOException {
            /**
             * 从一个库移到另一个库
             * **/
            String url = "XXXXXXXXX";
            String driver = "";
            String username = "XXXXXX";
            String password = "XXXXXX";
            Connection con = null;
            PreparedStatement ps = null;
            InputStream input = null;
            OutputStream osM = null;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            try {
                con = DriverManager.getConnection(url, username, password);
                con.setAutoCommit(false);//关闭自动提交
                String MuSql = "select image from sys_image where user_id = 'icc'";
                ps = con.prepareStatement(MuSql);
                ResultSet rs = ps.executeQuery();
                BLOB blob = null;
                while (rs.next()) {
                    blob = (BLOB) rs.getBlob("image");
                }
                input = blob.getBinaryStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int l = 0;
                while ((l = input.read(b)) != -1) {
                    baos.write(b, 0, l);
                }

                String updateSql = "insert into  sys_image_test values('yzy',?,sysdate)";
                ps = con.prepareStatement(updateSql);
                ps.setBytes(1, baos.toByteArray());
                ps.executeUpdate();
                input.close();
                baos.close();
                con.commit();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                con.rollback();
            } finally {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }

            }

        }
    }
}
