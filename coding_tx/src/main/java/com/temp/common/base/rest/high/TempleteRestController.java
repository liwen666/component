//package com.temp.common.base.rest.high;
//
//import com.hq.bpmn.common.util.WfBeanFactory;
//import com.hq.bpmn.templatedef.bean.BpmnTemplateDef;
//import com.hq.bpmn.templatedef.dao.BpmnTemplateDefDao;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/**/bpmnTemplateRest")
//public class TempleteRestController {
//
//
//
//	/**
//	 * 查询业务系统对应的模板
//	 */
//	@RequestMapping(value = "/selectTemplate",method = RequestMethod.POST)
//	@ResponseBody
//	public List<BpmnTemplateDef> initBpmnTemplateDef( @RequestBody Map<String, Object> param) {
//		ApplicationContext context = WfBeanFactory.getContext();
//		BpmnTemplateDefDao tempDao = (BpmnTemplateDefDao) context.getBean("bpmnTemplateDefDao");
//		List<BpmnTemplateDef> selectTemplateDef = tempDao.selectTemplateDefAllByAppId((String) param.get("appId"));
//		return selectTemplateDef;
//	}
//	/**
//	 * 查询业务系统对应的模板
//	 */
//	@RequestMapping(value = "/test/{appId}",method = RequestMethod.GET, produces = "application/json;charset=utf-8")
//	@ResponseBody
//	public List<BpmnTemplateDef> test(@PathVariable String appId) {
//		ApplicationContext context = WfBeanFactory.getContext();
//		BpmnTemplateDefDao tempDao = (BpmnTemplateDefDao) context.getBean("bpmnTemplateDefDao");
//		List<BpmnTemplateDef> selectTemplateDef = tempDao.selectTemplateDefAllByAppId(appId);
//		return selectTemplateDef;
//	}
//	@RequestMapping(value = "/category", method = RequestMethod.POST)
//	@ResponseBody
//	public List saveTemplateCategory(@RequestBody Map<String, Object> map) {
//		System.out.println(map);
//		return new ArrayList(){{add(1);}};
//	}
//
//}
