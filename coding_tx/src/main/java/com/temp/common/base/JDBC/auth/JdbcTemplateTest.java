package com.temp.common.base.JDBC.auth;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.google.common.base.CaseFormat;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        dataSourceSSO.setDriverClassName("com.mysql.jdbc.Driver");
        dataSourceSSO.setUrl("jdbc:mysql://192.168.42.136:3306/davinci_test?serverTimezone=Hongkong&useUnicode=true&useSSL=false&characterEncoding=utf8");
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
        jdbcTemplate = new JdbcTemplate(dataSourceSSO);
        System.out.println("初始化jdbctemplate" + jdbcTemplate);
    }

    /**
     * 查询用户
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void queryUser() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from USER");
        System.out.println(JSON.toJSONString(maps));
    }

    /**
     * 添加用户
     */

    /**
     * 密码加密
     */
    @Test
    public void name() {
        String hashpw = BCrypt.hashpw("123456789", BCrypt.gensalt());
        System.out.println(hashpw);
        boolean checkpw = BCrypt.checkpw("123456789", hashpw);
        System.out.println(checkpw);
    }

    @Test
    public void insertData() {
        String hashpw = BCrypt.hashpw("123456789", BCrypt.gensalt());
        System.out.println(hashpw);
        boolean checkpw = BCrypt.checkpw("123456789", hashpw);
        System.out.println(checkpw);
        User build = User.builder().active(true).username("测试用户").updateBy(0l).createTime(LocalDateTime.now()).createBy(0l).email("email").admin(true).password(hashpw).build();
        Field[] fields = User.class.getDeclaredFields();
        String[] cols = Arrays.asList(fields).stream().filter(e -> !e.getName().equals("log")).map(e -> e.getName()).collect(Collectors.toList()).toArray(new String[0]);
        Object[] vals = Arrays.asList(fields).stream().filter(e -> !e.getName().equals("log")).map(e -> {
            try {
                e.setAccessible(true);
                return e.get(build);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList()).toArray(new Object[0]);
        String insertSql = getInsertSql(cols, "USER");
        System.out.println(insertSql);
        jdbcTemplate.update(insertSql, vals);
    }

    public static String getInsertSql(String[] columnNames, String tableName) {
        StringBuffer insert = new StringBuffer(" INSERT INTO " + tableName + "(");
        StringBuffer value = new StringBuffer(" VALUES (");

        for (String column : columnNames) {
            String to = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);
            insert.append(to + " , ");
            value.append("?,");
        }
        value.delete(value.length() - 1, value.length());
        insert.delete(insert.length() - 2, insert.length());
        insert.append(")");
        insert.append(value + ")");
        return insert.toString();
    }
}
