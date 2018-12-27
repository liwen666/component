package com.temp.common.base.log.logback;


import java.lang.reflect.Field;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogNameContextFilter  extends Filter<LoggingEvent> {

	@Override
	public FilterReply decide(LoggingEvent event) {
		try {
			Field field = LogPathCus.class.getDeclaredField("info");
			System.out.println(field.get("info"));
//			try {
//				field.set("info", "aaaaaa");
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilterReply.ACCEPT;
	}


	

}
