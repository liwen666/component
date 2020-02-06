package com.temp.common.base.JDBC.jrx;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.temp.common.base.JDBC.greeplum.JdbcUtils;
import com.temp.common.base.util.PrimaryKeyGeneratorByUuid;
import com.temp.common.common.util.DateUtils;
import com.temp.common.common.util.IdGenerator;
import net.minidev.json.writer.BeansMapper;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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
        ResultSet resultSet = statement.executeQuery("select * from user1");
        while (resultSet.next()) {
            String id_ = resultSet.getString("name");
            System.out.println(id_);

        }

    }

    @Test
    public void query() {
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("select * from user1 where user1.uuid='22'");
        System.out.println(JSON.toJSONString(stringObjectMap));
        List query = jdbcTemplate.queryForList("select * from user1"
        );

    }

    @Test
    public void insert() {
        List<UserTest> list = new ArrayList<>();
        int j = 0;
        for (int i = 1011; i < 1030; i++) {
            UserTest build = UserTest.builder()
                    .idx_key(IdGenerator.getNext())
                    .uuid(i + "")
                    .user_name("用户" + i)
                    .cj_wd1("维度2---" + i)
                    .cj_wd2("维度2---")
                    .createTime(DateUtils.addDays(new Date(), j)).build();
            j++;
            list.add(build);
        }
        batchCarFlowInsert(list);
    }

    public void batchCarFlowInsert(List<UserTest> list) {

        String sql = " INSERT INTO user1 (idx_key ,uuid,username,cj_wd2,cj_wd1,create_time) " +
                " VALUES (?, ?, ?, ?,?,?) ";
        List<Object[]> args = transformFlowCarReportDayBoToObjects(list);
        int fromIndex = 0;
        int toIndex = 100;
        while (fromIndex != args.size()) {
            if (toIndex > args.size()) {
                toIndex = args.size();
            }
            this.jdbcTemplate.batchUpdate(sql, args.subList(fromIndex, toIndex));
            fromIndex = toIndex;
            toIndex += 100;
            if (toIndex > args.size())
                toIndex = args.size();
        }

    }

    private List<Object[]> transformFlowCarReportDayBoToObjects(List<UserTest> flowCarReportDayBoList) {

        List<Object[]> list = new ArrayList<>();

        Object[] object = null;
        for (UserTest userTest : flowCarReportDayBoList) {
            object = new Object[]{
                    userTest.getIdx_key(),
                    userTest.getUuid(),
                    userTest.getUser_name(),
                    userTest.getCj_wd2(),
                    userTest.getCj_wd1(),
                    userTest.getCreateTime()
            };
            list.add(object);
        }

        return list;
    }

    @Test
    public void jdbctempleteTest() {
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("select * from user1");
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


    /**
     * pg 数据 释放锁
     */
    @Test
    public void cacleLok() {
//        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("select oid from pg_class where relname='ods_app_customer_basic_info'");
//        List query = jdbcTemplate.query("select oid from pg_class where relname='ods_app_customer_basic_info'", new BeanPropertyRowMapper(String.class));
        List<Integer> query = jdbcTemplate.query("select oid from pg_class where relname='ods_app_customer_basic_info'", new SingleColumnRowMapper(Integer.class));
        Integer objid = query.get(0);
        List<Integer> pids = jdbcTemplate.query("select pid from pg_locks where relation='35633'", new SingleColumnRowMapper(Integer.class));

//        select pid from pg_locks where relation='35633'
//        select pg_cancel_backend()
    }
}
