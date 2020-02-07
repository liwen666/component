package com.temp.common.base.JDBC.jrx.jrx_bi_demo.data_copy;

import com.temp.common.base.JDBC.jrx.JdbcTemplateUtils;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.DataBaseSyncUtils;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.DataTypeEnum;
import com.temp.common.base.JDBC.jrx.jrx_bi_demo.JdbcAssertUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import javax.xml.datatype.DatatypeConfigurationException;
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
public class DataCopyTest {

    /**
     * 获取信贷数据
     * 101.93数据拷贝
     */
    @Test
    public void copyCreditData() {
        JdbcAssertUtil.dataTypeEnum= DataTypeEnum.GREENPLUM;
        JdbcTemplate source = JdbcTemplateUtils.getJrxDemoDb();
        JdbcTemplate target = JdbcTemplateUtils.getJrxDb();
        String sourceSql = "SELECT *,\n" +
                "       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,\n" +
                "       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,\n" +
                "       credit_amount AS credit_amount_group\n" +
                "FROM md_cm_app_case_formatted limit 1 ";
        String targetTableName = "md_cm_app_case_formatted";
        /**
         * 创建表
         */
        try {
            DataBaseSyncUtils.createTable(source, target, sourceSql, targetTableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 复制数据
         */
        RowCountCallbackHandler rowCountCallbackHandler = new RowCountCallbackHandler();
        source.query(sourceSql, rowCountCallbackHandler);

        String dataSql = "SELECT *,\n" +
                "       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,\n" +
                "       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,\n" +
                "       credit_amount AS credit_amount_group\n" +
                "FROM md_cm_app_case_formatted limit 1000";
        List<Map<String, Object>> dataList = source.queryForList(dataSql);
        DataBaseSyncUtils.insertData(target, targetTableName, rowCountCallbackHandler.getColumnNames(), dataList);

    }


}
