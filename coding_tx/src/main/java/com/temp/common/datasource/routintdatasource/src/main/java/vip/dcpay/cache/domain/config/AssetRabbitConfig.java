package com.temp.common.datasource.routintdatasource.src.main.java.vip.dcpay.cache.domain.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AssetRabbitConfig {

    //交换机
    public static final String EXCHANGE_ALTER_ASSET = "alterAssetExchange";

    //队列
    public static final String QUEUE_ALTER_ASSET= "toAlterAssetNotify";

    //路由
    public static final String ROUTING_KEY_ALTER_ASSET= "toAlterAssetNotifyKey";




    @Bean
    public Queue asset_queue() {
        return new Queue(QUEUE_ALTER_ASSET);
    }

    @Bean
    public DirectExchange asset_exchange() {
        return new DirectExchange(EXCHANGE_ALTER_ASSET);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(asset_queue()).to(asset_exchange()).with(ROUTING_KEY_ALTER_ASSET);
    }


}
