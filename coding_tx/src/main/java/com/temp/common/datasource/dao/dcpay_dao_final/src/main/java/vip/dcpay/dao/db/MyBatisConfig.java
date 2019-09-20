package com.temp.common.datasource.dao.dcpay_dao_final.src.main.java.vip.dcpay.dao.db;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import vip.dcpay.util.frame.spring.PropertiesConfigurer;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author 朱光勋
 * @version V1.0
 * @date 2019/6/5 10:17
 */

@Slf4j
@EnableTransactionManagement(proxyTargetClass = true,order=10)
@DependsOn({"myRoutingDataSource","dbPropertiesConfigurer"})
@Order(2)
@Configuration
public class MyBatisConfig {

    @Resource(name="myRoutingDataSource")
    private DataSource myRoutingDataSource;

    @Resource(name = "dbPropertiesConfigurer")
    private PropertiesConfigurer dbPropertiesConfigurer;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(myRoutingDataSource);

        List<Interceptor> interceptorList = new ArrayList<>();
        if (dbPropertiesConfigurer.getBoolean("db.PaginationInterceptor", true)) {
            interceptorList.add(new PaginationInterceptor());// 分页插件配置
        }
        if (dbPropertiesConfigurer.getBoolean("db.OptimisticLockerInterceptor", false)) {
            interceptorList.add(new OptimisticLockerInterceptor());// 乐观锁配置
        }
            if (dbPropertiesConfigurer.getBoolean("db.PerformanceInterceptor", false)) {
            interceptorList.add(new PerformanceInterceptor());// 性能拦截器，兼打印sql，不建议生产环境配置
        }
        sqlSessionFactoryBean.setPlugins(interceptorList.toArray(new Interceptor[interceptorList.size()]));

        // 构造配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);// 部分数据库不识别默认的NULL类型（比如oracle，需要配置该属性
        configuration.setMapUnderscoreToCamelCase(dbPropertiesConfigurer.getBoolean("db.under_score_to_camel_case", false));
        //构造全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);//全局ID类型
        dbConfig.setCapitalMode(false);
        globalConfig.setDbConfig(dbConfig);

        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);
        String mapperRoot = dbPropertiesConfigurer.getProperty("mybatis.mapper.path");
        log.info("当前加载的mapperRoot:{}", mapperRoot);
        if (StringUtils.isNotBlank(mapperRoot)) {
            org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperRoot);
            for (org.springframework.core.io.Resource e : resources) {
                log.info("加载了mapper.xml:{}", e.getURI());
            }
            sqlSessionFactoryBean.setMapperLocations(resources);
        }

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(myRoutingDataSource);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }



}
