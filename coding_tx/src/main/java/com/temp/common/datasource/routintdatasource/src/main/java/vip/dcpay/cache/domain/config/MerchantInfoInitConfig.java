package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import vip.dcpay.cache.domain.mgr.ServiceManager;

/**
 * author lw
 * date 2019/8/20  14:13
 * discribe 商户信息初始化
 */
@Component
public class MerchantInfoInitConfig implements ApplicationRunner {
    @Autowired
    private ServiceManager serviceManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //初始化缓存数据
        serviceManager.initMerchant();
    }
}
