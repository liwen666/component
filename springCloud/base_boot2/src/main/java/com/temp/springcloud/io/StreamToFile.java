package com.temp.springcloud.io;

import com.temp.springcloud.bean.UserDomain;
import com.temp.springcloud.sqlscript.controller.SqlFileExecutorControllerForJar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.URL;
@RestController
//@RequestMapping(value = "/file")
public class StreamToFile {
    public static void main(String[] args) {
        InputStream resourceAsStream = SqlFileExecutorControllerForJar.class.getResourceAsStream("");
        URL resource = SqlFileExecutorControllerForJar.class.getResource("");
        System.out.println(resource);


    }
  @RequestMapping("/getUser/{name}")
    public UserDomain getUser(@PathVariable(value = "name")String name){
      System.out.println(name);
      URL resource = SqlFileExecutorControllerForJar.class.getResource("");
      System.out.println(resource);
      UserDomain ud = new UserDomain("li","10");
        return ud;
    }


}
