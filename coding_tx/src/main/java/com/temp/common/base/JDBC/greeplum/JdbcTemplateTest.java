package com.temp.common.base.JDBC.greeplum;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * author lw
 * date 2019/8/30  19:54
 * discribe
 */
public class JdbcTemplateTest {
    private static JdbcTemplate jdbcTemplate;

    static {
        DruidDataSource dataSourceSSO = new DruidDataSource();

        //设置连接参数
        dataSourceSSO.setDriverClassName("com.pivotal.jdbc.GreenplumDriver");
        dataSourceSSO.setUrl("jdbc:pivotal:greenplum://172.16.101.19:5432;DatabaseName=zzz_data_tpl");
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

    @Test
    public void gpSelect() throws SQLException, ClassNotFoundException {
        Connection greenplumConnection = JdbcUtils.getGreenplumConnection();
        Statement statement = greenplumConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from zzz_data_tpl.gp_toolkit.user_tbl");
        while (resultSet.next()) {
            String id_ = resultSet.getString("name");
            System.out.println(id_);

        }

    }


    @Test
    public void jdbctempleteTest() {
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("select * from zzz_data_tpl.gp_toolkit.user_tbl");
        System.out.println(JSON.toJSONString(stringObjectMap));
    }



    @Test
    public void update() throws IllegalAccessException {
        String sql = "";
        jdbcTemplate.update(sql);

    }

    @Test
    public void modify() throws IllegalAccessException {
        update();
        String sql = "update merchant_info set recv_pay_ways=? where uid =? ";
        jdbcTemplate.update(sql, new Object[]{"FDAFD", 1});
    }


    @Test
    public void testSelect() {

        String sql = "select count(*) from myclass";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);//此处返回数据的总数（一个数值）
        System.out.println(count);
    }
}