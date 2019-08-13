package com.temp.common.mq.dev.src.main.java.vip.dcpay.mq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import vip.dcpay.util.frame.spring.PropertiesConfigurer;


@Configuration
@ComponentScan
@EnableRabbit
public class MqConfig {

	@Bean
	public PropertiesConfigurer mqPropertiesConfigurer(){
		PropertiesConfigurer prop = new PropertiesConfigurer();
		prop.setIgnoreUnresolvablePlaceholders(true);
		prop.setIgnoreResourceNotFound(true);
		prop.setLocations(new ClassPathResource("mq.properties"));
		return prop;
	}

    @Bean
    public ConnectionFactory mqConnectionFactory(){
    	
    	PropertiesConfigurer prop = mqPropertiesConfigurer();
    	
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(prop.getProperty("rabbitmq.url"));
        connectionFactory.setPort(Integer.parseInt(prop.getProperty("rabbitmq.port")));
        connectionFactory.setUsername(prop.getProperty("rabbitmq.username"));
        connectionFactory.setPassword(prop.getProperty("rabbitmq.password"));
        connectionFactory.setVirtualHost(prop.getProperty("rabbitmq.virtual-host"));
        return connectionFactory;
    }
 
    @Bean
    public RabbitAdmin rabbitAdmin(){
    	
    	ConnectionFactory mqConnectionFactory = mqConnectionFactory();
    	
        return new RabbitAdmin(mqConnectionFactory);
    }
    
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(){

    	ConnectionFactory mqConnectionFactory = mqConnectionFactory();

    	//SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(mqConnectionFactory);
        factory.setPrefetchCount(1);
        return factory;
    }

    @Bean("rabbitListenerContainerFactory2")
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory2(){

        ConnectionFactory mqConnectionFactory = mqConnectionFactory();

        //SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(mqConnectionFactory);
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return factory;
    }
    
	@Bean("mqTemplate")
	public RabbitTemplate mqTemplate(){
    	ConnectionFactory mqConnectionFactory = mqConnectionFactory();
		RabbitTemplate template = new RabbitTemplate(mqConnectionFactory);
		return template;
	}

}
