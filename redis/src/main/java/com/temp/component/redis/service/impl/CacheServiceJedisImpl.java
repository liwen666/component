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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.*;

@Service
public class CacheServiceJedisImpl implements CacheService {
    @Autowired
    private JedisPool jedisPool;
    private int batchSendCount = 50000;
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceJedisImpl.class);

    @Override
    public long cacheMembershipUser(String userIdKey, String groupIdValue) {
        Jedis jedis = jedisPool.getResource();
        Long sadd = jedis.sadd(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER,userIdKey), groupIdValue);
        jedis.close();
        return sadd;
    }

    @Override
    public long cacheMembershipUser(String userIdKey, List<String> groupIdListValue) {
        Jedis jedis = jedisPool.getResource();
        long sendResult = 0L;
        List<List<String>> lists = RedisUtils.byCountAssign(groupIdListValue, batchSendCount);
        for (List<String> l : lists) {
            String[] groupIds = (String[]) RedisUtils.toArray(l, new String[0]);
            Long sadd = jedis.sadd(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER,userIdKey), groupIds);
            sendResult += sadd;
        }
        jedis.close();
        return sendResult;
    }

    @Override
    public long cacheMembershipGroup(String groupIdKey, String userIdValue) {
        Jedis jedis = jedisPool.getResource();
        Long sadd = jedis.sadd(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP,groupIdKey), userIdValue);
        jedis.close();
        return sadd;
    }

    @Override
    public long cacheMembershipGroup(String groupIdKey, List<String> userIdListValue) {
        Jedis jedis = jedisPool.getResource();
        long sendResult = 0L;
        List<List<String>> lists = RedisUtils.byCountAssign(userIdListValue, batchSendCount);
        for (List<String> l : lists) {
            String[] userIds = (String[]) RedisUtils.toArray(l, new String[0]);
            Long sadd = jedis.sadd(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP,groupIdKey), userIds);
            sendResult += sadd;
        }
        jedis.close();
        return sendResult;
    }

    @Override
    public long cacheProcessInstance(ProcessInstanceCache processInstanceCache) {
        Jedis jedis = jedisPool.getResource();
        String isOK = null;
        try {
            isOK = jedis.hmset(RedisUtils.getHashKey(RedisKeyPrefixConstant.PROCESS_INSTANCE,processInstanceCache.getBpmnType(), processInstanceCache.getTicketId()), RedisUtils.objectToMap(processInstanceCache));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        jedis.close();
        return isOK.equals("OK") ? 1 : 0;
    }

    @Override
    public long cacheProcessInstance(List<ProcessInstanceCache> processInstanceCacheList) throws IOException {
        Jedis jedis = jedisPool.getResource();
        Pipeline pipelined = jedis.pipelined();
        long count = 0L;
        Map<String,ProcessInstanceCache> keyValue = new HashMap<String,ProcessInstanceCache>();
        for (ProcessInstanceCache p :processInstanceCacheList){
            keyValue.put(RedisUtils.getHashKey(RedisKeyPrefixConstant.PROCESS_INSTANCE,p.getBpmnType(), p.getTicketId()),p);
        }
        for (Map.Entry<String,ProcessInstanceCache> m : keyValue.entrySet()) {
            String isOK = null;
            try {
                pipelined.hmset( m.getKey(),RedisUtils.objectToMap(m.getValue()));

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
            LOGGER.info("缓存实例数量是：{}", count);
        }
        return count;
    }



    @Override
    public List<String> listGroupIdList(String userIdKey) {
        Jedis jedis = jedisPool.getResource();
        Set<String> sGroupMembers = jedis.smembers(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER,userIdKey));
        jedis.close();
        return new ArrayList<String>(sGroupMembers);
    }

    @Override
    public List<String> listUserIdList(String groupIdKey) {
        Jedis jedis = jedisPool.getResource();
        Set<String> sUserMembers = jedis.smembers(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP,groupIdKey));
        jedis.close();
        return new ArrayList<String>(sUserMembers);
    }

    @Override
    public ProcessInstanceCache getProcessInstanceCache(String ticketIdKey, String bpmn) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map = jedis.hgetAll(RedisUtils.getHashKey(RedisKeyPrefixConstant.PROCESS_INSTANCE,bpmn, ticketIdKey));
        ProcessInstanceCache processInstance = null;
        try {

            processInstance = RedisUtils.mapToObject(map, new ProcessInstanceCache());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        jedis.close();
        return processInstance;
    }

    @Override
    public List<ProcessInstanceCache> listProcessInstanceCache(String ticketIdKey, String bpmnTypeKey) {
        return null;
    }

    List<ProcessInstanceCache> listProcessInstanceCache(String ticketIdKey) {
        return null;
    }

    @Override
    public long removeMembershipUserCache(String userIdKey) {
        Jedis resource = jedisPool.getResource();
        Long del = resource.del(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER, userIdKey));
//        Long srem = resource.srem(RedisKeyPrefixConstant.getRedisSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP,groupIdKey),"22333");
        resource.close();
        return del;
    }

    @Override
    public long removeMembershipUserCache(List<String> userIdKeyList) {
        Jedis resource = jedisPool.getResource();
        List<String> userKeyList = new ArrayList<String>();
        for (String groupId : userIdKeyList) {
            userKeyList.add(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_USER, groupId));
        }
        Long del = resource.del(RedisUtils.toArray(userKeyList, new String[0]));
        resource.close();
        return del;

    }

    @Override
    public long removeMembershipGroupCache(String groupIdKey) {
        Jedis resource = jedisPool.getResource();
        Long del = resource.del(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP, groupIdKey));
//        Long srem = resource.srem(RedisKeyPrefixConstant.getRedisSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP,groupIdKey),"22333");
        resource.close();
        return del;
    }

    @Override
    public long removeMembershipGroupCache(List<String> groupIdKeyList) {
        Jedis resource = jedisPool.getResource();
        List<String> groupKeyList = new ArrayList<String>();
        for (String groupId : groupIdKeyList) {
            groupKeyList.add(RedisUtils.getSetKey(RedisKeyPrefixConstant.MEMBERSHIP_GROUP, groupId));
        }
        Long del = resource.del(RedisUtils.toArray(groupKeyList, new String[0]));
        resource.close();
        return del;
    }

    @Override
    public long removeProcessInstanceCache(String ticketIdKey, String bpmnType) {
        Jedis resource = jedisPool.getResource();
        Long del = resource.del(RedisUtils.getSetKey(RedisKeyPrefixConstant.PROCESS_INSTANCE, ticketIdKey, bpmnType));
        resource.close();
        return del;
    }

    @Override
    public long removeProcessInstanceCache(Map<String, List<String>> bpmnTypeTicketIdsListMap) {
        Jedis resource = jedisPool.getResource();
        Long del = null;
        List<String> proceInsListKeys = new ArrayList<String>();
        for (Map.Entry<String, List<String>> entry : bpmnTypeTicketIdsListMap.entrySet()) {
            List<String> value = entry.getValue();
            String bpmnType = entry.getKey();
            for (String id : value) {
                String proceInsKey = RedisUtils.getHashKey(RedisKeyPrefixConstant.PROCESS_INSTANCE, bpmnType, id);
                proceInsListKeys.add(proceInsKey);
            }
            del = resource.del(RedisUtils.toArray(proceInsListKeys,new String[0]));
        }
        resource.close();
        return del;
    }

}
