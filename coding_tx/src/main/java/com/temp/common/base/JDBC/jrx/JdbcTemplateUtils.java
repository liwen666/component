package com.temp.common.base.JDBC.jrx;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.sql.SQLException;
import java.util.List;

public class JdbcTemplateUtils {


    // 1、Postgresql
    public static JdbcTemplate getJrxDb()  {
        DruidDataSource dataSourceSSO = new DruidDataSource();

        //设置连接参数
//        dataSourceSSO.setDriverClassName("com.pivotal.jdbc.GreenplumDriver");
//        dataSourceSSO.setUrl("jdbc:pivotal:greenplum://172.16.101.19:5432;DatabaseName=any_est_lw");
        dataSourceSSO.setDriverClassName("org.postgresql.Driver");
        dataSourceSSO.setUrl("jdbc:postgresql://172.16.101.93:5432/any_est_lw?reWriteBatchedInserts=true&nullCatalogMeansCurrent=true&currentSchema=public");
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
        dataSourceSSO.setRemoveAbandoned(true);
        dataSourceSSO.setRemoveAbandonedTimeout(2000);
        //防止过期
        dataSourceSSO.setValidationQuery("SELECT 'x'");
        dataSourceSSO.setTestWhileIdle(true);
        dataSourceSSO.setTestOnBorrow(true);
       return  new JdbcTemplate(dataSourceSSO);
    }

    // 1、Postgresql
    public static JdbcTemplate getJrxDemoDb()  {
        DruidDataSource dataSourceSSO = new DruidDataSource();

        //设置连接参数
//        dataSourceSSO.setDriverClassName("com.pivotal.jdbc.GreenplumDriver");
//        dataSourceSSO.setUrl("jdbc:pivotal:greenplum://172.16.101.19:5432;DatabaseName=any_est_lw");
        dataSourceSSO.setDriverClassName("com.pivotal.jdbc.GreenplumDriver");
        dataSourceSSO.setUrl("jdbc:pivotal:greenplum://172.16.103.105:5432;DatabaseName=zzz_data_tpl");
        dataSourceSSO.setUsername("gpadmin");
        dataSourceSSO.setPassword("gpadmin");
        //配置初始化大小、最小、最大
        dataSourceSSO.setInitialSize(1);
        dataSourceSSO.setMinIdle(1);
        dataSourceSSO.setMaxActive(20);
        //连接泄漏监测
        /**
         * 控制连接超时强制关闭连接时间
         */
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
       return  new JdbcTemplate(dataSourceSSO);
    }


    // 1、Postgresql
    public static JdbcTemplate getLocalDb()  {
        DruidDataSource dataSourceSSO = new DruidDataSource();

        //设置连接参数
        dataSourceSSO.setDriverClassName("org.postgresql.Driver");
        dataSourceSSO.setUrl("jdbc:postgresql://192.168.42.136:5432/postgres?reWriteBatchedInserts=true&nullCatalogMeansCurrent=true&currentSchema=public");
        dataSourceSSO.setUsername("postgres");
        dataSourceSSO.setPassword("postgres");
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
        return  new JdbcTemplate(dataSourceSSO);
    }


    // 1、Postgresql
    public static JdbcTemplate getLocalMysql()  {
        DruidDataSource dataSourceSSO = new DruidDataSource();

        //设置连接参数
        dataSourceSSO.setDriverClassName("com.mysql.jdbc.Driver");
        dataSourceSSO.setUrl("jdbc:mysql://192.168.42.136:3306/data_flow_test?serverTimezone=Hongkong&useUnicode=true&useSSL=false&characterEncoding=utf8");
        dataSourceSSO.setUsername("root");
        dataSourceSSO.setPassword("root");
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
        return  new JdbcTemplate(dataSourceSSO);
    }

    public static void main(String[] args) {
        JdbcTemplate jrxDbGpData = getJrxDemoDb();
        List<Integer> query = jrxDbGpData.query("select count(1) from md_cm_app_case_formatted ", new SingleColumnRowMapper<>(Integer.class));

    }
//    // 2、Greenplum
//    public static Connection getGreenplumConnection() throws ClassNotFoundException, SQLException {
//        // 加载数据库驱动
//        Class.forName(greenplum_driver);
//        //System.out.println("测试加载数据库成功");
//        Connection con = DriverManager.getConnection(greenplum_url, greenplum_user, greenplum_password);
//        //System.out.println("测试数据库链接成功");
//        return con;
//    }

}