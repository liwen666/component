package com.temp.common.base.log.logback;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ch.qos.logback.core.PropertyDefinerBase;
/**
 * 
* <p>describe 动态获取日志的路径</p> 
* <p>LogPathCus.java</p>
* <p></p>
* @author lw
* @date 2016年12月13日
* @version 1.0
* @link
 */
public class LogPathCus extends PropertyDefinerBase{
	String info;
	@Override
	public String getPropertyValue() {
		
		InetAddress netAddress=getInetAddress();
		info=getHostName(netAddress);
		return info;
	}

	private String getHostName(InetAddress netAddress) {
		if(null==netAddress){
			return null;
		}
		String ip= netAddress.getHostName();
		return ip;
	}

	private InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

}
