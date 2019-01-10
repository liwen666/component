package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page")
public class PageController {
    @RequestMapping(value = "/index")
    public String findPage(Model model){
        System.out.println("访问mvc配置页面");
        model.addAttribute("name","xiao");
        return "index";
    }
    @RequestMapping(value="/page")
    public String page(Model model){
        return "jsp/page";
    }
}
