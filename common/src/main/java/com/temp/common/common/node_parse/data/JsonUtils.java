package com.temp.common.common.node_parse.data;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * json转换
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象转换为JSON字符串。
     *
     * @param object Object
     * @return String
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("对象转换为json时出错", e);
        }
        return "{json parsing error!}";
    }

    /**
     * json转对象
     *
     * @param data
     * @param type
     * @return
     */
    public static <V> V fromJson(String data, Class<V> type) {
        try {
            if ((data != null) && (data.length() > 0)) {
                V v = objectMapper.readValue(data, type);
                return v;
            }
        } catch (Exception e) {
            logger.error("fromJson", e);
        }
        return null;
    }

    /**
     * json转对象列表
     *
     * @param data
     * @param type
     * @return
     */
    public static <V> List<V> toList(String data, Class<V> type) {
        if ((data != null) && !"".equals(data.trim())) {
            return (List<V>) JSONArray.parseArray(data, type);
        }
        return null;
    }


}
