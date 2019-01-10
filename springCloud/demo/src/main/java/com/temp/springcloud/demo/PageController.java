package com.temp.springcloud.demo;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/page")
public class PageController {
    @RequestMapping(value = "/index")
    public String findPage(Model model){
        System.out.println("访问mvc配置页面");
        System.out.println("文件路径-------"+this.getClass().getResource("").getPath());
        model.addAttribute("name","xiao");
        return "index";
    }
    @RequestMapping(value="/page")
    public String page(Model model){
        return "jsp/page";
    }
}
