package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import vip.dcpay.alert.sdk.MyAlertManager;
import vip.dcpay.dao.DaoConfig;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.redis.RedisSentinelConfig;
import vip.dcpay.redis.RedisServiceManager;
import vip.dcpay.redis.service.RedisService;
import vip.dcpay.util.frame.spring.AnnotationBeanNameGeneratorRewrite;
import vip.dcpay.util.frame.spring.FrameUtilConfig;

@MapperScan(basePackages = {"vip.dcpay.dao","vip.dcpay.cache.infrastructure.dao"},basePackageClasses= BaseMapper.class)
@SpringBootApplication
@ComponentScan(nameGenerator = AnnotationBeanNameGeneratorRewrite.class)
@Import({
        FrameUtilConfig.class
        , DaoConfig.class
        ,MyLogManager.class
        ,MyAlertManager.class,
        RedisSentinelConfig.class
})

@EnableAspectJAutoProxy
@EnableScheduling
@EnableDubbo
public class RunApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(RunApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
    }
    @Bean("redisService")
    public RedisService redisService() {
        return RedisServiceManager.gainRedisService(1);
    }

}
