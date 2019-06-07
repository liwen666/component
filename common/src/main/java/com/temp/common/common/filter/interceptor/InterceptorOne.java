package com.temp.common.common.filter.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <mvc:interceptors>
 <mvc:interceptor>
 <mvc:mapping path="/**.in"/>
 <bean class="com.fportal.login.config.InterceptorOne">
 </bean>
 </mvc:interceptor>
 </mvc:interceptors>

 拦截器 preHandle返回 false之后就不会继续 跳转到controller  可以实现自定义页面跳转功能
 舍弃 dispatcherServlet 的功能  防止多次forward  错误
 */
public class InterceptorOne implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

		//获取到目标方法对象
//		HandlerMethod method = (HandlerMethod) o;
		//取到方法上的注解
//		IncludeInterceptor methodAnnotation = method.getMethodAnnotation(IncludeInterceptor.class);
//		if (methodAnnotation!=null) {
//			//无此注解的一律拦截
////			httpServletResponse.sendRedirect("http://localhost:8080/html/interceptor.html");
//			return true;
//		}
		httpServletRequest.getRequestDispatcher("FOALogin").forward(httpServletRequest,httpServletResponse);
		System.out.println("Interceptor_preHandle_One");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

		System.out.println("Interceptor_postHandle_One");
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

		System.out.println("Interceptor_afterCompletion_One");
	}
}
