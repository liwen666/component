//package com.temp.common.dubbo.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import vip.dcpay.constant.mq.NamesConstant;
//
//
//@Configuration
//public class RabbitConfig {
//
//    //完成订单队列
//    public static final String COMMISSION_QUEUE = NamesConstant.toOrderFinish_QUEUE;
//    //完成订单通知交换机
//    public static final String COMMISSION_EXCHANGE = NamesConstant.toOrderFinish;
//    //完成订单routing_key
//    public static final String COMMISSION_ROUTING_KEY = NamesConstant.toOrderFinish_KEY;
//    //测试
//
//
//
//    @Bean
//    public Queue commission_queue() {
//        return new Queue(COMMISSION_QUEUE);
//    }
//
//    @Bean
//    public DirectExchange commission_exchange() {
//        return new DirectExchange(COMMISSION_EXCHANGE);
//    }
//
//    @Bean
//    public Binding topicBinding() {
//        return BindingBuilder.bind(commission_queue()).to(commission_exchange()).with(COMMISSION_ROUTING_KEY);
//    }
//
//
//}
