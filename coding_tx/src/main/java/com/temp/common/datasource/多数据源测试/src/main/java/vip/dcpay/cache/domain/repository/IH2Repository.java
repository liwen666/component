package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.domain.repository;

import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;

import java.util.List;

public interface IH2Repository {
    int insertMerchantInfo(MerchantInfoCache build);

    int updateMerchantCacheInfo(MerchantInfoCache merchantInfoCache);

    int deleteMerchantCacheList();

    MerchantInfoCache getMerchant(Long merchantId);

    int deleteMerchantById(Long merchantId);

    List<MerchantInfoCache> listMerchantCache();
}
