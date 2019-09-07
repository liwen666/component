package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.domain.config;//package vip.dcpay.cache.domain.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
//import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.type.JdbcType;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import vip.dcpay.util.frame.spring.PropertiesConfigurer;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//@Slf4j
//@Configuration
//@MapperScan(basePackages="vip.dcpay.cache",markerInterface=BaseMapper.class)
//public class DaoConfig {
//
//	@Bean
//	public PropertiesConfigurer dbPropertiesConfigurer() {
//		PropertiesConfigurer prop = new PropertiesConfigurer();
//		prop.setIgnoreUnresolvablePlaceholders(true);
//		prop.setIgnoreResourceNotFound(true);
//		prop.setLocations(new ClassPathResource("jdbc.properties"));
//		return prop;
//	}
//
//	@Bean("dataSource")
//	public DruidDataSource dataSource() {
//		DruidDataSource d = new DruidDataSource();
//
//		PropertiesConfigurer prop = dbPropertiesConfigurer();
//
//		String url = prop.getProperty("db.url");
//		String username = prop.getProperty("db.username");
//		String password = prop.getProperty("db.password");
//		int maxActive = prop.getInteger("db.maxActive", 20);
//		int initialSize = prop.getInteger("db.initialSize", 1);
//		int minIdle = prop.getInteger("db.minIdle", 1);
//		long maxWaitMillis = prop.getLong("db.maxWaitMillis", 60000L);
//		int timeBetweenEvictionRunsMillis = prop.getInteger("db.timeBetweenEvictionRunsMillis", 60000);
//		int minEvictableIdleTimeMillis = prop.getInteger("db.minEvictableIdleTimeMillis", 300000);
//		boolean testWhileIdle = prop.getBoolean("db.testWhileIdle", true);
//		boolean testOnBorrow = prop.getBoolean("db.testOnBorrow", false);
//		boolean testOnReturn = prop.getBoolean("db.testOnReturn", false);
//		boolean poolPreparedStatements = prop.getBoolean("db.poolPreparedStatements", true);
//		int maxOpenPreparedStatements = prop.getInteger("db.maxOpenPreparedStatements", 20);
//		boolean asyncInit = prop.getBoolean("db.asyncInit", true);
//		String filters = prop.getProperty("db.filters", "slf4j");
//		String connectionProperties = prop.getProperty("db.connectionProperties");
//
//		Properties p = null;
//		if (StringUtils.isNoneBlank(connectionProperties)) {
//			String[] arr = connectionProperties.split(";");
//			if (null != arr && arr.length > 0) {
//				for (String s : arr) {
//					String[] pair = s.split("=");
//					if (null != pair && pair.length > 0) {
//						if (null == p) {
//							p = new Properties();
//						}
//						p.put(pair[0], pair[1]);
//					}
//				}
//
//			}
//		}
//
//		if (StringUtils.isAnyBlank(url, username, password)) {
//			log.error("配置文件jdbc.properties中,必选项【db.url | db.username | db.password】不可是空 ");
//			throw new NullPointerException("配置项非法");
//		}
//
//		d.setUrl(url);
//		d.setUsername(username);
//		d.setPassword(password);
//		d.setDriverClassName("org.h2.Driver");
//		d.setInitialSize(initialSize);
//		d.setMaxActive(maxActive);
//		d.setMinIdle(minIdle);
//		d.setMaxWait(maxWaitMillis);
//		d.setPoolPreparedStatements(poolPreparedStatements);
//		d.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
//		d.setValidationQuery("SELECT 1");
//		d.setTestOnBorrow(testOnBorrow);
//		d.setTestOnReturn(testOnReturn);
//		d.setTestWhileIdle(testWhileIdle);
//		d.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//		d.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//		d.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//		d.setAsyncInit(asyncInit);
//		if (null != p) {
//			d.setConnectProperties(p);
//		}
//
//		try {
//			d.addFilters(filters);
//		} catch (SQLException e) {
//			log.error("配置文件jdbc.properties中,必选项【db.filters】设置异常", e);
//		}
//		return d;
//
//	}
//
//	@Bean("mybatisSqlSession")
//	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		PropertiesConfigurer prop = dbPropertiesConfigurer();
//		// 构造工厂
//		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//		sqlSessionFactory.setDataSource(dataSource);
//		sqlSessionFactory.setTypeAliasesPackage("vip.dcpay.model");// 配置实体扫描路径，多个package可以用“分号;”或“逗号,” 分隔， 支持通配符*
//
//		// 插件配置
//
//		List<Interceptor> interceptorList = new ArrayList<>();
//		if (prop.getBoolean("db.PaginationInterceptor", true)) {
//			interceptorList.add(new PaginationInterceptor());// 分页插件配置
//		}
//		if (prop.getBoolean("db.OptimisticLockerInterceptor", false)) {
//			interceptorList.add(new OptimisticLockerInterceptor());// 乐观锁配置
//		}
//		if (prop.getBoolean("db.PerformanceInterceptor", false)) {
//			interceptorList.add(new PerformanceInterceptor());// 性能拦截器，兼打印sql，不建议生产环境配置
//		}
//		sqlSessionFactory.setPlugins(interceptorList.toArray(new Interceptor[interceptorList.size()]));
//
//		// 构造配置
//		MybatisConfiguration configuration = new MybatisConfiguration();
//		configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
//		configuration.setJdbcTypeForNull(JdbcType.NULL);// 部分数据库不识别默认的NULL类型（比如oracle，需要配置该属性
//		configuration.setMapUnderscoreToCamelCase(prop.getBoolean("db.under_score_to_camel_case", false));
//		// 是否自动将驼峰属性转化成下划线列名。true转化
//
//		// 构造全局配置
//		GlobalConfig globalConfig = new GlobalConfig();
//		DbConfig dbConfig = new DbConfig();
//		dbConfig.setIdType(IdType.AUTO);// 全局ID类型
//		dbConfig.setCapitalMode(false);
//		globalConfig.setDbConfig(dbConfig);
//
//		sqlSessionFactory.setConfiguration(configuration);
//		sqlSessionFactory.setGlobalConfig(globalConfig);
//
//		String mapperRoot = prop.getProperty("mybatis.mapper.path");
//		log.info("当前加载的mapperRoot:{}", mapperRoot);
//		if (StringUtils.isNotBlank(mapperRoot)) {
//			Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperRoot);
//			for (Resource e : resources) {
//				log.info("加载了mapper.xml:{}", e.getURI());
//			}
//			sqlSessionFactory.setMapperLocations(resources);
//		}
//		return sqlSessionFactory.getObject();
//	}
//
//
//}
