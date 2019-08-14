//package com.temp.common.mq.commission;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.alibaba.fastjson.JSON;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.Assert;
//import vip.dcpay.alert.sdk.enums.AlertLevelEnum;
//import vip.dcpay.business.config.dto.PerformanceRateDto;
//import vip.dcpay.business.config.param.PerformanceRateParam;
//import vip.dcpay.business.order.dto.OrderRecordDto;
//import vip.dcpay.commission.aop.annotation.CommissionLog;
//import vip.dcpay.commission.application.bean.MerchantRebates;
//import vip.dcpay.commission.application.service.MerchantCommissionService;
//import vip.dcpay.commission.domain.pojo.AgentTreeNode;
//import vip.dcpay.commission.domain.pojo.MerchantRebatesConfigPojo;
//import vip.dcpay.commission.domain.repository.IAgentRepository;
//import vip.dcpay.commission.domain.service.RemoteService;
//import vip.dcpay.commission.domain.util.TreeBuilder;
//import vip.dcpay.commission.util.AlertUtil;
//import vip.dcpay.log.sdk.MyLogManager;
//import vip.dcpay.merchant.application.dto.MerchantAgentDto;
//import vip.dcpay.merchant.application.dto.RecursiveAgentDto;
//import vip.dcpay.vo.basic.Result;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * @Auther: liq
// * @Date: 2019/7/9 15:25
// * @Description:
// */
//@Service
//public class MerchantCommissionServiceImpl implements MerchantCommissionService {
//    private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//    private final BigDecimal baseUnit = new BigDecimal(10000);
//    @Autowired
//    private RemoteService remoteService;
//    @Autowired
//    private IAgentRepository agentRepository;
//
//    public MerchantCommissionServiceImpl() {
//    }
//
//    /**
//     * 查询商家实时佣金
//     *
//     * @param merchantId
//     * @return
//     */
//    @CommissionLog(description = "实时佣金计算")
//    public Result<MerchantRebates> getOrderAmountStatistics(Long merchantId) {
//        long l = System.currentTimeMillis();
//        List<MerchantAgentDto> agentList1 = null;
//        List<AgentTreeNode> agentTreeNodes = null;
//        List<RecursiveAgentDto> list = remoteService.findChirdenAgentMap(Arrays.asList(merchantId));
//        agentList1 = list.get(0).getAgentList();
//        agentTreeNodes = new ArrayList();
//        agentTreeNodes.add(AgentTreeNode.builder().parentId(0L).selfId(merchantId).build());
//
//        for (MerchantAgentDto merchantAgentDto : agentList1) {
//            agentTreeNodes.add(AgentTreeNode.builder().selfId(merchantAgentDto.getMerchantId()).parentId(merchantAgentDto.getParentId()).build());
//        }
//        //构造商户代理树
//        List<AgentTreeNode> agentTreeNodes1 = TreeBuilder.bulid(agentTreeNodes);
//        if (null == agentTreeNodes1.get(0).getChildList()) {
//            MyLogManager.develop("============merchantAgentService 获取指定商户下级代理数据为空[商户 merchantId: " + merchantId);
//            return Result.success(MerchantRebates.builder().orderAmount(new BigDecimal(0)).rebates(new BigDecimal(0)).rebatesAmount(new BigDecimal(0)).build());
//        } else {
//            Set<Long> collect = agentList1.stream().map(MerchantAgentDto::getMerchantId).collect(Collectors.toSet());
//            //计算商家团队佣金
//            MyLogManager.develop("[实时佣金计算]商家 merchantId  [" + merchantId + "]  的下级代理：" + JSON.toJSONString(collect));
//                        /*************************************优化实时佣金计算***********************************************/
////                BigDecimal amountPerformances = this.getAmountPerformances(collect);
//            //商家交易订单
//            Map<Long, Map<String, BigDecimal>> merchantOrderMap = new HashMap<>();
//            BigDecimal amountPerformances = this.getAmountPerformancesFromMap(collect, merchantOrderMap, false);
//                      /****************************************************************************************************/
//            MyLogManager.develop("商家" + merchantId + "的团队业绩统计：" + JSON.toJSONString(amountPerformances));
//            MerchantRebates merchantRebates = this.getMerchantRebates(merchantId, amountPerformances);
//            //计算下级代理团队佣金
//            BigDecimal teamRebates = new BigDecimal(0);
//            List<AgentTreeNode> childList = agentTreeNodes1.get(0).getChildList();
//            for (AgentTreeNode agentTreeNode : childList) {
//                if (null != agentTreeNode.getChildList()) {
//                    List<AgentTreeNode> childList1 = agentTreeNode.getChildList();
//                    List<Long> result = new ArrayList();
//                    this.getTeamAgentList(result, childList1);
//                    MyLogManager.develop("=================商家" + agentTreeNode.getSelfId() + "存在的下级代理 有" + JSON.toJSONString(result));
//                    /*************************************优化实时佣金计算***********************************************/
////                    BigDecimal agentPerformances = this.getAmountPerformances(new HashSet(result));
//                    BigDecimal agentPerformances = this.getAmountPerformancesFromMap(new HashSet(result), merchantOrderMap, true);
//
//                    /****************************************************************************************************/
//
//                    MerchantRebates merchantRebates1 = this.getMerchantRebates(agentTreeNode.getSelfId(), agentPerformances);
//                    teamRebates = teamRebates.add(merchantRebates1.getRebatesAmount());
//                }
//            }
//            //商家团队佣金 - 商家下级代理团队佣金
//            merchantRebates.setRebatesAmount(merchantRebates.getRebatesAmount().subtract(teamRebates));
//            MyLogManager.develop("[实时佣金计算结果]  商家 merchantId [" + merchantId + "] 获取的佣金计算结果是 " + JSON.toJSONString(merchantRebates)+"  耗时： "+(System.currentTimeMillis()-l));
//
//            return Result.success(merchantRebates);
//        }
//    }
//
//    private void getTeamAgentList(List<Long> result, List<AgentTreeNode> childList1) {
//        Iterator<AgentTreeNode> iterator = childList1.iterator();
//        while (iterator.hasNext()) {
//            AgentTreeNode agentTreeNode = (AgentTreeNode) iterator.next();
//            result.add(agentTreeNode.getSelfId());
//            if (null != agentTreeNode.getChildList()) {
//                this.getTeamAgentList(result, agentTreeNode.getChildList());
//            }
//        }
//
//    }
//
//    /**
//     * 根据商家业绩计算商家返佣
//     *
//     * @param merchantId
//     * @param amountPerformances
//     * @return
//     */
//    private MerchantRebates getMerchantRebates(Long merchantId, BigDecimal amountPerformances) {
//        Result<MerchantRebatesConfigPojo> configResult = this.getCommissionLadder(amountPerformances);
//        Assert.state(configResult.getSuccess(), "==============商家 mechantId:" + merchantId + "  获取返佣配置信息失败！" + configResult.getMessage());
//        MyLogManager.develop("商家 id：" + merchantId + "业绩 :  " + amountPerformances + "匹配的返点：" + JSON.toJSONString(configResult.getData()));
//        BigDecimal modAmount = amountPerformances.divide(this.baseUnit).setScale(0, RoundingMode.DOWN);
//        BigDecimal rebates = configResult.getData().getRebates();
//        MerchantRebates merchantRebates = MerchantRebates.builder().orderAmount(amountPerformances).rebatesAmount(modAmount.multiply(rebates)).rebates(rebates).build();
//        MyLogManager.develop("商家 Id：:" + merchantId + "业绩计算结果:" + JSON.toJSONString(merchantRebates));
//        return merchantRebates;
//    }
//
//    private BigDecimal getAmountPerformances(Set<Long> merchantIds) {
//        BigDecimal amountPerformances = new BigDecimal(0);
//        //如果不存在下级代理返回业绩0
//        if (null == merchantIds || merchantIds.size() == 0) {
//            return BigDecimal.ZERO;
//        }
//        //查询需要计算佣金的订单
//        Result<List<OrderRecordDto>> orderAmountInOneDay = agentRepository.listCompleteOrderByMerchantIds(merchantIds, new Date());
//        Result<List<PerformanceRateDto>> performanceRateList = remoteService.list(PerformanceRateParam.builder().status(1).build());
//        Assert.state(null != performanceRateList && null != performanceRateList.getData(), "======获取 业绩计算配置失败  --> performance:" + JSON.toJSONString(performanceRateList));
//        //循环计算订单业绩
//        for (OrderRecordDto orderRecordDto : orderAmountInOneDay.getData()) {
//            //查找订单业绩返点率 计算订单业绩 匹配不到订单业绩返点率率 订单业绩按0计算
//            for (PerformanceRateDto performanceRateDto : performanceRateList.getData()) {
//                try {
//                    //拦截业绩计算中出现的各种可能出现的异常，方便定位该订单 此处暂时不删
//                    if (orderRecordDto.getRevcPaymentWay().equalsIgnoreCase(performanceRateDto.getPayWay()) && orderRecordDto.getOrderType() == performanceRateDto.getOrderType()) {
//                        BigDecimal performance = performanceRateDto.getPerformance(orderRecordDto.getRealPaymentAmount());
//                        amountPerformances = amountPerformances.add(performance);
//                    }
//                } catch (Exception var10) {
//                    MyLogManager.develop("=======该订单业绩计算失败： -->,[订单是 orderRecordDto：" + JSON.toJSONString(orderRecordDto) + "业绩计算方法是 performanceRateDto：" + JSON.toJSONString(performanceRateDto) + "  异常信息：" + var10.getMessage() + "]");
//                    AlertUtil.sendAlertMsg("计算佣金", "=======该订单业绩计算失败： -->,[订单是 orderRecordDto：" + JSON.toJSONString(orderRecordDto) + "业绩计算方法是 performanceRateDto：" + JSON.toJSONString(performanceRateDto) + "  异常信息：" + var10.getMessage() + "]", AlertLevelEnum.INFO);
//                    return BigDecimal.ZERO;
//                }
//            }
//        }
//        return amountPerformances;
//    }
//
//    /**
//     * 优化实时佣金查询，减少对数据库的查询次数。
//     *
//     * @param merchantIds
//     * @param merchantOrderMap
//     * @param flag
//     * @return
//     */
//    private BigDecimal getAmountPerformancesFromMap(Set<Long> merchantIds, Map<Long, Map<String, BigDecimal>> merchantOrderMap, boolean flag) {
//        BigDecimal amountPerformances = new BigDecimal(0);
//        if (!flag) {
//            //如果不存在下级代理返回业绩0
//            if (null == merchantIds || merchantIds.size() == 0) {
//                return BigDecimal.ZERO;
//            }
//            //订单业绩集合
//            List<OrderRecordDto> orderRecordDtoList = new ArrayList<>();
//            Result<List<OrderRecordDto>> orderAmountInOneDay = agentRepository.listCompleteOrderByMerchantIds(merchantIds, new Date());
//            Result<List<PerformanceRateDto>> performanceRateList = remoteService.list(PerformanceRateParam.builder().status(1).build());
//            Assert.state(null != performanceRateList && null != performanceRateList.getData(), "======获取业绩计算配置失败  --> performance:" + JSON.toJSONString(performanceRateList));
//            //循环计算订单业绩
//            for (OrderRecordDto orderRecordDto : orderAmountInOneDay.getData()) {
//                //查找订单业绩返点率 计算订单业绩 匹配不到订单业绩返点率率 订单业绩按0计算
//                for (PerformanceRateDto performanceRateDto : performanceRateList.getData()) {
//                    if (orderRecordDto.getRevcPaymentWay().equalsIgnoreCase(performanceRateDto.getPayWay()) && orderRecordDto.getOrderType() == performanceRateDto.getOrderType()) {
//                        BigDecimal performance = performanceRateDto.getPerformance(orderRecordDto.getRealPaymentAmount());
//                        /**
//                         * 将订单收款金额转换成业绩   加入业绩集合中
//                         */
//                        orderRecordDtoList.add(OrderRecordDto.builder().merchantId(orderRecordDto.getMerchantId())
//                                .realPaymentAmount(performance)
//                                .orderId(orderRecordDto.getOrderId()).build());
//                        amountPerformances = amountPerformances.add(performance);
//                    }
//                }
//            }
//            Map<Long, Map<String, BigDecimal>> collect = orderRecordDtoList.stream().filter(e -> e.getMerchantId() != null && e.getRealPaymentAmount() != null).collect(Collectors.groupingBy(OrderRecordDto::getMerchantId, Collectors.toMap(OrderRecordDto::getOrderId, a -> a.getRealPaymentAmount(), (k1, k2) -> k2)));
//            collect.keySet().forEach(e->{merchantOrderMap.put(e,collect.get(e));});
//            return amountPerformances;
//        } else {
//            //计算下级业绩从内存中取
//            for (long mid : merchantIds) {
//                Map<String, BigDecimal> stringBigDecimalConcurrentMap = merchantOrderMap.get(mid);
//                if (null!=stringBigDecimalConcurrentMap) {
//                    Set<String> strings = stringBigDecimalConcurrentMap.keySet();
//                    for (String orderId : strings) {
//                        amountPerformances = amountPerformances.add(stringBigDecimalConcurrentMap.get(orderId));
//                    }
//                }
//            }
//            return amountPerformances;
//        }
//    }
//
//
//    private Result<MerchantRebatesConfigPojo> getCommissionLadder(BigDecimal teamWater) {
//        if (null == teamWater || teamWater.compareTo(BigDecimal.ZERO) < 0) {
//            return Result.error("团队流水不能小于零");
//        }
//        List<MerchantRebatesConfigPojo> ladders = agentRepository.getMerchantCommissionLadderList();
//        if (ladders.isEmpty()) {
//            return Result.error("未获取到流水返点配置");
//        }
//        // 返点倒序排序
//        Collections.sort(ladders, new Comparator<MerchantRebatesConfigPojo>() {
//            @Override
//            public int compare(MerchantRebatesConfigPojo o1, MerchantRebatesConfigPojo o2) {
//                return o2.getRebates().compareTo(o1.getRebates());
//            }
//        });
////        MyLogManager.develop("===========>>>业绩阶梯：" + JSON.toJSONString(ladders));
//        // 阶梯金额单位为万元
//        BigDecimal teamWaterTemp = teamWater.divide(baseUnit);
//        for (MerchantRebatesConfigPojo ladder : ladders) {
//            if (teamWaterTemp.compareTo(ladder.getAmount()) >= 0) {
//                return Result.success(ladder);
//            }
//        }
//        MerchantRebatesConfigPojo pojo = MerchantRebatesConfigPojo.builder()
//                .amount(BigDecimal.ZERO)
//                .rebates(BigDecimal.ZERO)
//                .build();
//        return Result.success(pojo);
//    }
//}
