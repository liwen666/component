package com.temp.springcloud.base_boot2;

import org.h2.tools.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class BaseBoot2Application {
	Server server;
	Server serverWeb;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BaseBoot2Application.class);
		//配置事件监听器
//		application.addListeners((ApplicationListener<?>) new MyApplicationListener());
		ConfigurableApplicationContext context = SpringApplication.run(BaseBoot2Application.class, args);

//		context.close();

	}

}

