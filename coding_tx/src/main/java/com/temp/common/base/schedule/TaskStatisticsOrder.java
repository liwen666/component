//package com.temp.common.base.schedule;
//
//import com.alibaba.fastjson.JSON;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import vip.dcpay.calculate.application.common.IConstant;
//import vip.dcpay.calculate.domain.service.index.OrderStatisticsService;
//import vip.dcpay.redis.service.RedisService;
//import vip.dcpay.util.date.DateUtils;
//import vip.dcpay.vo.backstage.index.OrderStatisticsVo;
//
//import javax.annotation.Resource;
//import java.util.Calendar;
//import java.util.Date;
//
//@Component
//public class TaskStatisticsOrder {
//
//    @Resource
//    OrderStatisticsService orderStatisticsService;
//
//    @Resource
//    RedisService redisService;
//
//
//    /**
//     * 统计昨天的订单数据
//     */
//    @Scheduled(fixedDelay = 30 * 60 * 1000)
//    public void statisticsYesterdayOrder() {
//        Date lastMinDay = DateUtils.getLastMinDay(new Date());
//        Date lastMaxDay = DateUtils.getLastMaxDay(new Date());
//        OrderStatisticsVo statistics = orderStatisticsService.statistics(lastMinDay, lastMaxDay);
//        redisService.save(IConstant.BACKSTAGE_INDEX_ORDER_YESTERDAY, JSON.toJSONString(statistics));
//    }
//
//    /**
//     * 统计当天的数据
//     */
//    @Scheduled(fixedDelay = 30*60*1000)
//    public void statisticsCurrentDay(){
//        Date lastMinDay = DateUtils.getLastMinDay(DateUtils.addDays(new Date(),1));
//        OrderStatisticsVo statistics = orderStatisticsService.statistics(lastMinDay, new Date());
//        redisService.save(IConstant.BACKSTAGE_INDEX_ORDER_CURRENT, JSON.toJSONString(statistics));
//    }
//
//    /**
//     * 统计最近三十分钟
//     */
//    @Scheduled(fixedDelay = 30*60*1000)
//    public void statisticsNearlyThirtyMinute(){
//        Calendar calendar=Calendar.getInstance();
//        calendar.add(Calendar.MINUTE,-30);
//        Date time = calendar.getTime();
//        OrderStatisticsVo statistics = orderStatisticsService.statistics(time, new Date());
//        redisService.save(IConstant.BACKSTAGE_INDEX_ORDER_THIRTY_MINUTE, JSON.toJSONString(statistics));
//    }
//
//    /**
//     * 统计最近一小时
//     */
//    @Scheduled(fixedDelay = 30*60*1000)
//    public void statisticsOneHour(){
//        Calendar calendar=Calendar.getInstance();
//        calendar.add(Calendar.HOUR,-1);
//        Date time = calendar.getTime();
//        OrderStatisticsVo statistics = orderStatisticsService.statistics(time, new Date());
//        redisService.save(IConstant.BACKSTAGE_INDEX_ORDER_ONE_HOUR, JSON.toJSONString(statistics));
//    }
//
//    /**
//     *统计最近三小时
//     */
//    @Scheduled(fixedDelay = 30*60*1000)
//    public void statisticsThreeHour(){
//        Calendar calendar=Calendar.getInstance();
//        calendar.add(Calendar.HOUR,-3);
//        Date time = calendar.getTime();
//        OrderStatisticsVo statistics = orderStatisticsService.statistics(time, new Date());
//        redisService.save(IConstant.BACKSTAGE_INDEX_ORDER_THREE_HOUR, JSON.toJSONString(statistics));
//    }
//
//}
