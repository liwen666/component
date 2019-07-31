package vip.dcpay.commission.domain.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.springboot.ClientApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import vip.dcpay.alert.sdk.enums.AlertLevelEnum;
import vip.dcpay.business.config.application.service.PerformanceRateService;
import vip.dcpay.business.config.dto.PerformanceRateDto;
import vip.dcpay.business.config.param.PerformanceRateParam;
import vip.dcpay.business.order.application.service.OrderSearchService;
import vip.dcpay.business.order.dto.OrderRecordDto;
import vip.dcpay.commission.RunApplication;
import vip.dcpay.commission.util.AlertUtil;
import vip.dcpay.dto.order.OrderPaymentDto;
import vip.dcpay.enums.order.OrderTypeEnum;
import vip.dcpay.merchant.application.dto.MerchantDayTradeDto;
import vip.dcpay.vo.basic.Result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClientApplication.class)
//@Log
public class CommissionManagerTest {
    @Reference
    private PerformanceRateService performanceRateService;
    @Reference
    private OrderSearchService orderSearchService;
    @Test
    public void getPerformanceConfig() {

        PerformanceRateParam build = PerformanceRateParam.builder().status(1).build();
        System.out.println(JSON.toJSONString(build));
        Result<List<PerformanceRateDto>> list = performanceRateService.list(PerformanceRateParam.builder().status(1).build());

        System.out.println("Result<List<PerformanceRateDto>> list   --->"+JSON.toJSONString(list));
        MerchantDayTradeDto aliPay = MerchantDayTradeDto.builder().merchantId(25L).date("2019-07-19").orderType(1).payWay("AliPay").dayAmout(new BigDecimal(2129.0000000000)).build();
        BigDecimal performance = getPreformanceRate(aliPay, list.getData());
        Assert.state(null!=performance,"============performanceRateService.未找到该订单业绩的计算方式 订单流水 dayTradeRecord ");
        System.out.println(performance+"=======================================");


    }

    @Test
    public void getMechantDto() {
        Result<List<OrderRecordDto>> orderAmountInOneDay = orderSearchService.statMerchantOrderAmountInOneDay(new Date(), OrderTypeEnum.getEnum(1));
        System.out.println(JSON.toJSONString(orderAmountInOneDay));
        Result<List<OrderRecordDto>> orderAmountInOneDay1 = orderSearchService.statSomeMerchantOrderAmountInOneDay(new ArrayList<>(), new Date(), OrderTypeEnum.MERCHANT_DEPOSIT);
        System.out.println(JSON.toJSONString(orderAmountInOneDay1));
    }

    public BigDecimal getPreformanceRate(MerchantDayTradeDto dayTradeRecord, List<PerformanceRateDto> performanceRateList) {
        Assert.state(null!=performanceRateList," 计算费率传入的performanceRateList 为空");
        BigDecimal dayAmout = dayTradeRecord.getDayAmout();
        for(PerformanceRateDto performanceRateDto:performanceRateList){
            if(performanceRateDto.getOrderType()==dayTradeRecord.getOrderType()
                    &&performanceRateDto.getPayWay().equalsIgnoreCase(dayTradeRecord.getPayWay())){
                return performanceRateDto.getPerformance(dayAmout);
            }
        }
        return null;
    }
}


