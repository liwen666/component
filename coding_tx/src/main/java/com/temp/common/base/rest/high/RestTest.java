package com.temp.common.base.rest.high;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        List body = hqbpmn.getBody();
        System.out.println(hqbpmn);

        int i = 0;
//        for (Object b : body) {
//            BpmnTemplateDef bpmnTemplateDef = transitionObjToTemp(b);
//            i++;
//        }
        System.out.println(i);
//        ResponseEntity<List> listResponseEntity = restTemplate.postForEntity("http://localhost:8080/hqbpmn/hqbpmn/bpmnTemplateRest/category.do", new HashMap<String, String>() {{
//            put("appId", "habpmn");
//        }}, List.class);
//        System.out.println(listResponseEntity);


    }
//    private static BpmnTemplateDef transitionObjToTemp(Object b)  {
//        BpmnTemplateDef temp = new BpmnTemplateDef();
//        LinkedHashMap<String ,Object> data = (LinkedHashMap<String, Object>) b;
//        Field[] declaredFields = temp.getClass().getDeclaredFields();
//        for (String key : data.keySet()) {
//            for (Field ftemp : declaredFields) {
//                ftemp.setAccessible(true);
//                if (ftemp.getName().equals(key)) {
//                    try {
//
//                        if (ftemp.getType().getName().equals("java.util.Date")) {
//                            long time = data.get(key)==null?0:(long)data.get(key);
//                            ftemp.set(temp, time==0?null:new Date(time));
//                            continue;
//                        }
//                        if(key.equals("contentBytes")){
////                            System.out.println(new String (data.get(key).toString().getBytes(),"gbk"));
//                            ftemp.set(temp, Base64Utils.decodeFromString((String) data.get(key)));
//                            continue;
//                        }
//                        ftemp.set(temp, data.get(key));
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }
//        return temp;
//    }
}
