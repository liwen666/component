package com.temp.common.datasource.dao.dcpay_dao_final.src.main.java.vip.dcpay.dao.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * @author 朱光勋
 * @version V1.0
 * @date 2019/6/5 10:10
 */


public class MyRoutingDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getDB();
    }
}
