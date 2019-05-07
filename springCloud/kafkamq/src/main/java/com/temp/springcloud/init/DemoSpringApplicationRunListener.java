package com.temp.springcloud.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author hafiz.zhang
 * @description:
 * @date Created in 2018/6/7 20:07.
 */
public class DemoSpringApplicationRunListener implements SpringApplicationRunListener {
    private final SpringApplication application;
    private final String[] args;
    public DemoSpringApplicationRunListener(SpringApplication sa, String[] args) {
        this.application = sa;
        this.args = args;
    }

    @Override
    public void starting() {
        System.out.println("自定义starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("自定义environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("自定义contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

        System.out.println("自定义contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }

}