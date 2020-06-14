package com.temp.common.common.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author sukang
 */
public class HandlerDataSource extends AbstractRoutingDataSource {


    private final static ThreadLocal<String> DATASOURCE_THREAD_LOCAL = new ThreadLocal<>();

    public HandlerDataSource(DataSource defaultTargetDataSource,
                             Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }



    public static Object getDataSource() {
        return DATASOURCE_THREAD_LOCAL.get();
    }

    public static void setDataSource(String dataSourceKey){
        DATASOURCE_THREAD_LOCAL.set(dataSourceKey);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public static void clearDataSource() {
        DATASOURCE_THREAD_LOCAL.remove();
    }
}
