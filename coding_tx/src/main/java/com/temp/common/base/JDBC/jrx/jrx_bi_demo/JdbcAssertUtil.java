package com.temp.common.base.JDBC.jrx.jrx_bi_demo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.DbType;
import com.google.common.base.CaseFormat;
import com.temp.common.base.JDBC.jrx.JdbcTemplateUtils;
import com.temp.common.base.JDBC.jrx.UserTest;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import java.sql.Types;
import java.util.*;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class JdbcAssertUtil {
    public static DataTypeEnum dataTypeEnum = DataTypeEnum.MYSQL;

    /**
     * 获取表结构建表语句
     */
    public static String getCreateTableSql(JdbcTemplate jdbcTemplate, String sql, String tableName) {
        /**
         * 远程数据库表
         */
        RowCountCallbackHandler rowCountCallbackHandler = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, rowCountCallbackHandler);
        StringBuffer createTb;
        if (dataTypeEnum == DataTypeEnum.MYSQL) {

            createTb = new StringBuffer("create table if not EXISTS " + tableName + "(");
        } else {
            createTb = new StringBuffer("create table  " + tableName + "(");

        }
        String[] columnNames = rowCountCallbackHandler.getColumnNames();
        int[] columnTypes = rowCountCallbackHandler.getColumnTypes();

        for (int i = 0; i < columnNames.length; i++) {
//            System.out.println(columnNames[i] + "  ->  " + columnTypes[i]);
            createTb.append(columnNames[i] + " " + covince(columnTypes[i], DataTypeEnum.GREENPLUM) + " , ");
        }
        String substring = createTb.substring(0, createTb.length() - 2);
//        StringBuffer sbsql = new StringBuffer("create table "+tableName);
//        for(int i=0;i<columnNames.length;i++){
//        sbsql.append(" "+columnNames[i]+covince(columnTypes[i])+"(100)");
//        }


//        /**
//         * 本地数据库表
//         */
//        RowCountCallbackHandler rowCountCallbackHandler1 = new RowCountCallbackHandler();
//        insertOne(loalJdbcTemplate);
//        loalJdbcTemplate.query("select * from "+tableName, rowCountCallbackHandler1);
//        String[] localName = rowCountCallbackHandler1.getColumnNames();
//        int[] localType = rowCountCallbackHandler1.getColumnTypes();
//        List<String> strings = Arrays.asList(localName);
//        Map<String, String> addColumnMap = new HashMap<>();
//        for (int i = 0; i < columnNames.length; i++) {
//            if (!strings.contains(columnNames[i])) {
//                addColumnMap.put(columnNames[i], covince(columnTypes[i]));
//            }
//        }
//
//        /**
//         * 添加字段
//         */
//
//        addColumnMap.keySet().forEach(key -> {
//            String type = addColumnMap.get(key);
//            String sql = getAddColumnSql(tableName, key, type);
//            loalJdbcTemplate.update(sql);
//        });
//
//        /**
//         * 同步数据
//         * 查询远程数据
//         */
//        List<UserTest> query = JdbcTemplateUtils.getJrxDb().query("select * from "+tableName, new BeanPropertyRowMapper<>(UserTest.class));
//        /**
//         * 清理本地数据
//         */
//
////        loalJdbcTemplate.update("delete from user1");
//        loalJdbcTemplate.update("TRUNCATE user1");
//        /**
//         * 添加数据
//         */
//        batchCarFlowInsert(query, loalJdbcTemplate);
//
        return substring + ")";
    }

    public static String covince(int type, DataTypeEnum dbType) {
        switch (type) {
            case (Types.VARCHAR):
                return "VARCHAR(200)";
            case (Types.DATE):
                return "DATE";
            case (Types.TIMESTAMP):
                return "TIMESTAMP(0)";
            case (Types.BIGINT):
                if (DataTypeEnum.GREENPLUM == dbType) {
                    return "int8";
                } else {
                    return "BIGINT";
                }
            case (Types.LONGVARCHAR):
                if (DataTypeEnum.GREENPLUM == dbType) {
//                    return "varchar COLLATE";
                    return "varchar ";
                } else {
                    return "LONGVARCHAR";
                }
            case (Types.INTEGER):
                if (DataTypeEnum.GREENPLUM == dbType) {
                    return "int4";
                } else {
                    return "INTEGER";
                }
            case (Types.NUMERIC):
                return "numeric";
            default:
                return "VARCHAR";
        }
    }


    public static void batchCarFlowInsert(List<Map<String, Object>> list, JdbcTemplate targetTemp, String tableName, String[] columnNames) {
        /**
         * 远程数据库表
         */
        String sql = getInsertSql(columnNames, tableName);
        System.out.println(sql);
        List<Object[]> args = transformFlowCarReportDayBoToObjects(list, columnNames);
        targetTemp.update("TRUNCATE  " + tableName);
//        Object test=null;
//        try {
//            for (Object[] obj : args) {
//                System.out.println("******************************************************");
//                test=obj;
//                System.out.println(obj[0]);
//                targetTemp.update(sql, obj);
//            }
//        } catch (DataAccessException e) {
//            System.out.println(JSON.toJSONString(test));
//            e.printStackTrace();
//        }

        int fromIndex = 0;
        int toIndex = 100;
        while (fromIndex != args.size()) {
            if (toIndex > args.size()) {
                toIndex = args.size();
            }
            targetTemp.batchUpdate(sql, args.subList(fromIndex, toIndex));
            fromIndex = toIndex;
            toIndex += 100;
            if (toIndex > args.size())
                toIndex = args.size();
        }

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

    public static void main(String[] args) {

        String[] strings = {"case_id", "app_id", "are_id", "casetype", "subcasetype", "channel_type", "is_chg_credit", "status", "approve_status", "approve_rst", "reject_code", "rejrect_desc", "cust_group_code", "risk_level", "cust_level", "credit_rate", "credit_amount", "cust_number", "ecif_cust_no", "id_type", "ic_start_date", "id_no", "ic_end_date", "mobile", "nation", "education", "marriage", "gender", "career_category", "province", "city", "district", "address", "industry", "post_type", "job_title", "technical_title", "duty_status", "product_id", "net_check_result", "net_check_tips", "txn_flag", "is_tie_card", "reserve_mobile_no", "bank_name", "card_no", "live_postcode", "base_acct_no", "usage", "effective_date", "expiry_date", "create_time", "last_update_time", "approve_time", "now", "biz_date", "biz_mth", "biz_quarter", "create_hour", "biz_hour_group", "age", "age_group", "is_approved", "is_rejected", "credit_amount_group"};

        String jjjj = getInsertSql(strings, "testdb");
        System.out.println(jjjj);
    }

    private static List<Object[]> transformFlowCarReportDayBoToObjects(List<Map<String, Object>> flowCarReportDayBoList, String[] coloumNames) {

        List<Object[]> list = new ArrayList<>();

        List<Object> object = new ArrayList<>();
        for (Map<String, Object> data : flowCarReportDayBoList) {
            for (String name : coloumNames) {
                object.add(data.get(name));
            }
            list.add(object.toArray());
            object.clear();
        }
        return list;
    }


}
