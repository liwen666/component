package com.temp.common.base.h2;

import java.sql.SQLException;

import org.h2.tools.Server;

public class h2Service2TCP {
	public static void main(String[] args) {
    	try {
            System.out.println("正在启动h2...");
            Server server = Server.createTcpServer(new String[] { "-tcpAllowOthers", "-tcpPort",
          		"8043" }).start();//其他电脑可以连接
//            Server server = Server.createTcpServer(
//            		new String[] { "-tcp", "-tcpAllowOthers", "-tcpPort",
//            		"8043" }).start();
            System.out.println("启动成功：" + server.getStatus());
            /**
             * 正在启动h2...
			        启动成功：Web Console server running at http://192.168.9.180:8082 (only local connections)
             */
        } catch (SQLException e) {
            System.out.println("启动h2出错：" + e.toString());
 
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
}
