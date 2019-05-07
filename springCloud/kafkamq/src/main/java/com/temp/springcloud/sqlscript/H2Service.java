package com.temp.springcloud.sqlscript;

import org.h2.tools.Server;
import org.springframework.stereotype.Service;

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
//public class H2Service implements ApplicationRunner,DisposableBean {
    @Service
public class H2Service {
    Server server;
    Server serverWeb;
    private H2Service(){}

    public void run() {
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

    public void destroy() {
            if (server != null) {
                System.out.println("正在关闭h2...");
                server.stop();
                System.out.println("关闭成功.");
            }
            if (serverWeb != null) {
                System.out.println("正在关闭h2web...");
                serverWeb.stop();
                System.out.println("关闭成功.");
            }
            System.out.println("关闭");
    }
    public static H2Service  getH2Service(){
        return H2ServiceHolder.h2Service;
    }

    public static class H2ServiceHolder {
        {
            System.out.println("创建h2service");//不会执行
        }
        private static H2Service h2Service = new H2Service();
    }
}
