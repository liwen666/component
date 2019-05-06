package com.temp.springcloud.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;


/**
 *  * @Author: BillYu
 * <p>
 *  * @Description:kafka连接测试
 * <p>
 *  * @Date: Created in 下午3:52 2018/6/4.
 * <p>
 *  
 */

@RestController
public class SampleController {
    private final Logger logger = LoggerFactory.getLogger(SampleController.class);
    @Autowired
    private KafkaTemplate<String, String> template;
    @RequestMapping("/send")
    @ResponseBody
    String send(String topic, String key, String data) {
        template.send(topic, key, data);
        return "success";
    }
    @KafkaListener(id = "test", topics = "test")
    public void listenTest(ConsumerRecord<?, ?> cr) throws Exception {
        System.out.println("===>listen");
        logger.info("{} - {} : {}", cr.topic(), cr.key(), cr.value());

    }
    @KafkaListener(id = "tests",topics = {"aaa"})
    public void listenAllTest(ConsumerRecord<?, ?> data) throws Exception {
        String topic = data.topic();//消费的topic
        logger.info("-------------recieve message from {} topic-------------", topic);
        logger.info("partition:{}", String.valueOf(data.partition()));//消费的topic的分区
        logger.info("offset:{}", String.valueOf(data.offset()));//消费者的位置
        logger.info("get message from {} topic : {}", topic, data.value());//接收到的消息

    }
    @Autowired
    private  KafkaTemplate<String,String> kafkaTemplate;//kafkaTemplate相当于生产者

    @RequestMapping(value = "/{topic}/send",method = RequestMethod.GET)
    public void sendMeessage(
            @RequestParam(value = "message",defaultValue = "hello world") String message,
            @PathVariable final String topic) {
        logger.info("start sned message to {}",topic);
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic,message);//发送消息，topic不存在将自动创建新的topic
        listenableFuture.addCallback(//添加成功发送消息的回调和失败的回调
                result -> logger.info("send message to {} success",topic),
                ex -> logger.info("send message to {} failure,error message:{}",topic,ex.getMessage()));
    }

    @RequestMapping(value = "/default/send",method = RequestMethod.GET)
    public void sendMeessagedefault() {//发送消息到默认的topic
        logger.info("start send message to default topic");
        kafkaTemplate.sendDefault("你好，世界");
    }
}