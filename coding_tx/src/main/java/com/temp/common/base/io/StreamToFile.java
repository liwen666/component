package com.temp.common.base.io;

import com.temp.common.base.sqlscript.controller.SqlFileExecutorControllerForJar;
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


}
