package com.temp.common.common.filter;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * XSS过滤
 */
public class XssFilter implements Filter {
	//日志
    private static Logger log= LoggerFactory.getLogger(XssFilter.class);
	
    
	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		
		
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		
		
		
		
		//获取路径
		String path = xssRequest.getRequestURI();
		//获取参数
		String param = xssRequest.getQueryString();
		String value = param;
		Boolean flag = true;
		if(StringUtils.isNotBlank(value)){
			if(StringUtils.isNotBlank(value) && !value.equals("null")){
				value = value.toLowerCase();
				value = URLDecoder.decode(URLDecoder.decode(value,"UTF-8"),"UTF-8");
				//serverid=common.appinstallcomponent&method=loadComponent&appid=fportal&n1=jdbc:oracle:thin:@127.0.0.1:1521:orcl&n2=1&n3=FASP_11
				if(value.contains("<") || value.contains(">") 
						|| value.contains("\'") || value.contains("\"") 
						|| value.contains("./") || value.contains(";") 
						|| value.contains("]")  || value.contains("*") 
						|| value.contains("$")  || value.contains("+") 
						|| value.contains("＃") || value.contains("#")
						|| value.contains("!")  //|| value.contains("@")
						|| value.contains("javascript") || value.contains("script") 
						|| value.contains("{")  || value.contains("}") 
						|| value.contains("(")  || value.contains(")")
						|| value.contains("#")  || value.contains("\\u") 
						|| value.contains("fromcharcode") || value.contains("alert") 
						|| value.contains("prompt") || value.contains("onerror") 
						|| value.contains("\\(") || value.contains("\\)")
						|| value.contains("eval(") || value.contains("\\((.*)\\)")
						|| value.contains("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']")
						|| value.contains("%2e") || value.contains("%c0%af")
						|| value.contains("%25E9%") || value.contains("%5c") || value.contains("..") 
						 ){
					flag = false;
				}
			}
		}
		if(flag){
			chain.doFilter(xssRequest, response);
		}else{
			System.out.println(flag+"--flag--");
		}
	}

	@Override
	public void destroy() {
	}

	
	
	
}