//package com.temp.common.mq.dev;
//
//import com.alibaba.fastjson.JSON;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//import vip.dcpay.commission.config.RabbitConfig;
//import vip.dcpay.dto.mq.MessageBasic;
//
////@Slf4j
//@Component
//public class OrderFinishReceiver {
//
//
//    @RabbitListener(queues = RabbitConfig.COMMISSION_QUEUE)
//    public void receiveMsg(String msg) {
//        {
//
//
//            MessageBasic messageBasic = JSON.parseObject(msg, MessageBasic.class);
//
//            System.out.println(msg);
//
//        }
//    }
//}