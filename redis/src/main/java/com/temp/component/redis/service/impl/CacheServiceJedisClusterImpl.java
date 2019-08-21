package com.temp.component.redis.service.impl;

import com.temp.component.redis.RedisUtils;
import com.temp.component.redis.bean.ProcessInstanceCache;
import com.temp.component.redis.bean.RedisKeyPrefixConstant;
import com.temp.component.redis.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.*;

//@Service
public class CacheServiceJedisClusterImpl implements CacheService {

    private static Logger LOGGER = LoggerFactory.getLogger(CacheServiceJedisClusterImpl.class);
    private static int REDIS_CLUSTER_BATCH_COUNT = 50000;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public long cacheMembershipUser(String userIdKey, String groupIdValue) {
        return jedisCluster.sadd(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER, userIdKey), groupIdValue);
    }

    @Override
    public long cacheMembershipUser(String userIdKey, List<String> groupIdListValue) {
        long sendResult = 0L;
        List<List<String>> lists = RedisUtils.byCountAssign(groupIdListValue, REDIS_CLUSTER_BATCH_COUNT);
        for (List<String> l : lists) {
            String[] groupIds = RedisUtils.toArray(l, new String[0]);
            sendResult += jedisCluster.sadd(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER, userIdKey), groupIds);
        }
        return sendResult;
    }

    @Override
    public long cacheMembershipGroup(String groupIdKey, String userIdValue) {
        return jedisCluster.sadd(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP, groupIdKey), userIdValue);
    }

    @Override
    public long cacheMembershipGroup(String groupIdKey, List<String> userIdListValue) {
        long sendResult = 0L;
        List<List<String>> lists = RedisUtils.byCountAssign(userIdListValue, REDIS_CLUSTER_BATCH_COUNT);
        for (List<String> l : lists) {
            String[] userIds = RedisUtils.toArray(l, new String[0]);
            sendResult += jedisCluster.sadd(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP, groupIdKey), userIds);
        }
        return sendResult;
    }

    @Override
    public long cacheProcessInstance(ProcessInstanceCache processInstanceCache) {

        String result = "";
        try {
            result = jedisCluster.hmset(RedisUtils.getHashKey(RedisKeyPrefixConstant.PROCESS_INSTANCE, processInstanceCache.getBpmnType(), processInstanceCache.getTicketId()), RedisUtils.objectToMap(processInstanceCache));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result.equals("OK") ? 1 : 0;
    }

    @Override
    public long cacheProcessInstance(List<ProcessInstanceCache> processInstanceCacheList) throws IOException {
//        long count = 0L;
//        Map<JedisPool, Map<String, ProcessInstanceCache>> pipelineBatchDataMapMap = RedisUtils.getPipelineBatchDataMapMap(processInstanceCacheList, jedisCluster);
//        for (JedisPool pool :
//                pipelineBatchDataMapMap.keySet()) {
//            count += batchCacheProcessInstanceCache(pool.getResource(), pipelineBatchDataMapMap.get(pool));
//        }
//        LOGGER.info("cache pi count : {}", count);
//        System.out.println("cache pi count : " + count);
//        return count;
        return 1;
    }

    private long batchCacheProcessInstanceCache(Jedis jedis, Map<String, ProcessInstanceCache> map) {
        long count = 0L;
        Pipeline pipelined = jedis.pipelined();

        Set<String> keySet = map.keySet();

//        long r = 0;
        for (String key :
                keySet) {
            try {
                long s = System.currentTimeMillis();
                Map<String, String> stringStringMap = RedisUtils.objectToMap(map.get(key));
                long e = System.currentTimeMillis();
//                r += (e - s);
                pipelined.hmset(key, stringStringMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            count++;
            if (count % REDIS_CLUSTER_BATCH_COUNT == 0) {
                pipelined.sync();
            }
        }
//        System.out.println("obj2map = " + r);
        pipelined.sync();
        try {
            pipelined.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return count;
    }

    /*private long clusterCacheProcessInstance(Jedis jedis, Map<String, ProcessInstanceCache> processInstanceCacheMap) throws IOException {
        Pipeline pipelined = jedis.pipelined();
        long count = 0L;
        Set<Map.Entry<String, ProcessInstanceCache>> entries = processInstanceCacheMap.entrySet();
        for (Map.Entry<String, ProcessInstanceCache> m : entries) {
            try {
                pipelined.hmset(ticketIdConversionToKey(m.getKey(), m.getValue().getBpmnType()), RedisUtils.objectToMap(m.getValue()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (count % batchSendCount == 0) {
                pipelined.sync();
            }
            count++;
        }
        pipelined.sync();
        if (LOGGER.isDebugEnabled()) {
            pipelined.clear();
            HashMap<String, Response<String>> intrmMap = new HashMap<String, Response<String>>();
            for (Map.Entry<String, ProcessInstanceCache> m : entries) {
                intrmMap.put(ticketIdConversionToKey(m.getKey(), m.getValue().getBpmnType()), pipelined.get(ticketIdConversionToKey(m.getKey(), m.getValue().getBpmnType())));
            }
            pipelined.sync();
            LOGGER.debug("验证数据量--->分片实际缓存实例数量是：{}  hostPort----->{}", intrmMap.size(), jedis.getClient().getHost() + ":" + jedis.getClient().getPort());
        }
        pipelined.close();
        jedis.close();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("cluster分片缓存实例数量是：{}  hostPort---------->{}", count, jedis.getClient().getHost() + ":" + jedis.getClient().getPort());
        }
        return count;
    }*/


    private List<Map<String, String>> getMasterNodeInfo(JedisPool pool) {
        String[] nodeInfos = pool.getResource().clusterNodes().split("\n");
        List<Map<String, String>> masters = new ArrayList<Map<String, String>>();
        for (String s : nodeInfos) {
            Map<String, String> ipAndHostMap = new HashMap<String, String>();
            if (s.contains("master")) {
                String[] split = s.split(" ");
                for (String ipAndHost : split) {
                    if (ipAndHost.contains("@")) {
                        String ipHost = ipAndHost.split("@")[0];
                        ipAndHostMap.put("ip", ipHost.split(":")[0]);
                        ipAndHostMap.put("host", ipHost.split(":")[1]);
                        masters.add(ipAndHostMap);
                    }
                }
            }
        }
        return masters;
    }

    private List<String> getMasterIpHostString(JedisPool pool) {
        String[] nodeInfos = pool.getResource().clusterNodes().split("\n");
        List<String> masters = new ArrayList<String>();
        for (String s : nodeInfos) {
            if (s.contains("master")) {
                String[] split = s.split(" ");
                for (String str : split) {
                    if (str.contains("@")) {
                        masters.add(str.split("@")[0]);
                    }
                }
            }
        }
        return masters;
    }

    @Override
    public List<String> listGroupIdList(String userIdKey) {
        Set<String> groupSet = jedisCluster.smembers(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER, userIdKey));
        return new ArrayList<String>(groupSet);
    }

    @Override
    public List<String> listUserIdList(String groupIdKey) {
        Set<String> userSet = jedisCluster.smembers(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP, groupIdKey));
        return new ArrayList<String>(userSet);
    }

    @Override
    public ProcessInstanceCache getProcessInstanceCache(String ticketIdKey, String bpmnTypeKey) {
        Map<String, String> map = jedisCluster.hgetAll(RedisUtils.getHashKey(RedisKeyPrefixConstant.PROCESS_INSTANCE, bpmnTypeKey, ticketIdKey));
        ProcessInstanceCache processInstance = null;
        try {
            processInstance = RedisUtils.mapToObject(map, new ProcessInstanceCache());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return processInstance;
    }

    @Override
    public List<ProcessInstanceCache> listProcessInstanceCache(String ticketIdKey, String bpmnTypeKey) {
        return null;
    }

    @Override
    public long removeMembershipUserCache(String userIdKey) {
        return jedisCluster.del(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER, userIdKey));
    }

    @Override
    public long removeMembershipUserCache(List<String> userIdKeyList) {

        return this.removeList(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER, ""), userIdKeyList);

        /*List<String> stringKeys = new ArrayList<>();
        Map<String, List<String>> hostPortMapMap = RedisUtils.getHostPortMapMap(stringKeys, slotHostMap);
        for (Map.Entry<String, List<String>> entry : hostPortMapMap.entrySet()) {
            String keys = "";
            for (String str : entry.getValue()) {
                keys += "','" + str;
            }
            String script = "local t1 = {'" + keys + "'} for k,v in pairs(t1) do redis.call('del',v) print(v) end";
            Jedis resource = nodeMap.get(entry.getKey()).getResource();
            try {
                if (!"".equals(keys)) {
                    resource.eval(script);
                }
            } catch (Exception e) {
                LOGGER.error("集群中不存在这样的keys   eval     {}  分片地址是 ：{}", script, entry.getKey());
            }

        }
        return 1;*/
    }

    private long removeList(String prefix, List<String> list) {
//
//        long count = 0L;
//        Map<JedisPool, List<String>> pipelineBatchDataListMap = RedisUtils.getPipelineBatchDataListMap(list, jedisCluster, prefix);
//
//        for (JedisPool pool :
//                pipelineBatchDataListMap.keySet()) {
//            String scriptParam = "";
//            List<String> pipelineBatchDataList = pipelineBatchDataListMap.get(pool);
//            if (pipelineBatchDataList != null) {
//                int size = pipelineBatchDataList.size();
//                if (size > 1) {
//                    scriptParam = pipelineBatchDataList.toString().replaceAll("\\s","").replace(",", "','");
//                    scriptParam = scriptParam.substring(1, scriptParam.length() - 1);
//                } else if (size == 1) {
//                    scriptParam = pipelineBatchDataList.get(0);
//                }
//                String script = "local t1 = {'" + scriptParam + "'} for k,v in pairs(t1) do redis.call('del',v) print(v) end";
//                Jedis jedis = pool.getResource();
//                jedis.eval(script);
//                count += size;
//            }
//        }
//        return count;
        return 1;
    }

    @Override
    public long removeMembershipGroupCache(String groupIdKey) {
        return jedisCluster.del(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP, groupIdKey));
    }

    @Override
    public long removeMembershipGroupCache(List<String> groupIdKeyList) {

        return this.removeList(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP, ""), groupIdKeyList);

        /*List<String> stringKeys = new ArrayList<String>();
        for (String id : groupIdKeyList) {
            if (StringUtils.isEmpty(id)) {
                continue;
            }
            stringKeys.add(RedisKeyPrefixConstant.getRedisSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP, id));
        }
        Map<String, List<String>> hostPortMapMap = RedisUtils.getHostPortMapMap(stringKeys, slotHostMap);
        for (Map.Entry<String, List<String>> entry : hostPortMapMap.entrySet()) {
            String keys = "";
            for (String str : entry.getValue()) {
                keys += "','" + str;
            }
            String script = "local t1 = {'" + keys + "'} for k,v in pairs(t1) do redis.call('del',v) print(v) end";
            String newScript = script.replace("'','", "'");
            //String script = "local t1 = {'s:membership:group:g22222'} for k,v in pairs(t1) do redis.call('del',v) print(v) end";
            Jedis resource = nodeMap.get(entry.getKey()).getResource();
            try {
                if (!"".equals(keys)) {
                    resource.eval(newScript, 0);
                }
            } catch (Exception e) {
                LOGGER.error("集群中不存在这样的keys   eval     {}  分片地址是 ：{}", script, entry.getKey());
            }
        }
        return 1;*/
    }

    @Override
    public long removeProcessInstanceCache(String ticketId, String bpmnType) {
        return jedisCluster.del(RedisUtils.getHashKey(RedisKeyPrefixConstant.PROCESS_INSTANCE, bpmnType, ticketId));
    }

    @Override
    public long removeProcessInstanceCache(Map<String, List<String>> bpmnTypeTicketIdsListMap) {

        long count = 0L;
        for (String bpmnType :
                bpmnTypeTicketIdsListMap.keySet()) {
            count += this.removeList(RedisUtils.getHashKey(RedisKeyPrefixConstant.PROCESS_INSTANCE, bpmnType), bpmnTypeTicketIdsListMap.get(bpmnType));
        }
        return count;
    }

}
