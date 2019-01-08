package com.temp.springcloud.base_boot2.init;

import com.temp.springcloud.base_boot2.listener.H2Service;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service(value = "customer")
public class CustomerDestoryBean implements ApplicationRunner,DisposableBean {


    @Override
    public void destroy() throws Exception {
        H2Service.getH2Service().destroy();
        System.out.println("执行销毁");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("执行初始化");

    }

    public void say() {
        System.out.println("hello");
    }
}
