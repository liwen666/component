package com.temp.common.java8.stream.map;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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


}