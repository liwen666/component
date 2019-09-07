package com.temp.common.datasource.routintdatasource.src.main.test.vip.dcpay.cache.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vip.dcpay.cache.RunApplication;
import vip.dcpay.cache.infrastructure.dao.MerchantInfoCacheDao;
import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;
import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RunApplication.class)
public class AgentRepositoryTest {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private MerchantInfoCacheDao merchantInfoCacheDao;



    @Test
    public void listAsset() {
        List<ExDigitalMoneyAsset> exDigitalMoneyAssets = agentRepository.listMerchantAssets(10l, 2);
        System.out.println(JSON.toJSONString(exDigitalMoneyAssets));

    }
    @Test
    public void insertCache() {
        List<ExDigitalMoneyAsset> exDigitalMoneyAssets = agentRepository.listMerchantAssets(10l, 2);
        System.out.println(JSON.toJSONString(exDigitalMoneyAssets));
        merchantInfoCacheDao.insert(MerchantInfoCache.builder().id(10101001l).uid("10000").realname("test").build());
    }

    @Test
    public void testMaper() {
        List<ExDigitalMoneyAsset> exDigitalMoneyAssets = agentRepository.listMerchantAssetsByMapper();
        System.out.println(JSON.toJSONString(exDigitalMoneyAssets));

    }
}