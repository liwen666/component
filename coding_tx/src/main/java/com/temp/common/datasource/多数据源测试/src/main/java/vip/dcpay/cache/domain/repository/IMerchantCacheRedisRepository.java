package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.domain.repository;

public interface IMerchantCacheRedisRepository {

    boolean lockMerchant(Long merchantId);

    long deleteMerchantLock();

}
