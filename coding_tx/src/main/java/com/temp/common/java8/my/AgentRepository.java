//package com.temp.common.java8.my;
//
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import vip.dcpay.business.config.application.service.PerformanceRateService;
//import vip.dcpay.business.config.dto.PerformanceRateDto;
//import vip.dcpay.business.config.param.PerformanceRateParam;
//import vip.dcpay.business.order.dto.OrderRecordDto;
//import vip.dcpay.commission.domain.pojo.MerchantDayCommissionParam;
//import vip.dcpay.commission.domain.pojo.MerchantDayWaterParam;
//import vip.dcpay.commission.domain.pojo.MerchantRebatesConfigPojo;
//import vip.dcpay.commission.domain.repository.IAgentRepository;
//import vip.dcpay.commission.infrastructure.repository.dao.MerchantDayCommissionDao;
//import vip.dcpay.commission.infrastructure.repository.dao.MerchantDayWaterDao;
//import vip.dcpay.commission.infrastructure.repository.dao.MerchantRebatesConfigDao;
//import vip.dcpay.commission.infrastructure.repository.dao.OrderRecordDao;
//import vip.dcpay.commission.infrastructure.repository.model.MerchantDayCommission;
//import vip.dcpay.commission.infrastructure.repository.model.MerchantDayWater;
//import vip.dcpay.commission.infrastructure.repository.model.MerchantRebatesConfig;
//import vip.dcpay.commission.infrastructure.translator.AgentTranslator;
//import vip.dcpay.log.sdk.MyLogManager;
//import vip.dcpay.model.business.order.OrderRecord;
//import vip.dcpay.util.date.DateUtils;
//import vip.dcpay.vo.basic.Result;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//;
//
//@Repository
//public class AgentRepository implements IAgentRepository {
//
//    @Autowired
//    private MerchantRebatesConfigDao merchantRebatesConfigDao;
//
//    @Autowired
//    private MerchantDayCommissionDao merchantDayCommissionDao;
//
//
//    @Autowired
//    private MerchantDayWaterDao merchantDayWaterDao;
//
//    @Autowired(required = false)
//    private OrderRecordDao orderRecordDao;
//
//    @Reference
//    private PerformanceRateService performanceRateService;
//
//    @Override
//    public List<MerchantRebatesConfigPojo> getMerchantCommissionLadderList() {
//
//        List<MerchantRebatesConfig> configList = merchantRebatesConfigDao.selectList(new QueryWrapper<MerchantRebatesConfig>());
//        return JSON.parseArray(JSON.toJSONString(configList), MerchantRebatesConfigPojo.class);
//    }
//
//    @Override
//    @Transactional
//    public int insertMerchantDayCommissionBatch(List<MerchantDayWaterParam> waterParamList, List<MerchantDayCommissionParam> commissionList) {
//
//        for (MerchantDayWaterParam waterParam : waterParamList) {
//            MerchantDayWater merchantDayWater = AgentTranslator.translateFromModel(waterParam);
//            merchantDayWaterDao.insert(merchantDayWater);
//        }
//
//        int nCount = 0;
//        for (MerchantDayCommissionParam commissionParam : commissionList) {
//
//            MerchantDayCommission merchantDayCommission = AgentTranslator.translateFromModel(commissionParam);
//            merchantDayCommissionDao.insert(merchantDayCommission);
//            nCount++;
//        }
//        return nCount;
//    }
//
//    @Override
//    public int isMerchantDayCommissionExist(String date) {
//
//        return merchantDayCommissionDao.selectCount(new QueryWrapper<MerchantDayCommission>().lambda()
//                .eq(MerchantDayCommission::getCommissionDate, date));
//    }
//
//    @Override
//    @Transactional
//    public void deleteMerchantDayCommission(String date) {
//
//        merchantDayCommissionDao.delete(new QueryWrapper<MerchantDayCommission>().lambda()
//                .eq(MerchantDayCommission::getCommissionDate, date));
//
//        merchantDayWaterDao.delete(new QueryWrapper<MerchantDayWater>().lambda()
//                .eq(MerchantDayWater::getWaterDate, date));
//
//    }
//
//    /**
//     * 获取昨日已完成订单
//     */
//    @Override
//    public Result<List<OrderRecordDto>> listCompleteYesterdayOrder(Date date) {
//        Date lastMaxDay = DateUtils.getLastMaxDay(date);
//        Date lastMinDay = DateUtils.getLastMinDay(date);
//        List<OrderRecord> orderRecords = orderRecordDao.selectList(new QueryWrapper<OrderRecord>().lambda().eq(OrderRecord::getOrderStatus, 100).between(OrderRecord::getCreateTime, lastMinDay, lastMaxDay));
//        Map<String, Map<Integer, List<OrderRecord>>> collect = orderRecords.stream().collect(Collectors.groupingBy(OrderRecord::getRevcPaymentWay, Collectors.groupingBy(OrderRecord::getOrderType)));
//        Result<List<PerformanceRateDto>> list = performanceRateService.list(PerformanceRateParam.builder().status(1).build());
//        List<PerformanceRateDto> data = list.getData();
//        MyLogManager.develop("==================获取到的业绩配置信息是：PerformanceRateDto  " + data == null ? "null" : JSON.toJSONString(data));
//        //对数据进行过滤
//        List<OrderRecord> resultList = new ArrayList<>();
//        //判断订单是否开启佣金计算
//        for (Map.Entry me : collect.entrySet()) {
//            String payway = (String) me.getKey();
//            //判断支付方式
//            for (PerformanceRateDto p : data) {
//                if (p.getPayWay().equalsIgnoreCase(payway)) {
//                    Map<Integer, List<OrderRecord>> value = (Map<Integer, List<OrderRecord>>) me.getValue();
//                    //转移订单数据
//                    if(value.get(p.getOrderType())!=null){
//                        resultList.addAll(value.get(p.getOrderType()));
//                    }
//
//                }
//            }
//        }
//        List<OrderRecordDto> recordDtos = new ArrayList<>();
//        for (OrderRecord o : resultList) {
//            recordDtos.add(OrderRecordDto.builder().revcPaymentWay(o.getRevcPaymentWay())
//                    .realPaymentAmount(o.getRealPaymentAmount())
//                    .orderId(o.getOrderId())
//                    .orderType(o.getOrderType())
//                    .merchantId(o.getMerchantId())
//                    .build());
//        }
//        MyLogManager.info("===============订单时间：" + DateUtils.format(DateUtils.addDays(date,-1), DateUtils.YYYY_MM_DD_HH_MM_SS) + "=========获取到需要算佣金的订单数量：【size:" + recordDtos.size() + "订单Ids" + JSON.toJSONString(recordDtos.stream().map(e -> e.getOrderId()).collect(Collectors.toSet())));
//        return Result.success(recordDtos);
//    }
//
//    /**
//     * 获取指定商家指定时间符合返佣条件的订单
//     */
//    @Override
//    public Result<List<OrderRecordDto>> listCompleteOrderByMerchantIds(Set<Long> merchantIds, Date date) {
//        date = DateUtils.addDays(date, 1);
//        Date lastMaxDay = DateUtils.getLastMaxDay(date);
//        Date lastMinDay = DateUtils.getLastMinDay(date);
//        List<OrderRecord> orderRecords = orderRecordDao.selectList(new QueryWrapper<OrderRecord>().lambda().eq(OrderRecord::getOrderStatus, 100).in(OrderRecord::getMerchantId, merchantIds).between(OrderRecord::getCreateTime, lastMinDay, lastMaxDay));
//        Map<String, Map<Integer, List<OrderRecord>>> collect = orderRecords.stream().collect(Collectors.groupingBy(OrderRecord::getRevcPaymentWay, Collectors.groupingBy(OrderRecord::getOrderType)));
//        Result<List<PerformanceRateDto>> list = performanceRateService.list(PerformanceRateParam.builder().status(1).build());
//        List<PerformanceRateDto> data = list.getData();
//        MyLogManager.develop("==================获取到的业绩配置信息是：PerformanceRateDto  "+ JSON.toJSONString(data));
//        //对数据进行过滤
//        List<OrderRecord> resultList = new ArrayList<>();
//        //判断订单是否开启佣金计算
//        for (Map.Entry me : collect.entrySet()) {
//            String payway = (String) me.getKey();
//            //判断支付方式
//            for (PerformanceRateDto p : data) {
//                if (p.getPayWay().equalsIgnoreCase(payway)) {
//                    Map<Integer, List<OrderRecord>> value = (Map<Integer, List<OrderRecord>>) me.getValue();
//                    //转移订单数据
//                    if(value.get(p.getOrderType())!=null){
//                        resultList.addAll(value.get(p.getOrderType()));
//                    }
//
//                }
//            }
//        }
//        List<OrderRecordDto> recordDtos = new ArrayList<>();
//        for (OrderRecord o : resultList) {
//            recordDtos.add(OrderRecordDto.builder()
//                    .revcPaymentWay(o.getRevcPaymentWay())
//                    .realPaymentAmount(o.getRealPaymentAmount())
//                    .orderId(o.getOrderId())
//                    .orderType(o.getOrderType())
//                    .merchantId(o.getMerchantId()).build());
//        }
//        MyLogManager.develop("===============商家id" + JSON.toJSONString(merchantIds) + "订单时间：" + DateUtils.format(DateUtils.addDays(date,-1), DateUtils.YYYY_MM_DD_HH_MM_SS) + "=========获取到需要算佣金的订单数量：【size:" + recordDtos.size() + "----->订单Ids：" + JSON.toJSONString(recordDtos.stream().map(e -> e.getOrderId()).collect(Collectors.toSet())));
//        return Result.success(recordDtos);
//    }
//}
