package com.temp.common.base.h2;

import java.sql.SQLException;

import org.h2.tools.Server;

public class H2WebServiceTest {
	/**
	 * 创建客户端访问服务器
	 * @param args
	 */
	 public static void main(String[] args) {
	    	try {
	            System.out.println("正在启动h2...");
	            Server server = Server.createWebServer().start();
//	            Server server = Server.createWebServer(
//	            		new String[] { "-tcp", "-tcpAllowOthers", "-tcpPort",
//	            		"8043" }).start();
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
