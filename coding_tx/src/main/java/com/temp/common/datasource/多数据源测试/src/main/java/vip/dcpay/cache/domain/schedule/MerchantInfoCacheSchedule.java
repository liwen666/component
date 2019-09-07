package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.domain.schedule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.dcpay.cache.domain.mgr.ServiceManager;
import vip.dcpay.log.sdk.MyLogManager;

/**
 * 每日定时刷新缓存
 */
@Component
public class MerchantInfoCacheSchedule {
    @Autowired(required = false)
    private ServiceManager serviceManager;

    @Scheduled(cron = "0 0 0 * * ?") //每日零点执行一次
    public void flushMerchantInfoCache() {
        MyLogManager.info("====每日0点刷新商户缓存，定时任务启动！");
        serviceManager.initMerchant();
    }
    @Scheduled(cron = "0 0 1 * * ?") //每日凌晨1点执行一次
    public void flushMerchantInfoCache2() {
        MyLogManager.info("====每日1点刷新商户缓存，定时任务启动！");
        serviceManager.initMerchant();
    }


}
