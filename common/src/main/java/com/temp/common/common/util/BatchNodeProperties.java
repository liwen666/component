package com.temp.common.common.util;

import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author tx
 * @since 2019/5/26 23:40
 */
@Validated
public class BatchNodeProperties {
    public static Map<String, String> address = new ConcurrentHashMap<>();
    public static Set<String> errorServer = new HashSet<>();
    public static Set<String> initServers = new HashSet<>();


    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        address.remove("test_node");
        BatchNodeProperties.address = address;
    }

    public  void setInitServers(Set<String> initServers) {
        BatchNodeProperties.initServers = initServers;
    }

    public  Set<String> getInitServers() {
        return initServers;
    }
}
