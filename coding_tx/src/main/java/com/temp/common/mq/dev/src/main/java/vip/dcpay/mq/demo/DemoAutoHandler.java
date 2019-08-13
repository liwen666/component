//package com.temp.common.mq.dev.src.main.java.vip.dcpay.mq.demo;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//
///**
// * RabbitMQ注解实现消费者
// * @Demo : 自动确认
// */
////@Component
//@RabbitListener(queues = "q_queue_auto")
//public class DemoAutoHandler {
//
//	/**
//	 * @param body 解码后的消息
//	 */
//	@RabbitHandler
//	public void handler(byte[] body)
//			throws Exception {
//		String message = new String(body);
//
//		if (message.equals("1")) {
//			throw new RuntimeException("主动抛出异常");
//		}
//
//		System.out.println("消費成功:" + message);
//
//	}
//
//}
