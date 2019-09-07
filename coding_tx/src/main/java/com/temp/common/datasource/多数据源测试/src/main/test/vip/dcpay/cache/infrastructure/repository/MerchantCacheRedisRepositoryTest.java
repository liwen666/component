package com.temp.common.datasource.多数据源测试.src.main.test.vip.dcpay.cache.infrastructure.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vip.dcpay.cache.RunApplication;
import vip.dcpay.cache.domain.repository.IMerchantCacheRedisRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RunApplication.class)
public class MerchantCacheRedisRepositoryTest {
    @Autowired
    private IMerchantCacheRedisRepository merchantCacheRedisRepository;

    @Test
    public void lockMerchant() {
        Long merchantId = 1l;

        boolean isLock = merchantCacheRedisRepository.lockMerchant( merchantId);
    }
}