package com.temp.common.common.util;

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
    private final static ThreadLocal<Map<String, Object>> localProperties = new ThreadLocal<>();

    public static void addProperties(String key, Object object) {
        if (null == localProperties.get()) {
            localProperties.set(new HashMap<>());
        }
        localProperties.get().put(key, object);
    }

    public static Map<String, Object> get() {
        return localProperties.get();
    }

    public static Object getProperties(String key) {
        return localProperties.get().get(key);
    }

    public static void remove(String key) {
        localProperties.get().remove(key);
    }
}