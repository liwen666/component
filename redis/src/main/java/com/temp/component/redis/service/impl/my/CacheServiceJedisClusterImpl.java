package com.temp.component.redis.service.impl.my;

import com.temp.component.redis.RedisUtils;
import com.temp.component.redis.bean.ProcessInstanceCache;
import com.temp.component.redis.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.io.IOException;
import java.util.*;

//@Service
public class CacheServiceJedisClusterImpl implements CacheService {
    private int batchSendCount = 50000;
    private static Logger LOGGER = LoggerFactory.getLogger(CacheServiceJedisClusterImpl.class);

    private static JedisCluster jedisCluster;
    private static TreeMap<Long, String> slotHostMap = null;
    private static Map<String, JedisPool> nodeMap = null;


    @Override
    public long cacheMembershipUser(String userIdKey, String groupIdValue) {
        Long sadd = jedisCluster.sadd(RedisUtils.getSetKey(userIdKey), groupIdValue);
        return sadd;
    }

    @Override
    public long cacheMembershipUser(String userIdKey, List<String> groupIdListValue) {
        long sendResult = 0L;
        List<List<String>> lists = RedisUtils.byCountAssign(groupIdListValue, batchSendCount);
        for (List<String> l : lists) {
            String[] groupIds = (String[]) RedisUtils.toArray(l,new String [0]);
            Long sadd = jedisCluster.sadd(RedisUtils.getSetKey(userIdKey), groupIds);
            sendResult += sadd;
        }
        return sendResult;
    }

    @Override
    public long cacheMembershipGroup(String groupIdKey, String userIdValue) {
        Long sadd = jedisCluster.sadd(RedisUtils.getSetKey(groupIdKey), userIdValue);
        return sadd;
    }

    @Override
    public long cacheMembershipGroup(String groupIdKey, List<String> userIdListValue) {
        long sendResult = 0L;
        List<List<String>> lists = RedisUtils.byCountAssign(userIdListValue, batchSendCount);
        for (List<String> l : lists) {
            String[] userIds =  RedisUtils.toArray(l,new String[0]);
            Long sadd = jedisCluster.sadd(RedisUtils.getSetKey(groupIdKey), userIds);
            sendResult += sadd;
        }
        return sendResult;
    }

    @Override
    public long cacheProcessInstance(ProcessInstanceCache processInstanceCache) {
        return 0;
    }

    @Override
    public long cacheProcessInstance(List<ProcessInstanceCache> processInstanceCacheList) throws IOException {
        return 0;
    }

    public long cacheProcessInstance(String ticketIdKey, ProcessInstanceCache processInstanceCacheValue) {

        String isOK = null;
        try {
            isOK = jedisCluster.hmset(RedisUtils.getHashKey(ticketIdKey, processInstanceCacheValue.getBpmnType()), RedisUtils.objectToMap(processInstanceCacheValue));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return isOK.equals("OK") ? 1 : 0;
    }

    @Autowired(required = true)
    public void setJedisCluster(JedisCluster jedisCluster) {
        CacheServiceJedisClusterImpl.jedisCluster = jedisCluster;
        nodeMap = jedisCluster.getClusterNodes();
        String anyHost = nodeMap.keySet().iterator().next();
        slotHostMap = RedisUtils.getSlotHostMap(anyHost);
    }

    public long cacheProcessInstance(Map<String, ProcessInstanceCache> processInstanceCacheMap) throws IOException {
        long count = 0L;
        Set<Map.Entry<String, ProcessInstanceCache>> entries = processInstanceCacheMap.entrySet();
        Map<String, Map<String, ProcessInstanceCache>> hostPortAndMapProcessIns = new HashMap<String, Map<String, ProcessInstanceCache>>();
        Iterator<Map.Entry<Long, String>> iterator = slotHostMap.entrySet().iterator();
        while (iterator.hasNext()) {
            String hostAndPort = iterator.next().getValue();
            hostPortAndMapProcessIns.put(hostAndPort, new HashMap<String, ProcessInstanceCache>());
        }

        for (Map.Entry<String, ProcessInstanceCache> proceInsMap : entries) {
            //获取槽号
            int slot = JedisClusterCRC16.getSlot(RedisUtils.getHashKey( proceInsMap.getKey(), proceInsMap.getValue().getBpmnType()));
            if (slot == 0) slot = 1;
            String hostPort = null;
            try {
                hostPort = slotHostMap.lowerEntry(Long.valueOf(slot)).getValue();
                //将数据放到相应的map下
                hostPortAndMapProcessIns.get(hostPort).put(proceInsMap.getKey(), proceInsMap.getValue());
            } catch (Exception e) {
                LOGGER.error("缓存数据解析失败  KEY是：{}  solt 是{}  ip端口是 {}", proceInsMap.getKey(), slot, hostPort);
            }
        }

        for (Map.Entry<String, Map<String, ProcessInstanceCache>> mapProcessIns : hostPortAndMapProcessIns.entrySet()) {
            Map<String, ProcessInstanceCache> value = mapProcessIns.getValue();
            Jedis jedis = nodeMap.get(mapProcessIns.getKey()).getResource();
            long stageCount = clusterCacheProcessInstance(jedis, value);
            count += stageCount;
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("缓存实例数量是：{}", count);
        }
        return count;
    }

    private long clusterCacheProcessInstance(Jedis jedis, Map<String, ProcessInstanceCache> processInstanceCacheMap) throws IOException {
        Pipeline pipelined = jedis.pipelined();
        long count = 0L;
        Set<Map.Entry<String, ProcessInstanceCache>> entries = processInstanceCacheMap.entrySet();
        for (Map.Entry<String, ProcessInstanceCache> m : entries) {
            try {
                pipelined.hmset(RedisUtils.getHashKey(m.getKey(), m.getValue().getBpmnType()), RedisUtils.objectToMap(m.getValue()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (count % batchSendCount == 0) {
                pipelined.sync();
            }
            count++;
        }
        pipelined.sync();
        pipelined.close();
        jedis.close();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("cluster分片缓存实例数量是：{}  hostPort---------->{}", count, jedis.getClient().getHost() + ":" + jedis.getClient().getPort());
        }
        return count;
    }



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
        Set<String> sGroupMembers = jedisCluster.smembers(RedisUtils.getSetKey(userIdKey));
        return new ArrayList<String>(sGroupMembers);
    }

    @Override
    public List<String> listUserIdList(String groupIdKey) {
        Set<String> sUserMembers = jedisCluster.smembers(RedisUtils.getSetKey(groupIdKey));
        return new ArrayList<String>(sUserMembers);
    }

    @Override
    public ProcessInstanceCache getProcessInstanceCache(String ticketIdKey, String bpmnTypeKey) {
        Map<String, String> map = jedisCluster.hgetAll(RedisUtils.getHashKey(ticketIdKey, bpmnTypeKey));
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
        return 0;
    }

    @Override
    public long removeMembershipUserCache(List<String> userIdKeyList) {
        return 0;
    }

    @Override
    public long removeMembershipGroupCache(String groupIdKey) {
        return 0;
    }

    @Override
    public long removeMembershipGroupCache(List<String> groupIdKeyList) {
        return 0;
    }

    @Override
    public long removeProcessInstanceCache(String ticketId, String bpmnType) {
        return 0;
    }

    @Override
    public long removeProcessInstanceCache(Map<String, List<String>> bpmnTypeTicketIdListMap) {
        return 0;
    }


}
