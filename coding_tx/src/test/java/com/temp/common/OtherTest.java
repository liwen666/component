package com.temp.common;

import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class OtherTest {

    @Test
    public void name() {
        Map map = Maps.newConcurrentMap();
        map.put("aaa", 1);
        map.put("bbb", 2);
        map.put("ccc",3);
        map.forEach((k,v)->{
            System.out.println(k);
            if("aaa".equals(k)){
                return;
            }
            System.out.println(v);
        });
    }
}