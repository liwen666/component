package com.temp.component.redis.service;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;


public class GetMultiRecordWithPipelining {

    public static void main(String[] args) {
        // 连接池
        JedisPool jedisPool = new JedisPool("192.168.1.9", 6379);
        
        /* 操作Redis */
        Jedis jedis = null;
        Map<String, Response<String>> map = new HashMap<String, Response<String>>();
        try {
            jedis = jedisPool.getResource();
            
//            TimeLag t = new TimeLag(); // 开始计算时间
            
            Pipeline p = jedis.pipelined();
            /* 插入多条数据 */
            for(Integer i = 0; i < 100000; i++) {
                if (i % 2 == 1) {
                    map.put(i.toString(), p.get(i.toString()));
                }
            }
            p.sync();
            
            /* 由Response对象获取对应的值 */
            Map<String, String> resultMap = new HashMap<String, String>();
            String result = null;
            for (String key : map.keySet()) {
                result = map.get(key).get();
                if (result != null && result.length() > 0) {
                    resultMap.put(key, result);
                }
            }
            System.out.println("get record num : " + resultMap.size());
            
//            System.out.println(t.cost()); // 计时结束
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        
    }
}