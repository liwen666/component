package com.temp.springcloud.client;
 
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
 
@Controller
/*加载RestTemplate的配置*/
@Configuration
public class TestController {
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
 
	@GetMapping("/router")
	@ResponseBody
	public String router() {
		RestTemplate tpl = getRestTemplate();
		//到注册中心找服务并调用服务
		String json = tpl.getForObject("http://microservice-provider-user/hello/1", String.class);
		return json;
	}
 
}