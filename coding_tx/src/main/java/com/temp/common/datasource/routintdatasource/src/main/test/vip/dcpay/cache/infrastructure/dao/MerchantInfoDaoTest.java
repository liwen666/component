package com.temp.common.datasource.routintdatasource.src.main.test.vip.dcpay.cache.infrastructure.dao;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vip.dcpay.cache.RunApplication;
import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RunApplication.class)
public class MerchantInfoDaoTest {
    @Autowired(required = false)
    private MerchantInfoCacheDao merchantInfoDao;

    @Test
    public void insert() {
       merchantInfoDao.insert(MerchantInfoCache.builder().activateStatus(1)
               .id(1000000l)
               .uid("0000000").assets("{}")
               .areaCode("1")
               .areaName("jkfjlda").dayMountSum(new BigDecimal(100))
               .type(1).realname("test艰苦艰苦").build());
    }

    @Test
    public void query() {

        List<MerchantInfoCache> merchantInfoCaches = merchantInfoDao.selectList(null);
        System.out.println(JSON.toJSONString(merchantInfoCaches));
    }
    @Test
    public void delete() {
        int delete = merchantInfoDao.delete(Wrappers.lambdaQuery());
        System.out.println(delete);
    }

    @Test
    public void selectOne() {
        MerchantInfoCache merchantInfoCache = merchantInfoDao.selectById(1000000000);
    }
}