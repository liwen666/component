package com.temp.springcloud.base_boot2;


import com.temp.springcloud.base_boot2.listener.MyApplicationEvent;
import com.temp.springcloud.base_boot2.listener.MyApplicationListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApplicationListener {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationListener.class);
        //配置事件监听器  也可以以组件注解的方式加入
        application.addListeners(new MyApplicationListener());
        ConfigurableApplicationContext context =application.run(args);
        //发布事件
        context.publishEvent(new MyApplicationEvent(new Object()));
        context.close();
    }
}
