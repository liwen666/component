package com.temp.common.datasource.dao.dcpay_dao_final.src.main.java.vip.dcpay.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import vip.dcpay.dao.db.AspectConfig;
import vip.dcpay.dao.db.DBTypeEnum;
import vip.dcpay.dao.db.MyBatisConfig;
import vip.dcpay.dao.db.MyRoutingDataSource;
import vip.dcpay.util.frame.spring.PropertiesConfigurer;

import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Order(1)
@Configuration
@Import({MyBatisConfig.class, AspectConfig.class})
public class DaoConfig {


    @Bean("datasources")
    public Map<DBTypeEnum, DruidDataSource> datasources() {
        PropertiesConfigurer prop = dbPropertiesConfigurer();
        return loadDataSource(prop);
    }

    @Bean(name = "myRoutingDataSource")
    public DataSource myRoutingDataSource(@Qualifier("datasources") Map<DBTypeEnum, DruidDataSource> datasources
    ) {
        if (CollectionUtils.isEmpty(datasources)) {
            log.error("请指定数据源，在jdbc.properties配置文件中，增加db.datasource配置项");
            throw new RuntimeException("请指定数据源，在jdbc.properties配置文件中，增加db.datasource配置项");
        }
        PropertiesConfigurer prop = dbPropertiesConfigurer();
        String default_database = prop.getProperty("db.default.database");
        if (default_database == null || DBTypeEnum.getEnum(default_database) == null) {
            log.error("配置文件jdbc.properties中,必选项【db.default.database】不可是空 ");
            throw new RuntimeException("请在jdbc.properties配置文件中，通过db.default.database来指定默认数据库");
        }
        Map<Object, Object> targetDataSources = new HashMap<>(datasources);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(datasources.get(DBTypeEnum.getEnum(default_database)));
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }


    @Bean
    public PropertiesConfigurer dbPropertiesConfigurer() {
        PropertiesConfigurer prop = new PropertiesConfigurer();
        prop.setIgnoreUnresolvablePlaceholders(true);
        prop.setIgnoreResourceNotFound(true);
        prop.setLocations(new ClassPathResource("jdbc.properties"));
        return prop;
    }

    private static DruidDataSource initDataSource(String url, String username, String password, String driver, PropertiesConfigurer prop) {
        int maxActive = prop.getInteger("db.maxActive", 1000);
        int initialSize = prop.getInteger("db.initialSize", 1);
        int minIdle = prop.getInteger("db.minIdle", 1);
        long maxWaitMillis = prop.getLong("db.maxWaitMillis", 60000L);
        int timeBetweenEvictionRunsMillis = prop.getInteger("db.timeBetweenEvictionRunsMillis", 60000);
        int minEvictableIdleTimeMillis = prop.getInteger("db.minEvictableIdleTimeMillis", 300000);
        boolean testWhileIdle = prop.getBoolean("db.testWhileIdle", true);
        boolean testOnBorrow = prop.getBoolean("db.testOnBorrow", false);
        boolean testOnReturn = prop.getBoolean("db.testOnReturn", false);
        boolean poolPreparedStatements = prop.getBoolean("db.poolPreparedStatements", true);
        int maxOpenPreparedStatements = prop.getInteger("db.maxOpenPreparedStatements", 20);
        boolean asyncInit = prop.getBoolean("db.asyncInit", true);
        String filters = prop.getProperty("db.filters", "slf4j");
        String connectionProperties = prop.getProperty("db.connectionProperties");
        Properties p = null;
        if (StringUtils.isNoneBlank(connectionProperties)) {
            String[] arr = connectionProperties.split(";");
            if (arr.length > 0) {
                for (String s : arr) {
                    String[] pair = s.split("=");
                    if (pair.length > 0) {
                        if (null == p) {
                            p = new Properties();
                        }
                        p.put(pair[0], pair[1]);
                    }
                }

            }
        }
        if (StringUtils.isAnyBlank(url, username, password)) {
            log.error("配置文件jdbc.properties中,必选项【db.url | db.username | db.password】不可是空 ");
            throw new NullPointerException("配置项非法");
        }
        DruidDataSource d = new DruidDataSource();
        d.setUrl(url);
        d.setUsername(username);
        d.setPassword(password);
        d.setDriverClassName(driver);
        d.setInitialSize(initialSize);
        d.setMaxActive(maxActive);
        d.setMinIdle(minIdle);
        d.setMaxWait(maxWaitMillis);
        d.setPoolPreparedStatements(poolPreparedStatements);
        d.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        d.setValidationQuery("SELECT 1");
        d.setTestOnBorrow(testOnBorrow);
        d.setTestOnReturn(testOnReturn);
        d.setTestWhileIdle(testWhileIdle);
        d.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        d.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        d.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        d.setAsyncInit(asyncInit);
        if (null != p) {
            d.setConnectProperties(p);
        }

        try {
            d.addFilters(filters);
        } catch (SQLException e) {
            log.error("配置文件jdbc.properties中,必选项【db.filters】设置异常", e);
        }
        return d;
    }

    private static Map<String, String> getJDBC(String filePath) {
        Map<String, String> map = new HashMap<>();
        InputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            ResourceBundle resourceBundle = new PropertyResourceBundle(in);
            Set<String> keys = resourceBundle.keySet();
            for (String key : keys) {
                map.put(key, (String) resourceBundle.getObject(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("加载db.properties配置文件出错");
            throw new RuntimeException("加载db.properties配置文件出错");
        }
        return map;
    }

    private static Map<DBTypeEnum, DruidDataSource> loadDataSource(PropertiesConfigurer prop){
        String filePath = prop.getProperty("db.filePath");
        Set<String> dataKey = new HashSet<>(Arrays.asList(prop.getProperty("db.database").split(",")));
        Map<String, String> jdbc = getJDBC(filePath);
        Map<String,String> dataBaseUtil = new HashMap<>();
        jdbc.forEach((e,v)->{
            if(dataKey.contains(e.toLowerCase())){
                dataBaseUtil.put(e,v);
            }
        });
        Map<DBTypeEnum, DruidDataSource> datasource = new ConcurrentHashMap<>();
        dataBaseUtil.keySet().stream().filter(e -> DBTypeEnum.getEnum(e) != null).forEach(e -> {
            String s = jdbc.get(e);
            String[] split = s.split(";");
            if (split.length < 2) {
                DBTypeEnum name = DBTypeEnum.getEnum(e);
                splitDataSource(name,s,prop,datasource);
            } else {
                for (int i = 0; i < split.length; i++) {
                    String cur = split[i];
                    if("".equals(cur)){
                        continue;
                    }
                    DBTypeEnum name ;
                    if (i != 0) {
                        name = DBTypeEnum.getEnum(e+"_SLAVE");
                    } else {
                        name = DBTypeEnum.getEnum(e);
                    }
                    splitDataSource(name,cur,prop,datasource);
                }
            }
        });
        return datasource;
    }

    private static void splitDataSource(DBTypeEnum name, String key, PropertiesConfigurer prop, Map<DBTypeEnum, DruidDataSource> datasource){
        String username= "";
        String password= "";
        String driver = "";
        String url = "";
        url = key.substring(0, key.indexOf("&username")).trim();
        if(key.indexOf("username") != -1) {
            username = key.substring(key.indexOf("username") - 1, key.indexOf("password") - 1).split("=")[1].trim();
        }
        if(key.indexOf("password") != -1) {
            password = key.substring(key.indexOf("password") - 1, key.indexOf("driver") - 1).split("=")[1].trim();
        }
        if(key.indexOf("driver") != -1) {
            driver = key.substring(key.indexOf("driver") - 1).split("=")[1].trim();
        }
        datasource.put(name, initDataSource(url, username, password,driver, prop));
    }
}