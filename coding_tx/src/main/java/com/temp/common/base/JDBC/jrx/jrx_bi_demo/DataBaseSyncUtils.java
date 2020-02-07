package com.temp.common.base.JDBC.jrx.jrx_bi_demo;

import com.temp.common.base.JDBC.jrx.JdbcTemplateUtils;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import java.util.List;
import java.util.Map;

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

        insertData(target,targetTableName,rowCountCallbackHandler.getColumnNames(),dataList);



    }

    public static void createTable(JdbcTemplate soucce, JdbcTemplate target, String sourceSql, String targetTableName) {
        String createTb = JdbcAssertUtil.getCreateTableSql(soucce, sourceSql, targetTableName);
        target.update(createTb);
    }

    public static void insertData(JdbcTemplate target, String tableName, String[] coloumName, List<Map<String, Object>> dataList) {
        JdbcAssertUtil.batchCarFlowInsert(dataList, target, tableName, coloumName);
    }

}
