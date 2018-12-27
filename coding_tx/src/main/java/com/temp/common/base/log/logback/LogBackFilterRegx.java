package com.temp.common.base.log.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
/**
 * 位日志分类管理过滤
* <p>describe</p> 
* <p>LogBackFilterRegx.java</p>
* <p></p>
* @author lw
* @date 2016年12月14日
* @version 1.0
* @link
 */
public class LogBackFilterRegx extends Filter<LoggingEvent>{
	private String regxaccept;

	@Override
	public FilterReply decide(LoggingEvent eventObj) {
		LoggingEvent event=eventObj;
		if(event.getLoggerName().matches(regxaccept)){
			return FilterReply.ACCEPT;
		}
		return FilterReply.DENY;
	}

	public String getRegxaccept() {
		return regxaccept;
	}

	public void setRegxaccept(String regxaccept) {
		this.regxaccept = regxaccept;
	}
	

}
