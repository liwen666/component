package com.temp.common.base.cglib;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class PropertiesThreadLocalHolder {
    private final static ThreadLocal<Map<String, String>> localProperties = new ThreadLocal<>();

    public static void addProperties(String key, String value) {
        if (null == localProperties.get()) {
            localProperties.set(new HashMap<>());
        }
        localProperties.get().put(key, value);
    }

    public static Map<String, String> get() {
        return localProperties.get();
    }

    public static String getProperties(String key) {
        return localProperties.get().get(key);
    }

    public static void remove(String key) {
        localProperties.get().remove(key);
    }
}