package com.temp.common.datasource.多数据源测试.src.main.test.vip.dcpay.cache.infrastructure.dao;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vip.dcpay.cache.RunApplication;
import vip.dcpay.merchant.application.dto.FetcherDto;
import vip.dcpay.merchant.application.inner.MerchantInnerService;
import vip.dcpay.merchant.application.param.business.FetcherFilterCondition;
import vip.dcpay.vo.basic.Result;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RunApplication.class)
public class ScheduleServiceTest {
    @Reference
    MerchantInnerService merchantInnerService ;

    @Test
    public void getMerchant() {
        String data ="{\"currency\":\"CNY\",\"maxBalance\":1000000000,\"maxLoseLawRate\":1,\"maxTransQuantityInWeek\":1000000000,\"maxTurnoverInToday\":1000000000,\"merchantType\":3,\"minBalance\":0,\"minLoseLawRate\":0,\"minTransQuantityInWeek\":0,\"minTurnoverInToday\":0,\"orderType\":2}";
        FetcherFilterCondition fetcherFilterCondition = JSON.parseObject(data, FetcherFilterCondition.class);
        fetcherFilterCondition.setMerchantTypeList(new ArrayList<Integer>(){{add(1);add(2);add(3);}});
        Result<List<FetcherDto>> fetcher = merchantInnerService.findFetcher(fetcherFilterCondition);
        System.out.println(JSON.toJSONString(fetcher));

    }
}