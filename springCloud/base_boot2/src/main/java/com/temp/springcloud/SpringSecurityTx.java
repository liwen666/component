package com.temp.springcloud;

import com.temp.springcloud.bean.UserDomain;
import com.temp.springcloud.io.StreamToFile;
import com.temp.springcloud.sqlscript.controller.SqlFileExecutorController;
import com.temp.springcloud.sqlscript.controller.SqlFileExecutorControllerForJar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
@SpringBootApplication
@EnableAutoConfiguration(exclude = {  //关闭security模块配置
        SecurityAutoConfiguration.class
})
@RestController
@ComponentScan(basePackages = {"com.temp.springcloud", "com.temp.springcloud.sqlscript","com.temp.springcloud.io"})
public class SpringSecurityTx  {
    private static final Logger logger= LoggerFactory.getLogger(SpringSecurityTx.class);

    public static void main(String[] args) throws Exception {
        logger.info("开始启动！");
        ConfigurableApplicationContext run = SpringApplication.run(SpringSecurityTx.class, args);
        ConfigurableListableBeanFactory beanFactory = run.getBeanFactory();
        DataSource bean = beanFactory.getBean(DataSource.class);
        SqlFileExecutorControllerForJar bean1    = beanFactory.getBean(SqlFileExecutorControllerForJar.class);
        StreamToFile bean2 = beanFactory.getBean(StreamToFile.class);
        System.out.println(bean2.getUser("n"));


        bean1.initSqlScriptSource(null,null);

    }
    @RequestMapping("/rest/{name}")
    public UserDomain getUser(@PathVariable(value = "name")String name){
        System.out.println(name);
        UserDomain ud = new UserDomain("li","10");
        return ud;
    }

}
