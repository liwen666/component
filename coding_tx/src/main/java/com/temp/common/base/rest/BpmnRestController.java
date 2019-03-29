//package com.temp.common.base.rest;
//
//import com.hq.bpmn.common.bean.ProcessResult;
//import com.hq.bpmn.common.util.JsonUtil;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Controller
//@RequestMapping("/**/bpmnRest")
//public class BpmnRestController {
//
//
//	/**
//	 * 发送日志信息到服务器
//	 */
//	@RequestMapping(value = "/sendLogeData/{data}",method = RequestMethod.GET)
//	@ResponseBody
//	public ProcessResult<String> sendLogeData(@PathVariable("data") String data, HttpServletRequest request) {
//		System.out.println(data);
//		ProcessResult<String> pr = new ProcessResult<>();
//		pr.setMessage("发送成功！");
//				return pr;
//	}
//	@RequestMapping(value = "/sendLogeData/test",method = RequestMethod.GET)
//	@ResponseBody
//	public ProcessResult<String> test( String data, HttpServletRequest request) {
//		System.out.println("0000000000000");
//		ProcessResult<String> pr = new ProcessResult<>();
//		pr.setMessage("发送成功！");
//				return pr;
//	}
//	/**
//	 * 发送日志信息到服务器
//	 */
//	@RequestMapping(value = "/sendLogeData/{data}/{name}",method = RequestMethod.GET)
//	public void sendLogeDataAndVal(@PathVariable("data") String data, @PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response) {
//		System.out.println(data);
//		System.out.println(name);
//		ProcessResult<String> pr = new ProcessResult<>();
//		pr.setMessage("发送成功！");
//		JsonUtil.outPutPRToJSON(pr, response);
//	}
//
//}
