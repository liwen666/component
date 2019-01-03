package com.temp.springcloud.base_boot2.user;

import lombok.Data;
import org.h2.tools.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.sql.SQLException;

/**
 * CommandLineRunner  和 applicationrunner一样
 *  @ PostConstruct  加在方法上进行初始化
 *
    @ Order(1)调整初始化化顺序
 *
 *  执行销毁的方法
 *  implements DisposableBean, ExitCodeGenerator
 *
   @ PreDestroy 放在方法上面
 *
 */
// @Data
// @Configuration
// @ConfigurationProperties(prefix = "h2service")
public class H2Service implements ApplicationRunner,DisposableBean {
    Server server;
    Server serverWeb;

    @Override
    public void run(ApplicationArguments args) throws Exception {
            try {
                System.out.println("正在启动h2...");
//              server = Server.createTcpServer(new String[] {  "-tcpPort",
                server = Server.createTcpServer(new String[]{"-tcpAllowOthers", "-tcpPort",
                        "8043"}).start();//其他电脑可以连接
//            Server server = Server.createTcpServer(
//            		new String[] { "-tcp", "-tcpAllowOthers", "-tcpPort",
//            		"8043" }).start();
                System.out.println("启动成功：" + server.getStatus());
                serverWeb = Server.createWebServer().start();
                System.out.println(serverWeb.getStatus());
                /**
                 * 正在启动h2...
                 启动成功：Web Console server running at http://192.168.9.180:8082 (only local connections)
                 */
            } catch (SQLException e) {
                System.out.println("启动h2出错：" + e.toString());

                e.printStackTrace();
            }
    }

    @Override
    public void destroy() throws Exception {
            if (server != null) {
                System.out.println("正在关闭h2...");
                server.stop();
                System.out.println("关闭成功.");
            }
            if (serverWeb != null) {
                System.out.println("正在关闭h2...");
                serverWeb.stop();
                System.out.println("关闭成功.");
            }
            System.out.println("关闭");
    }

}
