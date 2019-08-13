//package com.temp.common.mq.dev.src.main.java.vip.dcpay.mq.demo;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//
//import com.rabbitmq.client.Channel;
//
///**
// * RabbitMQ注解实现消费者
// * @Demo : 处理手动ACK确认
// */
////@Component
//public class DemoManualHandler {
//
//	/**
//	 * @param body
//	 *            :解码后的消息
//	 * @param delicveryTag
//	 *            :使用@Header接口获取messageProperties中的DELIVERY_TAG属性。
//	 * @param channel
//	 *            : 接受消息所使用的信道
//	 */
//	@RabbitListener(queues = "q_queue_manual")
//	public void handler(@Payload byte[] body, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel)
//			throws Exception {
//		String message = new String(body);
//
//		System.out.println("deliveryTag=" + deliveryTag);
//
//		if (message.equals("1")) {
//			throw new RuntimeException("主动抛出异常");
//		}
//
//		channel.basicAck(deliveryTag, false);
//
//		System.out.println("消費成功:" + message);
//
//	}
//
//}
