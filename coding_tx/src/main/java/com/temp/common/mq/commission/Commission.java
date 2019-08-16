package com.temp.common.mq.commission;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.temp.common.base.thread.pooldesign.jdk.ThreadPoolDemo;
import com.temp.common.common.util.DateUtils;
import org.quartz.spi.ThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @time 14:22
 */
public class Commission {
    private static final Logger LOGGER = LoggerFactory.getLogger(Commission.class);
    private static final String EXCHANGE_NAME = NamesConstant.toOrderFinish;
    private static final String QUEUE_NAME_03 = NamesConstant.toOrderFinish_QUEUE;

    /**
     * 订单最终记录队列路由键
     */
    public static final String toOrderFinish_KEY = NamesConstant.toOrderFinish_KEY;

    public static void main(String[] args) {
        try {
            //连接管理器：我们的应用程序与RabbitMQ建立连接的管理器。
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ服务器地址
//            factory.setHost("192.168.1.124");
            factory.setHost("192.168.42.230");
            factory.setPort(5672);
            //设置帐号密码,默认为guest/guest，所以下面两行可以省略
            factory.setUsername("root");
            factory.setPassword("root");
            factory.setVirtualHost("/qq");

            //创建一个连接
            Connection connection = factory.newConnection();
            //创建一个信道
            Channel channel = connection.createChannel();

            //声明一个Direct类型的交换机

            /**
             * 此处如果是系统创建交换机，自己不需要创建，只需要往交换机发送数据即可
             */
            System.out.println("=========================================================================================");
//            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            //申明两个个队列
            System.out.println("=========================================================================================");
            /**
             * 第一个参数是queue：要创建的队列名
             * 第二个参数是durable：是否持久化。如果为true，可以在RabbitMQ崩溃后恢复消息
             * 第三个参数是exclusive：true表示一个队列只能被一个消费者占有并消费
             * 第四个参数是autoDelete：true表示服务器不在使用这个队列是会自动删除它
             * 第五个参数是arguments：其它参数
             */
            channel.queueDeclare(QUEUE_NAME_03, true, false, false, null);
            //开始绑定
            /**
             * 第一个队列对美女感兴趣
             * 第二个队列对股票和美食感兴趣
             *
             * 第一个参数是queue：要被绑定的队列名
             * 第一个参数是exchange：队列绑定到哪个路由器上
             * 第三个参数是routingKey：队列对这种路由键感兴趣，路由器会把这种routingKey的消息发送给队列
             */
            //队列一对交换机说，关于美女的消息给我
            //第三个队列  开发
            channel.queueBind(QUEUE_NAME_03, EXCHANGE_NAME, toOrderFinish_KEY);


            //模拟发送消息
            String message04 = "完成订单";

            //向交换机发送3个消息，分别是关于美女、股票、美食
//            channel.basicPublish(EXCHANGE_NAME, toOrderFinish_KEY, null, message04.getBytes("UTF-8"));

            ExecutorService es = Executors.newFixedThreadPool(5);//创建固定大小的线程
            for(int i=0;i<100;i++){
                //线程池可以 处理Callablel类型的任务  获取返回值
                /**
                 * 线程是5个5个的执行任务
                 * 这里下面两个处理任务的效果是一样的
                 */
//                es.submit(task);//这个会返回一个future对象
                es.execute(new Thread(){
                    @Override
                    public void run() {

                        for (int i = 0; i < 10; i++) {
                            OrderFinishMsgDto build = OrderFinishMsgDto.builder().createTime(new Date()).customerId((long) i).income(new BigDecimal(i))
                                    .orderNo(i+"").orderStatus(100).orderType(1).transactionAmount(new BigDecimal(100)).realPaymentAmount(new BigDecimal(100))
                                    .orderFinishTime(new Date()).payWay("wexin").recordId((long) i)
                                    .merchantId((long) i).build();
//                OrderFinishMsgDto build = OrderFinishMsgDto.builder().merchantId((long) i).build();
                            MessageBasic build1 = MessageBasic.builder().body(JSON.toJSONString(build)).build();

                            try {
                                channel.basicPublish(EXCHANGE_NAME, toOrderFinish_KEY, null, JSON.toJSONBytes(build1));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }


            Thread.sleep(5000);
            es.shutdown();
            LOGGER.info("消息发送成功");

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}