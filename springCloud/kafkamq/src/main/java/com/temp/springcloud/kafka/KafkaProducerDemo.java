package com.temp.springcloud.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class KafkaProducerDemo {
    private  final Producer<String,String> kafkaProducer;

    public final static String TOPIC="JAVA_TOPIC";

    private KafkaProducerDemo(){
        kafkaProducer=createKafkaProducer() ;
    }
    private Producer<String,String> createKafkaProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.42.220:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);
        return kafkaProducer;
    }

    void produce(){
        for(int i=1;i<1000;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String key=String.valueOf("key"+i);
            String data="hello kafka message:"+key;
            kafkaProducer.send(new ProducerRecord<String, String>(TOPIC, key, data), new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println(recordMetadata);

                }
            });
            System.out.println(data);
        }
    }

    public  static  void main(String[] args){
        new KafkaProducerDemo().produce();
    }
}