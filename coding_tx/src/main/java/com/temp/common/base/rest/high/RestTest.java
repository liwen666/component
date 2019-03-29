package com.temp.common.base.rest.high;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

public class RestTest {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> hqbpmn = restTemplate.getForEntity("http://localhost:8080/hqbpmn/hqbpmn/bpmnTemplateRest/test/hqbpmn.do", String.class);
//        ResponseEntity<List> hqbpmn = restTemplate.postForEntity("http://localhost:8080/hqbpmn/hqbpmn/bpmnTemplateRest/selectTemplate.do", "{\"appId\":\"hqbpmn\"}", List.class);
//        ResponseEntity<List> hqbpmn = restTemplate.postForEntity("http://localhost:8080/hqbpmn/hqbpmn/bpmnTemplateRest/selectTemplate.do", "appId=hqbpmn", List.class);
//        ResponseEntity<List> hqbpmn = restTemplate.postForEntity("http://localhost:8080/hqbpmn/hqbpmn/bpmnTemplateRest/selectTemplate.do", "hqbpmn", List.class);
//
        ResponseEntity<List> hqbpmn = restTemplate.postForEntity("http://localhost:8080/hqbpmn/hqbpmn/bpmnTemplateRest/selectTemplate.do", new HashMap<String, String>() {{
            put("appId", "hqbpmn");
        }}, List.class);
        System.out.println(hqbpmn);
//        ResponseEntity<List> listResponseEntity = restTemplate.postForEntity("http://localhost:8080/hqbpmn/hqbpmn/bpmnTemplateRest/category.do", new HashMap<String, String>() {{
//            put("appId", "habpmn");
//        }}, List.class);
//        System.out.println(listResponseEntity);


    }
}
