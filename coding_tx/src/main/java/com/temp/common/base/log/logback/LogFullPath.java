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
public class LogFullPath extends PropertyDefinerBase{
	
	@Override
	public String getPropertyValue() {
		String path=System.getProperty("user.dir");
		System.out.println(path);
		return path;
	}


}
