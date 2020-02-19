package com.temp.common.base.JDBC.jrx.jrx_bi_demo;

import com.temp.common.base.JDBC.jrx.JdbcTemplateUtils;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface.CityEnum;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface.ModelTest;
import com.temp.common.common.util.IdGenerator;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import java.lang.reflect.Field;
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
    public static void main(String[] args) {
        JdbcTemplate source = JdbcTemplateUtils.getJrxDemoDb();
        JdbcTemplate target = JdbcTemplateUtils.getLocalDb();
        String sourceSql = "SELECT *,\n" +
                "       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,\n" +
                "       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,\n" +
                "       credit_amount AS credit_amount_group\n" +
                "FROM md_cm_app_case_formatted limit 1 ";
        String targetTableName = "md_cm_app_case_formatted";
        /**
         * 创建表
         */
        createTable(source, target, sourceSql, targetTableName);
        /**
         * 复制数据
         */
        RowCountCallbackHandler rowCountCallbackHandler = new RowCountCallbackHandler();
        source.query(sourceSql, rowCountCallbackHandler);

        String dataSql = "SELECT *,\n" +
                "       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,\n" +
                "       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,\n" +
                "       credit_amount AS credit_amount_group\n" +
                "FROM md_cm_app_case_formatted limit 10000";
        List<Map<String, Object>> dataList = source.queryForList(dataSql);

        insertData(target, targetTableName, rowCountCallbackHandler.getColumnNames(), dataList);


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


        JdbcTemplate localDb = JdbcTemplateUtils.getLocalDb();
        ModelTest build = ModelTest.builder().address("潢川县").city("信阳市").province("河南省").create_time(new Date()).area_code("00001").id_card("1").idx_key(IdGenerator.getNext()).build();
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

//        JdbcTemplate localDb = JdbcTemplateUtils.getLocalDb();
        JdbcTemplate localDb = JdbcTemplateUtils.getJrxDb();
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
                        .id_card("1").idx_key(IdGenerator.getNext()).user_name(values[i].getName()+"用户--"+ran1).build();

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
    }



}
