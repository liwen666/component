package com.temp.component.redis.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRedisService implements RedisService {
    protected abstract RedisTemplate getRedisTemplate();
 
    @Override
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return getRedisTemplate().expire(key,timeout,timeUnit);
    }
 
 
    @Override
   public Map<String,Object> batchQueryByKeys(List<String> keys, Boolean useParallel){
        if(null == keys || keys.size() == 0 ){
            return null;
        }
 
        if(null == useParallel){
            useParallel = true;
        }
 
        List<Object> results = getRedisTemplate().executePipelined(
                new RedisCallback<Object>() {
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
                        for(String key:keys) {
                            stringRedisConn.get(key);
                        }
                        return null;
                    }
                });
        if(null == results || results.size() == 0 ){return null;}
 
        Map<String,Object> resultMap  =  null;
 
        if(useParallel){
 
            Map<String,Object> resultMapOne  = Collections.synchronizedMap(new HashMap<String,Object>());
 
            keys.parallelStream().forEach(t -> {
                resultMapOne.put(t,results.get(keys.indexOf(t)));
            });
 
            resultMap = resultMapOne;
 
        }else{
 
            Map<String,Object> resultMapTwo  = new HashMap<>();
 
            for(String t:keys){
                resultMapTwo.put(t,results.get(keys.indexOf(t)));
            }
 
            resultMap = resultMapTwo;
        }
 
        return  resultMap;
 
    }
 
    @Override
   public List<Object> batchPutInPipelined(Map<String,Object> keyValueMap,long expire){
 
        List<Object> results = getRedisTemplate().executePipelined(
                new RedisCallback<Object>() {
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
                        for(String key:keyValueMap.keySet()) {
                            if(null != keyValueMap.get(key)){
                                stringRedisConn.set(key, keyValueMap.get(key).toString());
                                stringRedisConn.expire(key,expire);
                            }
                        }
                        return null;
                    }
                });
 
        return  results;
 
    }
 
 
    @Override
    public List<MapEntity> batIncByPipelined(List<MapEntity> params){
 
        List<Object> results = getRedisTemplate().executePipelined(
                new RedisCallback<Object>() {
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
                        for(MapEntity entity:params) {
                            if(null != entity){
                                stringRedisConn.incrBy(entity.getKey(), entity.getValue());
                            }
                        }
                        return null;
                    }
                });
 
        List<MapEntity> resultList  = Lists.newArrayList();
 
        for(MapEntity entity:params){
            resultList.add(new MapEntity(entity.getKey(),(Long)results.get(params.indexOf(entity))));
        }
 
 
        return  resultList;
 
    }
}
--------------------- 
作者：代码也文艺 
来源：CSDN 
原文：https://blog.csdn.net/qq_37107280/article/details/84567631 
版权声明：本文为博主原创文章，转载请附上博文链接！