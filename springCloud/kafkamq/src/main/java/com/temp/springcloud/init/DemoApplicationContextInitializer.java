package com.temp.springcloud.init;

import com.temp.springcloud.sqlscript.H2Service;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author hafiz.zhang
 * @description:
 * @date Created in 2018/6/7 20:33.
 */
public class DemoApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
//        AnnotationConfigServletWebServerApplicationContext anotationApp= (AnnotationConfigServletWebServerApplicationContext) applicationContext;
        /**
         * 只注册并没有被加载
         */

        H2Service h2Service = H2Service.getH2Service();
        h2Service.run();
        System.out.println("自定义DemoApplicationContextInitializer的initialize方法");
    }
}