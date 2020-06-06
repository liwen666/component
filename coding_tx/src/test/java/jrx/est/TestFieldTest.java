package jrx.est;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestFieldTest
{
    @Test
    public void name() {

        String source = "{\"valueConditions\":[{\"default\":true,\"defaultKey\":\"default\",\"expressionUnits\":[],\"groupOrder\":1,\"key\":\"default\"},{\"conditionGroup\":{\"betweenType\":\"LCRO\",\"betweenType2\":\"LCRO\",\"hit\":false,\"id\":360317,\"listItemType\":\"STRING\",\"nodeName\":\"满足其中一条\",\"nodeNo\":\"1\",\"nodeType\":\"group\",\"parentId\":0,\"relation\":\"or\"},\"default\":false,\"defaultKey\":\"default\",\"expressionUnits\":[{\"code\":\"b6574\",\"name\":\"事件字段7\",\"type\":\"field\"},{\"term\":\"+\",\"type\":\"term\"},{\"type\":\"input\",\"value\":\"7\"}],\"functions\":{},\"groupOrder\":2,\"key\":\"1\",\"referFieldBids\":[\"b6574\"],\"referFunctionCodes\":[]}]}";
        JSONObject jsonObject = JSON.parseObject(source);

        JSONArray valueConditions = (JSONArray) jsonObject.get("valueConditions");
        for(Object jsonObject1:valueConditions){
            System.out.println(getProperty((Map<?, ?>) jsonObject1,"conditionGroup"));
            JSONArray   expressionUnits = (JSONArray) ((Map) jsonObject1).get("expressionUnits");
            for(Object object:expressionUnits){
                System.out.println(getProperty((Map<?, ?>) object,"code"));
                setProperty((Map) object,"code","aaa");
            }
            System.out.println(getProperty((Map<?, ?>) jsonObject1,"referFieldBids"));
            setProperty((Map) jsonObject1,"referFieldBids","aaa");
        }
        System.out.println(JSON.toJSONString(jsonObject));
//        System.out.println(getProperty(valueConditions,"valueConditions"));

    }

    public static Object getProperty(Map<?, ?> map, Object qualifiedKey) {
        if (map != null && !map.isEmpty() && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!"".equals(input)) {
                if (input.contains(".")) {
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    return getProperty((Map<?, ?>) map.get(left), right);
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
                    if (map.get(left) == null) map.put(left, new HashMap<>());
                    setProperty((Map<?, ?>) map.get(left), right, value);
                } else {
                    ((Map<Object, Object>) map).put(qualifiedKey, value);
                }
            }
        }
    }

}

