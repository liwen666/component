package com.temp.springcloud.security;

import com.temp.springcloud.admin.AdminController;
import com.temp.springcloud.base_boot2.BaseBoot2Application;
import com.temp.springcloud.base_boot2.init.CustomerDestoryBean;
import com.temp.springcloud.sqlscript.controller.SqlFileExecutorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
//@EnableWebMvc
@SpringBootApplication
@EnableAutoConfiguration(exclude = {  //关闭security模块配置
        SecurityAutoConfiguration.class
})
@ComponentScan(basePackages = {"com.temp.springcloud", "com.temp.springcloud.sqlscript"})
public class SpringSecurityTx {
    private static final Logger logger= LoggerFactory.getLogger(SpringSecurityTx.class);

    public static void main(String[] args) throws Exception {
        logger.info("开始启动！");
        ConfigurableApplicationContext run = SpringApplication.run(SpringSecurityTx.class, args);
        ConfigurableListableBeanFactory beanFactory = run.getBeanFactory();
        DataSource bean = beanFactory.getBean(DataSource.class);
        SqlFileExecutorController bean1 = beanFactory.getBean(SqlFileExecutorController.class);
        bean1.initSqlScriptSource(null,null);

    }
}
