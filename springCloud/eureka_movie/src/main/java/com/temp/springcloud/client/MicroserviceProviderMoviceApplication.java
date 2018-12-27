package com.temp.springcloud.client;

import com.netflix.discovery.EurekaNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient //启动EnableEureka客户端
@RestController
public class MicroserviceProviderMoviceApplication {

    @GetMapping("/movie/{name}")
    public String hello(@PathVariable String name) {
        System.out.println(name + " welcome . My is microservice provider movie");
        return name + " welcome movie"+"用户微服务：";
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceProviderMoviceApplication.class, args);
    }
}