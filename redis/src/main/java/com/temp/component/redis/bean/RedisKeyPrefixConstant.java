package com.temp.component.redis.bean;

/**
 * redis key prefix
 *
 * @author Administrator
 * @date 2018/12/10 15:06
 */
public class RedisKeyPrefixConstant {


    public static final String REDIS_SPLIT = ":";

    /**
     * 每个用户所对组set集合
     * key=prefix+userId
     */
    public static final String MEMBERSHIP_USER = "membership:user:";

    /**
     * 每个组所包含用户set集合
     * key=prefix+groupId
     */
    public static final String MEMBERSHIP_GROUP = "membership:group:";

    /**
     * 流程实例信息hash对象
     * key=prefix+bpmnType+ticketId
     */
    public static final String PROCESS_INSTANCE = "pi:";


}
