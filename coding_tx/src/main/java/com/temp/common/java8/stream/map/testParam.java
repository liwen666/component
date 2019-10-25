package com.temp.common.java8.stream.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public  class testParam {
    @org.junit.Test
    public  void TEST() {
        Map m = new HashMap();
        setm(m);
        Map<Long, Map<String, BigDecimal>> merchantOrderMap = new HashMap<>();
        setmmm(merchantOrderMap);
        System.out.println(JSON.toJSONString(m));
        System.out.println("=========================================================================================");
        System.out.println(JSON.toJSONString(merchantOrderMap));
    }

    private  void setmmm(Map<Long, Map<String, BigDecimal>> merchantOrderMap) {
        merchantOrderMap.put(1l,new HashMap(){{put("aaa",new BigDecimal(111));}});

    }

    private static void setm(Map m) {
        m.put("aaa","bbb");
    }


    @Test
    public void List() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        System.out.println(list);


        List<String> collect = list.stream().map(string -> {
            return "" + string;
        }).collect(Collectors.toList());
        System.out.println(collect);
//        list.stream().collect(e->)


    }

    @Test
    public void fastJson() {
        String obj = "{1513:{\"operates\":[],\"resourceId\":1513,\"viewContents\":[0],\"viewScope\":[1909]},1515:{\"operates\":[],\"resourceId\":1515,\"viewContents\":[0],\"viewScope\":[1909]},924:{\"operates\":[],\"resourceId\":924,\"viewContents\":[0],\"viewScope\":[1909]}}";
        JSONObject jsonObject = JSONObject.parseObject(obj);
        Map map = jsonObject.toJavaObject(Map.class);
//        Map map = JSON.parseObject(obj, Map.class);
        Set<Integer> set = map.keySet();
        for(Integer integer:set){
            System.out.println(integer);
        }
        Object o = map.get(1513);
        AuthScope authScopes = JSON.parseObject(o.toString(), AuthScope.class);

    }
}