package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.mgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vip.dcpay.cache.domain.constant.MerchantCacheConstant;
import vip.dcpay.cache.domain.service.RemoteService;
import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.util.frame.spring.SpringContextUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class ServiceManager {
    @Autowired
    private RemoteService remoteService;
    // Spring应用上下文环境
    public synchronized long initMerchant() {
        //初始化表结构
        MerchantCacheConstant.MERCHANT_INIT_LOCK=1;
        initTalble();
        long startTime = System.currentTimeMillis();
        MyLogManager.info("============================================初始化商户信息=============================================");
        long merchantCount = remoteService.initMerchant();
        MyLogManager.info("初始化获取商户数量：" + merchantCount + " 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        MerchantCacheConstant.MERCHANT_INIT_LOCK=0;
        return merchantCount;
    }

    public void initTalble() {
        Connection conn = null;
        Statement stmt = null;
        try {
            DataSource dataSource = (DataSource) SpringContextUtil.getBean("myRoutingDataSource");
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
//            stmt.execute("DROP TABLE IF EXISTS merchant_info_cache");
            stmt.execute("  CREATE TABLE if not exists  `merchant_info_cache` (\n" +
                    "        `id` bigint(32) not null ,\n" +
                    "        `uid` varchar (32) DEFAULT NULL,\n" +
                    "        `type` int(10) DEFAULT NULL,\n" +
                    "        `grab` int(1) DEFAULT NULL,\n" +
                    "        `player_deposit` int(1) DEFAULT NULL,\n" +
                    "        `platform_withdraw` int(1) DEFAULT NULL,\n" +
                    "        `merchant_deposit` int(1) DEFAULT NULL,\n" +
                    "        `merchant_withdraw` int(1) DEFAULT NULL,\n" +
                    "        `realname` varchar(255) DEFAULT NULL,\n" +
                    "        `activate_status` int(10) DEFAULT NULL,\n" +
//                    "        `payments` CLOB (6000) DEFAULT NULL,\n" +
//                    "        `payment_choices` CLOB (6000) DEFAULT NULL,\n" +
//                    "        `assets` CLOB(6000) DEFAULT NULL,\n" +
                    "        `payments` varchar (1000) DEFAULT NULL,\n" +
                    "        `payment_choices` varchar (1000) DEFAULT NULL,\n" +
                    "        `assets` varchar(1000) DEFAULT NULL,\n" +
                    "        `area_code` varchar(32) DEFAULT NULL,\n" +
                    "        `area_name` varchar(255) DEFAULT NULL,\n" +
                    "        `day_order_count` bigint(32) DEFAULT NULL,\n" +
                    "        `day_mount_sum` decimal(20,10) DEFAULT NULL,\n" +
                    "         PRIMARY KEY (`id`) \n" +
                    ")");

            //初始化顶顶那缓存表
            stmt.execute(" CREATE TABLE if not exists  `dispatch_order_info` (\n" +
                    "                          `id` bigint(32)  auto_increment ,\n" +
                    "                           `invoice` varchar(32) not NULL,\n" +
                    "                            `order_type` int(2) not NULL,\n" +
                    "                            `merchant_group_id` int(1) not NULL,\n" +
                    "                           `create_time` datetime DEFAULT NULL,\n" +
                    "                            PRIMARY KEY (`id`)\n" +
                    "                    );");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int updateMerchantById(long id) {
        int result = remoteService.updateMerchant(id);
        return result;
    }

    public List<MerchantInfoCache> queryMerchantList() {
        return remoteService.listMerchantCache();
    }
}
