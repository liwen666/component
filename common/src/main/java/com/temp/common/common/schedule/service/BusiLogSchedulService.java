package com.temp.common.common.schedule.service;

import com.temp.common.common.schedule.dao.ActBusiLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class BusiLogSchedulService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusiLogSchedulService.class);

//    @Autowired
//    @Qualifier("actBusiLogDao")
//    private ActBusiLogDao actBusiLogDao;


    private RestTemplate restTemplate = new RestTemplate();

    public void execute() throws IOException {
        LOGGER.info("定时器启动。。。");
        System.out.println("000");
    }

    public void execute2() throws IOException {
        LOGGER.info("执行任务。。。");
    }




}
