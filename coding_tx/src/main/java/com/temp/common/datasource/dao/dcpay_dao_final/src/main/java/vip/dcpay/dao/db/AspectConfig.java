package com.temp.common.datasource.dao.dcpay_dao_final.src.main.java.vip.dcpay.dao.db;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import vip.dcpay.util.frame.spring.SpringContextUtil;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 朱光勋
 * @version V1.0
 * @Package vip.dcpay.backstage.application.config
 * @date 2019/6/5 10:25
 * @descrption DB切换配置
 */
@Slf4j
@Aspect
@Component
public class AspectConfig {

    @Before("@annotation(vip.dcpay.dao.db.Switch)")
    public void beforeDataSource(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Switch sw = method.getAnnotation(Switch.class);
        if (sw == null) {
            sw = joinPoint.getTarget().getClass().getAnnotation(Switch.class);
        }
        DBTypeEnum value = sw.value();
        Map<DBTypeEnum, DruidDataSource> datasources = (Map<DBTypeEnum, DruidDataSource>) SpringContextUtil.getBean("datasources");
        DruidDataSource druidDataSource = datasources.get(value);
        if (druidDataSource == null) {
            throw new RuntimeException("当前切换数据库未注册，请在db.properties文件中注册当前数据库");
        } else if (value.equals(DBTypeEnum.PLATFORM)) {
            DBContextHolder.platform(sw.master());
        } else if (value.equals(DBTypeEnum.MERCHANT)) {
            DBContextHolder.merchant(sw.master());
        } else if (value.equals(DBTypeEnum.ORDER)) {
            DBContextHolder.order(sw.master());
        } else if (value.equals(DBTypeEnum.BASE)) {
            DBContextHolder.base(sw.master());
        } else if (value.equals(DBTypeEnum.COMMON)) {
            DBContextHolder.common(sw.master());
        } else if (value.equals(DBTypeEnum.SCHEDULE)) {
            DBContextHolder.schedule(sw.master());
        } else if (value.equals(DBTypeEnum.H2)) {
            DBContextHolder.h2(sw.master());
        } else if (value.equals(DBTypeEnum.NEWPAY)) {
            DBContextHolder.h2(sw.master());
        }


    }

    @AfterReturning("@annotation(vip.dcpay.dao.db.Switch)")
    public void afterDataSource(JoinPoint joinPoint) {
        DBContextHolder.clearDB();
        log.info("方法正常结束 清空数据库");
    }

    @AfterThrowing("@annotation(vip.dcpay.dao.db.Switch)")
    public void afterThrowDatabase(JoinPoint joinPoint) {
        DBContextHolder.clearDB();
        log.info("方法异常结束 清空数据库");
    }

}
