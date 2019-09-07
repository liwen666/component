package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vip.dcpay.cache.domain.repository.IMerchantCacheRedisRepository;
import vip.dcpay.redis.service.RedisService;

@Repository
public class MerchantCacheRedisRepository implements IMerchantCacheRedisRepository {


    @Autowired
    private RedisService redisService;


    @Override
    public boolean lockMerchant(Long merchantId) {
        long h = redisService.hsetnx("h:cache:merchant_info", merchantId.toString(), "");
        return 0 == h ? false : true;
    }

    @Override
    public long deleteMerchantLock() {
       return redisService.delete("h:cache:merchant_info");

    }
}
