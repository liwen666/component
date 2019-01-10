package com.temp.springcloud.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/page")
public class PageController {
    @RequestMapping(value = "/index")
    public String findPage(){
        System.out.println("访问mvc配置页面");
        return "page";
    }
}
