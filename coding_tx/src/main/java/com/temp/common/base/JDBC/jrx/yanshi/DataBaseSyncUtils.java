package com.temp.common.base.JDBC.jrx.yanshi;

import com.temp.common.base.JDBC.jrx.JdbcTemplateUtils;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.JdbcAssertUtil;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface.CityEnum;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface.ModelTest;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface.TaskExecution;
import com.temp.common.common.util.IdGenerator;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class DataBaseSyncUtils {


    @Test
    public void initMapSql() {
        JdbcTemplate source = JdbcTemplateUtils.getJrxDb93();
        JdbcTemplate target = JdbcTemplateUtils.getLocalMysqlGp();
        String sourceSql = "select * from dash_map_test ";
        String targetTableName = "dash_map_test";
        /**
         * 创建表
         */
        createTable(source, target, sourceSql, targetTableName);
//        /**
//         * 复制数据
//         */
//        RowCountCallbackHandler rowCountCallbackHandler = new RowCountCallbackHandler();
//        source.query(sourceSql, rowCountCallbackHandler);
//
//        String dataSql = "SELECT *,\n" +
//                "       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,\n" +
//                "       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,\n" +
//                "       credit_amount AS credit_amount_group\n" +
//                "FROM md_cm_app_case_formatted limit 10000";
//        List<Map<String, Object>> dataList = source.queryForList(dataSql);
//
//        insertData(target, targetTableName, rowCountCallbackHandler.getColumnNames(), dataList);
    }

    public static void createTable(JdbcTemplate soucce, JdbcTemplate target, String sourceSql, String targetTableName) {
        String createTb = JdbcAssertUtil.getCreateTableSql(soucce, sourceSql, targetTableName);
        target.update(createTb);
    }

    public static void insertData(JdbcTemplate target, String tableName, String[] coloumName, List<Map<String, Object>> dataList) {
        JdbcAssertUtil.batchCarFlowInsert(dataList, target, tableName, coloumName);
    }

    @Test
    public void insertData() {


//        JdbcTemplate localDb = JdbcTemplateUtils.getLocalDb();
        JdbcTemplate localDb = JdbcTemplateUtils.getLocalMysql();
        ModelTest build = ModelTest.builder().address("潢川县").city("信阳市").province("河南省").create_time(new Date()).area_code("00001").id_card("1").build();
        Field[] fields = ModelTest.class.getDeclaredFields();
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
        String insertSql = JdbcAssertUtil.getInsertSql(cols, "dash_map_test");
        System.out.println(insertSql);
        localDb.update(insertSql, vals);
    }

    @Test
    public void cityDataTest() {

        CityEnum[] values = CityEnum.values();
        JdbcTemplate localDb = JdbcTemplateUtils.getYanshiMsql();
        int max=100,min=4;

        for (int i=0;i<values.length;i++) {
            int ran = (int) (Math.random()*(max-min)+min);
            String code = values[i].getCode();
            String code1=code+"市";
//            String code2=code+"县";
            Random random = new Random(1);
            for(int j=0;j<ran;j++){
                int ran1 = random.nextInt(3)+1;
                ModelTest build = ModelTest.builder().address(values[i].getName()+"县"+ran1).city(values[i].getName() + "--市"+ran1).province(values[i].getName()).
                        create_time(new Date())
                        .area_code(code1+1)
                        .parent_area_code(code)
                        .id_card("1").user_name(values[i].getName()+"用户--"+ran1).build();

                Field[] fields = ModelTest.class.getDeclaredFields();
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

                String insertSql = JdbcAssertUtil.getInsertSql(cols, "test_table_data");
                localDb.update(insertSql, vals);
            }

        }
    }

    @Test
    public void create_city() {

        CityEnum[] values = CityEnum.values();

//        JdbcTemplate localDb = JdbcTemplateUtils.getLocalDb();
        JdbcTemplate localDb = JdbcTemplateUtils.getLocalMysqlGp();
        int max=100,min=4;

            int ran = (int) (Math.random()*(max-min)+min);
            String code = CityEnum.BEIJING.getCode();
            String code1=code+"市";
//            String code2=code+"县";
            Random random = new Random(1);
String[] city={"海淀区","门头沟区","朝阳区","丰台去","昌平区","怀柔区","延庆区","平谷区"};

            for(int j=0;j<ran;j++){
                int ran1 = random.nextInt(8);
                ModelTest build = ModelTest.builder().address(CityEnum.BEIJING.getName()+city[ran1]+"-县").city(city[ran1]).province(CityEnum.BEIJING.getName()).
                        create_time(new Date())
                        .area_code(code1+1)
                        .parent_area_code(code)
                        .id_card("1").user_name(CityEnum.BEIJING.getName()+"用户--"+city[ran1]).build();

                Field[] fields = ModelTest.class.getDeclaredFields();
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

                String insertSql = JdbcAssertUtil.getInsertSql(cols, "dash_map_test");
                localDb.update(insertSql, vals);
            }
    }


    @Test
    public void testMaxWell() {
        JdbcTemplate localDb = JdbcTemplateUtils.getLocalMysql();
        TaskExecution build = TaskExecution.builder().build();
        build.setEndTime(LocalDateTime.now());
        build.setErrorMessage("com");
        build.setTaskExecutionId(11111l);
        build.setStartTime(LocalDateTime.now());
        Field[] fields = TaskExecution.class.getDeclaredFields();
        String[] cols = Arrays.asList(fields).stream().filter(e -> !e.getName().equals("log")&&!e.getName().equals("serialVersionUID")).map(e -> e.getName()).collect(Collectors.toList()).toArray(new String[0]);
        Object[] vals = Arrays.asList(fields).stream().filter(e -> !e.getName().equals("log")&&!e.getName().equals("serialVersionUID")).map(e -> {
            try {

                e.setAccessible(true);
                System.out.println("******************************************************");
                System.out.println(e.getName());
                Object o = e.get(build);
                System.out.println(o);
                return e.get(build);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList()).toArray(new Object[0]);

        String insertSql = JdbcAssertUtil.getInsertSql(cols, "task_execution");
        localDb.update(insertSql, vals);
    }
}
