package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.repository;

public interface IMerchantCacheRedisRepository {

    boolean lockMerchant(Long merchantId);

    long deleteMerchantLock();

}
