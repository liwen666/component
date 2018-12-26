/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.temp.component.sys.web;

import com.temp.common.common.web.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 登录Controller
 * @version 2013-5-31
 */
@Controller
public class LoginController extends AbstractBaseController {
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	

	/**
	 * 管理登录
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		System.out.println("登录成功！");
		logger.debug("debug  tx");
		logger.info("info  tx");
		logger.warn("warn  tx");
		logger.error("error  tx");
		return "login";
	}
}
