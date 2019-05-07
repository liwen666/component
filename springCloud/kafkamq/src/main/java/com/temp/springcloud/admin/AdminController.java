package com.temp.springcloud.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class AdminController {
    private static final Logger logger= LoggerFactory.getLogger(AdminController.class);
    @RequestMapping("/index")
    public String homePage(){
        logger.info("登录成功！");

        return "home";
    }
}
