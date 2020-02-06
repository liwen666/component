package com.temp.common.base.JDBC.jrx;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.fastjson.JSON;
import com.temp.common.base.JDBC.greeplum.JdbcUtils;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.DataTypeEnum;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.JdbcAssertUtil;
import com.temp.common.common.util.DateUtils;
import com.temp.common.common.util.IdGenerator;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * author lw
 * date 2019/8/30  19:54
 * discribe
 */
public class LocalJdbcTemplate {
    private static JdbcTemplate loalJdbcTemplate;

    static {
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
        loalJdbcTemplate = new JdbcTemplate(dataSourceSSO);
        System.out.println("初始化jdbctemplate" + loalJdbcTemplate);
    }


    @Test
    public void query() {
        Map<String, Object> stringObjectMap = loalJdbcTemplate.queryForMap("select * from user1 where user1.uuid='22'");
        System.out.println(JSON.toJSONString(stringObjectMap));
        List query = loalJdbcTemplate.queryForList("select * from user1"
        );
        System.out.println(JSON.toJSONString(query));

    }

    @Test
    public void queryJrx() {
        JdbcTemplate jrxDb = JdbcTemplateUtils.getJrxDb();
        Map<String, Object> stringObjectMap = jrxDb.queryForMap("select * from user1 where user1.uuid='22'");
        System.out.println(JSON.toJSONString(stringObjectMap));

    }


    @Test
    public void attrTable() {
        String tableName="user1";
        /**
         * 远程数据库表
         */
        RowCountCallbackHandler rowCountCallbackHandler = new RowCountCallbackHandler();
        JdbcTemplateUtils.getJrxDb().query("select * from "+tableName, rowCountCallbackHandler);
        String[] columnNames = rowCountCallbackHandler.getColumnNames();
        int[] columnTypes = rowCountCallbackHandler.getColumnTypes();

        /**
         * 本地数据库表
         */
        RowCountCallbackHandler rowCountCallbackHandler1 = new RowCountCallbackHandler();
        insertOne(loalJdbcTemplate);
        loalJdbcTemplate.query("select * from "+tableName, rowCountCallbackHandler1);
        String[] localName = rowCountCallbackHandler1.getColumnNames();
        int[] localType = rowCountCallbackHandler1.getColumnTypes();
        List<String> strings = Arrays.asList(localName);
        Map<String, String> addColumnMap = new HashMap<>();
        for (int i = 0; i < columnNames.length; i++) {
            if (!strings.contains(columnNames[i])) {
                addColumnMap.put(columnNames[i], JdbcAssertUtil.covince(columnTypes[i], DataTypeEnum.GREENPLUM));
            }
        }

        /**
         * 添加字段
         */

        addColumnMap.keySet().forEach(key -> {
            String type = addColumnMap.get(key);
            String sql = getAddColumnSql(tableName, key, type);
            loalJdbcTemplate.update(sql);
        });

        /**
         * 同步数据
         * 查询远程数据
         */
        List<UserTest> query = JdbcTemplateUtils.getJrxDb().query("select * from "+tableName, new BeanPropertyRowMapper<>(UserTest.class));
        /**
         * 清理本地数据
         */

//        loalJdbcTemplate.update("delete from user1");
        loalJdbcTemplate.update("TRUNCATE user1");
        /**
         * 添加数据
         */
        batchCarFlowInsert(query, loalJdbcTemplate);

    }


    /**
     * 添加字段
     * ALTER TABLE 表明 add 字段名称 类型(int,char,VARCHAR...) DEFAULT 默认值  位置（FIRST, AFTER+字段名称）;
     * alter table <表名> alter column <字段名> 新类型名(长度)
     */
    public String getAddColumnSql(String tableName, String column, String type) {
        return "alter table " + tableName + " add " + column + "  " + type + "(100)";
    }



    /**
     * 同步表结构
     */

    public void insertOne(JdbcTemplate loalJdbcTemplate) {
        List<UserTest> list = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < 1; i++) {
            UserTest build = UserTest.builder()
                    .idx_key(IdGenerator.getNext())
                    .uuid(i + "")
                    .user_name("用户dd" + i)
                    .cj_wd1("维度2dd---" + i)
                    .cj_wd2("维度2dd---")
                    .createTime(DateUtils.addDays(new Date(), j)).build();
            j++;
            list.add(build);
        }
        batchCarFlowInsert(list, loalJdbcTemplate);
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
        batchCarFlowInsert(list, loalJdbcTemplate);
    }

    public void batchCarFlowInsert(List<UserTest> list, JdbcTemplate jdbcTemplate) {

        String sql = " INSERT INTO user1 (idx_key ,uuid,username,cj_wd2,cj_wd1,create_time) " +
                " VALUES (?, ?, ?, ?,?,?) ";
        List<Object[]> args = transformFlowCarReportDayBoToObjects(list);
        int fromIndex = 0;
        int toIndex = 100;
        while (fromIndex != args.size()) {
            if (toIndex > args.size()) {
                toIndex = args.size();
            }
            jdbcTemplate.batchUpdate(sql, args.subList(fromIndex, toIndex));
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
        Map<String, Object> stringObjectMap = loalJdbcTemplate.queryForMap("select * from user1");
        System.out.println(JSON.toJSONString(stringObjectMap));
    }


    @Test
    public void update() throws IllegalAccessException {
        String sql = "";
        loalJdbcTemplate.update(sql);

    }

    @Test
    public void modify() throws IllegalAccessException {
        update();
        String sql = "update merchant_info set recv_pay_ways=? where uid =? ";
        loalJdbcTemplate.update(sql, new Object[]{"FDAFD", 1});
    }


    @Test
    public void testSelect() {

        String sql = "select count(*) from myclass";
        int count = loalJdbcTemplate.queryForObject(sql, Integer.class);//此处返回数据的总数（一个数值）
        System.out.println(count);
    }


    /**
     * pg 数据 释放锁
     */
    @Test
    public void cacleLok() {
//        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("select oid from pg_class where relname='ods_app_customer_basic_info'");
//        List query = jdbcTemplate.query("select oid from pg_class where relname='ods_app_customer_basic_info'", new BeanPropertyRowMapper(String.class));
        List<Integer> query = loalJdbcTemplate.query("select oid from pg_class where relname='ods_app_customer_basic_info'", new SingleColumnRowMapper(Integer.class));
        Integer objid = query.get(0);
        List<Integer> pids = loalJdbcTemplate.query("select pid from pg_locks where relation='35633'", new SingleColumnRowMapper(Integer.class));

//        select pid from pg_locks where relation='35633'
//        select pg_cancel_backend()
    }
}
