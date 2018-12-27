package com.temp.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer//开启Eureka Server
public class MicroserviceDiscoveryEurekaApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceDiscoveryEurekaApplication.class, args);
    }
}