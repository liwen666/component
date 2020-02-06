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
public class BiDataDemo {

    /**
     * 获取信贷数据
     */
    @Test
    public void getCreditData() {
        JdbcTemplate jrxDemoDb = JdbcTemplateUtils.getJrxDemoDb();
        List<Map<String, Object>> maps = jrxDemoDb.queryForList("SELECT * ," +
                "       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved ," +
                "       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected, " +
                "       credit_amount AS credit_amount_group " +
                "FROM md_cm_app_case_formatted  limit 10000");

    }

    @Test
    public void createTable() {
        JdbcTemplate jrxDemoDb = JdbcTemplateUtils.getJrxDemoDb();
        JdbcTemplate localDb = JdbcTemplateUtils.getLocalDb();

        RowCountCallbackHandler rowCountCallbackHandler = new RowCountCallbackHandler();
                String sql = "SELECT *,\n" +
                        "       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,\n" +
                        "       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,\n" +
                        "       credit_amount AS credit_amount_group\n" +
                        "FROM md_cm_app_case_formatted limit 1 ";
//        String createTb = JdbcAssertUtil.getCreateTableSql(jrxDemoDb, sql, "md_cm_app_case_formatted");
        String createTb = JdbcAssertUtil.getCreateTableSql(jrxDemoDb, sql, "md_cm_app_case_formatted");
        localDb.update(createTb);


        System.out.println(createTb);
    }

    @Test
    public void insertData() {
        String sql = "SELECT *,\n" +
                "       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,\n" +
                "       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,\n" +
                "       credit_amount AS credit_amount_group\n" +
                "FROM md_cm_app_case_formatted limit 10";
        JdbcTemplate jrxDemoDb = JdbcTemplateUtils.getJrxDemoDb();
        JdbcTemplate localDb = JdbcTemplateUtils.getLocalDb();
        RowCountCallbackHandler rowCountCallbackHandler = new RowCountCallbackHandler();
        jrxDemoDb.query(sql, rowCountCallbackHandler);
        List<Map<String, Object>> maps = jrxDemoDb.queryForList(sql);
        JdbcAssertUtil.batchCarFlowInsert(maps,jrxDemoDb,localDb,"md_cm_app_case_formatted",rowCountCallbackHandler.getColumnNames());


    }




}
