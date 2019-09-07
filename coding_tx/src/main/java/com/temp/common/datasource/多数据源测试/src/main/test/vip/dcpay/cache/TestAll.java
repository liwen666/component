package com.temp.common.datasource.多数据源测试.src.main.test.vip.dcpay.cache;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import vip.dcpay.cache.infrastructure.model.MerchantInfoCache;

/**
 * author lw
 * date 2019/8/31  11:08
 * discribe
 */
public class TestAll
{
    @Test
    public void isAllFieldNull() {
        MerchantInfoCache merchantInfoCache = new MerchantInfoCache();
        System.out.println(JSON.toJSONString(merchantInfoCache));
    }

}
