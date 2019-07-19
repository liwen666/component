//package com.temp.component.redis.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.Pipeline;
//import redis.clients.jedis.Response;
//import vip.dcpay.schedule.RunApplication;
//import vip.dcpay.schedule.domain.thread.CommissionTaskExecute;
//import vip.dcpay.schedule.infrastructure.util.RedisUtil;
//
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicReference;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = RunApplication.class)
//public class ScheduleRedisRepositoryTest {
//    @Autowired
//    private RedisUtil redisUtil;
//
//    private AtomicReference<String> userId = new AtomicReference<>();
//
//    @Test
//    public void getOnlineUser() {
//        Set<String> userIds = new HashSet<>();
//        Jedis jedis = null;
//        long l = System.currentTimeMillis();
//
//        try {
//            jedis=redisUtil.getResource();
//            Set<String> keys = jedis.keys("mp:ur:*");
//            Map<String ,Map<String,String>> alLUserMap = new HashMap<>(3000);
//            Map<String, Response<Map<String, String>>> responseHashMap= new HashMap<>();
//            Pipeline pipelined  = jedis.pipelined();
//            for(String key:keys){
//                responseHashMap.put(key, pipelined.hgetAll(key));
//            }
//            pipelined.sync();
//            System.out.println(JSON.toJSONString(responseHashMap));
//            /* 由Response对象获取对应的值 */
//            Map<String,String> result = null;
//            System.out.println(responseHashMap.keySet().size()+"=================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//            for (String key : responseHashMap.keySet()) {
//                result = responseHashMap.get(key).get();
//                if (result != null) {
//                    alLUserMap.put(key, result);
//                }
//            }
//            pipelined.sync();
//            pipelined.close();
//            System.out.println(JSON.toJSONString(alLUserMap)+"=================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//            System.out.println("查询所有数据耗时："+String.valueOf(System.currentTimeMillis()-l));
//            for(Map.Entry<String, Map<String, String>> me:alLUserMap.entrySet()){
//                Map<String, String> value = me.getValue();
//               for(Map.Entry<String,String> v:value.entrySet()){
//                   JSONObject obj = JSONObject.parseObject(v.getValue());
//                   if ((boolean)obj.get("online"))
//                       userIds.add(me.getKey().split(":")[2]);
//               }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            redisUtil.returnBrokenResource(jedis);
//        }finally {
//            redisUtil.returnResource(jedis);
//        }
//        System.out.println(userIds);
//        System.out.println(String.valueOf(System.currentTimeMillis()-l)+"耗时");
//    }
//
//
//    @Test
//    public void getOnlineUsers() {
//        Set<String> userIds = new HashSet<>();
//        Jedis jedis = null;
//        long l = System.currentTimeMillis();
//
//        try {
//            jedis=redisUtil.getResource();
//
//            Set<String> keys = jedis.keys("mp:ur:*");
//            Map<String, String> stringStringMap = jedis.hgetAll("mp:ur:593879015042498560_0");
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ JSON.toJSONString(stringStringMap)+"-----------------------------");
//            System.out.println(keys.size());
//            System.out.println(JSON.toJSONString("keys----->"+keys));
//
//            for(String key:jedis.keys("mp:ur:*")){
//                Map<String, String> stringStringMap1 = jedis.hgetAll(key);
//                for(Map.Entry <String,String>me:stringStringMap1.entrySet()){
//                    String deviceId = me.getValue();
//                    JSONObject obj = null;
//                           obj= JSONObject.parseObject(deviceId);
//                    if (obj.getBoolean("online"))
//                        userIds.add(key.split(":")[2]);
//                }
//            }
//
//
////            for (String key : jedis.keys("mp:ur:*")) {
////                for (String ke : jedis.hkeys(key)) {
////                    List<String> devices = jedis.hmget(key, ke);
////                    if (devices != null && devices.size() > 0) {
////                        for (String device : devices) {
////                            JSONObject obj = JSONObject.parseObject(device);
////                            if (obj.getBoolean("online"))
////                                userIds.add(key.split(":")[2]);
////                        }
////                    }
////                }
////            }
//        }catch (Exception e){
//            e.printStackTrace();
//            redisUtil.returnBrokenResource(jedis);
//        }finally {
//            redisUtil.returnResource(jedis);
//        }
//        System.out.println(userIds);
//        System.out.println(String.valueOf(System.currentTimeMillis()-l)+"耗时");
//    }
//
//    @Test
//    public void name() {
//        System.out.println(11111/5);
//    }
//
//    @Test
//    public void getOnlineUsersByMultipleThread() {
//        Set<String> userIds = new HashSet<>();
//        Jedis jedis = null;
//        long l = System.currentTimeMillis();
//
//        try {
//            jedis=redisUtil.getResource();
//            Set<String> keys = jedis.keys("mp:ur:*");
//            Map<String, String> stringStringMap = jedis.hgetAll("mp:ur:593879015042498560_0");
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ JSON.toJSONString(stringStringMap)+"-----------------------------");
//            System.out.println();
//            System.out.println(JSON.toJSONString(keys));
//            System.out.println(keys.size());
//            List<String>keyList = new ArrayList<>(keys);
//            List<List<String>>keyLists = byCountAssign(keyList, 200);
//            System.out.println(JSON.toJSONString(keyLists));
//            ExecutorService exec = Executors.newFixedThreadPool(10);
//            CommissionTaskExecute taskExecute = new CommissionTaskExecute(keyLists,jedis);
//            for(int i=0;i<10;i++){
//                exec.submit(taskExecute);
//            }
//            System.out.println("--------------------------------------------------------------");
//            CommissionTaskExecute.end.await();
//            List<String> userIds1 = taskExecute.getUserIds();
//            System.out.println(taskExecute.getIntegerAtomicReference());
//            System.out.println(userIds1);
////            taskExecute.getUserIdSet();
//            for (String key : jedis.keys("mp:ur:*")) {
//                for (String ke : jedis.hkeys(key)) {
//                    List<String> devices = jedis.hmget(key, ke);
//                    if (devices != null && devices.size() > 0) {
//                        for (String device : devices) {
//                            JSONObject obj = JSONObject.parseObject(device);
//                            if (obj.getBoolean("online"))
//                                ScheduleRedisRepositoryTest.addUserId(userIds,key.split(":")[2]);
//                        }
//                    }
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            redisUtil.returnBrokenResource(jedis);
//        }finally {
//            redisUtil.returnResource(jedis);
//        }
//        System.out.println(userIds);
//        System.out.println(String.valueOf(System.currentTimeMillis()-l)+"耗时");
//    }
//    public static synchronized void addUserId(Set <String> list,String userId){
//            list.add(userId);
//    }
//
//
//    /**
//     * 将一个list均分成n个list,主要通过偏移量来实现的
//     *
//     * @param source
//     * @return
//     */
//    public static <T> List<List<T>> byCountAssign(List<T> source, int n) {
//        List<List<T>> result = new ArrayList<List<T>>();
//        int number = source.size() / n;  //然后是商
//        int offset = 0;//偏移量
//        for (int i = 0; i < number + 1; i++) {
//            List<T> val = null;
//            if (number > 0) {
//                if (i == number) {
//                    val = source.subList(i * n, source.size());
//                } else {
//                    val = source.subList(i * n, (i + 1) * n);
//                }
//            } else {
//                val = source.subList(i * n, source.size());
//            }
//            result.add(val);
//        }
//        return result;
//    }
//
//
//}