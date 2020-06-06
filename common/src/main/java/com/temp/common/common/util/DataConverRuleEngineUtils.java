package com.temp.common.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  描述
 * </p>
 *
 * @author lw
 * @since  2020/5/29 16:20
 */

public class DataConverRuleEngineUtils {
    public static Logger logger = LoggerFactory.getLogger(DataConverRuleEngineUtils.class);

    public static Object getTableProperty(Map<?, ?> map, Object qualifiedKey) {
        if (String.valueOf(qualifiedKey).contains(".")) {
            String json = (String) map.get(String.valueOf(qualifiedKey).substring(0, String.valueOf(qualifiedKey).indexOf(".")));
            return getProperty(JSON.parseObject(json), String.valueOf(qualifiedKey).substring(String.valueOf(qualifiedKey).indexOf(".") + 1));
        }
        return getProperty(map, qualifiedKey);
    }

    public static Object getProperty(Map<?, ?> map, Object qualifiedKey) {
        if (map != null && !map.isEmpty() && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!"".equals(input)) {
                if (input.contains(".")) {
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    if (map.get(left) instanceof Map) {
                        return getProperty((Map<?, ?>) map.get(left), right);
                    } else {
                        JSONArray jsonArray = (JSONArray) map.get(left);
                        if (jsonArray == null) return null;
                        ArrayList<Object> objects = new ArrayList<>();
                        for (Object jsonObject : jsonArray) {
                            Object property = getProperty((Map<?, ?>) jsonObject, right);
                            if (null != property) {
                                objects.add(property);
                            }
                        }
                        return objects;
                    }
                } else if (map.containsKey(input)) {
                    return map.get(input);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void setProperty(Map map, Object qualifiedKey, Object value) {
        if (map != null && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!input.equals("")) {
                if (input.contains(".")) {
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    if (map.get(left) == null) {
                        map.put(left, new HashMap<>());
                    } else {

                    }
                    setProperty((Map<?, ?>) map.get(left), right, value);
                } else {
                    ((Map<Object, Object>) map).put(qualifiedKey, value);
                }
            }
        }
    }

    public static void setProperty(Map map, Object qualifiedKey, Object value, Map<String, String> keyVaule, boolean replacePre) {
        Object property = getProperty(map, qualifiedKey);
        if (property == null) {
            return;
        }
        if (property instanceof String && StringUtils.isEmpty((String) property)) {
            return;
        }
        if (property instanceof List) {
            JSONArray jsonArray;
            Object property2 = getProperty(map, String.valueOf(qualifiedKey).substring(0, String.valueOf(qualifiedKey).lastIndexOf(".")));
            if (property2 instanceof ArrayList) {
                jsonArray = (JSONArray) (((List) property2).get(0));
            } else {
                jsonArray = (JSONArray) property2;
            }
            for (Object jsonObject : jsonArray) {
                String subCode = String.valueOf(qualifiedKey).substring(String.valueOf(qualifiedKey).lastIndexOf(".") + 1);
                Object property1 = getProperty((Map<?, ?>) jsonObject, subCode);
                if (null != property1) {
                    if (replacePre) {
                        value = keyVaule.get(String.valueOf(property1).replaceAll("[a-z]", ""));
                    } else {
                        value = keyVaule.get(property1);
                    }
                    setProperty((Map) jsonObject, subCode, value);
                }
            }
        } else {
            boolean listFlag = false;
            if (String.valueOf(property).contains("]")) {
                listFlag = true;
            }
            String s = String.valueOf(property).replaceAll("\\[", "").replaceAll("]", "");
            if (null != keyVaule) {
                value = new StringBuffer("");
                String[] split = s.split(",");
                for (String s1 : split) {
                    String s2 = null;
                    if (replacePre) {
                        s2 = keyVaule.get(s1.replaceAll("[a-z]", ""));
                    } else {
                        s2 = keyVaule.get(s1);
                    }
                    if (null != s2) {
                        ((StringBuffer) value).append(s2 + ",");
                    } else {
                        logger.error("数据转换未查询到对应key的值 key:{}", s1);
                    }
                }
            }
            if (listFlag) {
                setProperty(map, qualifiedKey, "["+value.toString().substring(0, value.toString().length() - 1)+"]");

            } else {
                setProperty(map, qualifiedKey, value.toString().substring(0, value.toString().length() - 1));
            }
        }
    }

    public static void setPropertyTable(Map map, Object qualifiedKey, Object value, Map<String, String> keyVaule, boolean replacePre) {
        if (String.valueOf(qualifiedKey).contains(".")) {
            String left = String.valueOf(qualifiedKey).substring(0, String.valueOf(qualifiedKey).indexOf("."));
            String right = String.valueOf(qualifiedKey).substring(String.valueOf(qualifiedKey).indexOf(".") + 1);
            String json = (String) map.get(left);
            if (StringUtils.isEmpty(json)) {
                return;
            }
            JSONObject jsonObject = JSON.parseObject(json);
            setProperty(jsonObject, right, value, keyVaule, replacePre);
            map.put(left, JSONObject.toJSONString(jsonObject));
            return;
        }
        setProperty(map, qualifiedKey, value, keyVaule, replacePre);
    }


//    public static String getTableName(Object baseEntity) {
//        Class<?> aClass = baseEntity.getClass();
//        Annotation[] annotations = aClass.getAnnotations();
//        for (Annotation annotation : annotations) {
//            if (annotation.annotationType().getName().equals("javax.persistence.Table")) {
//                String name = ((Table) annotation).name();
//                return name;
//            }
//        }
//        return null;
//    }
}
