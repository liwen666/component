package com.temp.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

//@EnableAutoConfiguration(exclude = {  //关闭security模块配置
//        SecurityAutoConfiguration.class
//})
@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.temp.springcloud", "com.temp.springcloud.sqlscript"})
public class AppServer {
    public static void main(String[] args) {
        SpringApplication.run(AppServer.class);
    }
}
