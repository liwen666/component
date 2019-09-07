package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.config.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import vip.dcpay.util.frame.spring.PropertiesConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Configuration
public class DcpayJdbcConfig {

	@Bean
	public PropertiesConfigurer dcpaydbPropertiesConfigurer() {
		PropertiesConfigurer prop = new PropertiesConfigurer();
		prop.setIgnoreUnresolvablePlaceholders(true);
		prop.setIgnoreResourceNotFound(true);
		prop.setLocations(new ClassPathResource("jdbc_dcpay.properties"));
		return prop;
	}

	@Bean("dcpaydataSource")
	public DruidDataSource dataSource() {
		DruidDataSource d = new DruidDataSource();

		PropertiesConfigurer prop = dcpaydbPropertiesConfigurer();

		String url = prop.getProperty("db.url");
		String username = prop.getProperty("db.username");
		String password = prop.getProperty("db.password");
		int maxActive = prop.getInteger("db.maxActive", 20);
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
			if (null != arr && arr.length > 0) {
				for (String s : arr) {
					String[] pair = s.split("=");
					if (null != pair && pair.length > 0) {
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

		d.setUrl(url);
		d.setUsername(username);
		d.setPassword(password);
//		d.setDriverClassName("com.mysql.jdbc.Driver");
		d.setDriverClassName("com.mysql.jdbc.Driver");
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

	@Bean("dcpayJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("dcpaydataSource") DataSource dataSource){
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
	}

}
