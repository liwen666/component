package com.temp.component.redis.service;


import com.temp.component.redis.bean.ProcessInstanceCache;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/12/13 10:17
 */
public interface CacheService {

    // c
    long cacheMembershipUser(String userIdKey, String groupIdValue);

    long cacheMembershipUser(String userIdKey, List<String> groupIdListValue);

    long cacheMembershipGroup(String groupIdKey, String userIdValue);

    long cacheMembershipGroup(String groupIdKey, List<String> userIdListValue);

    long cacheProcessInstance(ProcessInstanceCache processInstanceCache);

    long cacheProcessInstance(List<ProcessInstanceCache> processInstanceCacheList) throws IOException;


    // r
    List<String> listGroupIdList(String userIdKey);

    List<String> listUserIdList(String groupIdKey);

    ProcessInstanceCache getProcessInstanceCache(String ticketId, String bpmnType);

    List<ProcessInstanceCache> listProcessInstanceCache(String ticketId, String bpmnType);

    // u

    // d
    long removeMembershipUserCache(String userIdKey);

    long removeMembershipUserCache(List<String> userIdKeyList);

    long removeMembershipGroupCache(String groupIdKey);

    long removeMembershipGroupCache(List<String> groupIdKeyList);

    long removeProcessInstanceCache(String ticketId, String bpmnType);

    /**
     * 批量清除缓存
     *
     * @param bpmnTypeTicketIdListMap key bpmnType
     *                                value ticketIdList
     */
    long removeProcessInstanceCache(Map<String, List<String>> bpmnTypeTicketIdListMap);


}
