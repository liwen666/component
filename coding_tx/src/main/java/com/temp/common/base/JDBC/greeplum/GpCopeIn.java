package com.temp.common.base.JDBC.greeplum;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import lombok.val;
import org.junit.Test;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * author lw
 * date 2019/8/30  19:54
 * discribe
 */
public class GpCopeIn {
    private static JdbcTemplate jdbcTemplate;

    static {
        DruidDataSource dataSourceSSO = new DruidDataSource();

        //设置连接参数
        dataSourceSSO.setDriverClassName("com.pivotal.jdbc.GreenplumDriver");
//        dataSourceSSO.setUrl("jdbc:pivotal:greenplum://172.16.101.19:5432;DatabaseName=zzz_data_tpl");
        dataSourceSSO.setUrl("jdbc:pivotal:greenplum://10.0.22.87:5432;DatabaseName=anyest");
        dataSourceSSO.setUsername("gpadmin");
        dataSourceSSO.setPassword("gpadmin");
        //配置初始化大小、最小、最大
        dataSourceSSO.setInitialSize(1);
        dataSourceSSO.setMinIdle(1);
        dataSourceSSO.setMaxActive(20);
        //连接泄漏监测
        dataSourceSSO.setRemoveAbandoned(true);
        dataSourceSSO.setRemoveAbandonedTimeout(30);
        //配置获取连接等待超时的时间
        dataSourceSSO.setMaxWait(20000);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSourceSSO.setTimeBetweenEvictionRunsMillis(20000);
        //防止过期
        dataSourceSSO.setValidationQuery("SELECT 'x'");
        dataSourceSSO.setTestWhileIdle(true);
        dataSourceSSO.setTestOnBorrow(true);
        jdbcTemplate = new JdbcTemplate(dataSourceSSO);
        System.out.println("初始化jdbctemplate" + jdbcTemplate);


    }
    public static void copeToFile(Connection connection,String filePath,String tableOrQuery) throws SQLException, IOException {
        CopyManager copyManager = new CopyManager((BaseConnection) connection);
        FileOutputStream fileOutputStream1 = new FileOutputStream(filePath);
        String copyOut="COPY "+tableOrQuery +" TO STDOUT DELIMITER AS ',' ";
        final long line = copyManager.copyOut(copyOut,fileOutputStream1);
        System.out.println(line);
    }

    public static void copyFromFile(Connection connection, String filePath, String tableName)
            throws SQLException, IOException {

        FileInputStream fileInputStream = null;

        try {
            CopyManager copyManager = new CopyManager((BaseConnection)connection);
            fileInputStream = new FileInputStream(filePath);
            copyManager.copyIn("COPY " + tableName + " FROM STDIN  DELIMITER AS ',' ", fileInputStream);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    public void copeOut() throws SQLException, IOException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://10.0.22.87:5432/anyest?reWriteBatchedInserts=true&nullCatalogMeansCurrent=true&currentSchema=public", "gpadmin", "gpadmin");
        GpCopeIn.copeToFile(connection,"D:\\workspace\\test_workspace\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\JDBC\\greeplum\\sql.txt","  gp_record ");
    }

    @Test
    public void copeIn() throws SQLException, IOException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://10.0.22.87:5432/anyest?reWriteBatchedInserts=true&nullCatalogMeansCurrent=true&currentSchema=public", "gpadmin", "gpadmin");
        GpCopeIn.copyFromFile(connection,"D:\\workspace\\test_workspace\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\JDBC\\greeplum\\sql.txt","  gp_record_copy1 ");
    }

    @Test
    public void jdbctempleteTest() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("  select * from gp_record ");
        System.out.println(JSON.toJSONString(maps));
    }

}
