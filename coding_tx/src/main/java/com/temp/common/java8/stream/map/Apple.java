package com.temp.common.java8.stream.map;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Apple {
    private Integer id;
    private String name;
    private BigDecimal money;
    private Integer num;

    public Apple(Integer id, String name, BigDecimal money, Integer num) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.num = num;
    }

}

class Test {
    public static void main(String[] args) {
        List<Apple> appleList = new ArrayList<>();//存放apple对象集合

        Apple apple1 = new Apple(1, "苹果1", new BigDecimal("3.25"), 10);
        Apple apple12 = new Apple(1, "苹果2", new BigDecimal("1.35"), 20);
        Apple apple2 = new Apple(2, "香蕉", new BigDecimal("2.89"), 30);
        Apple apple3 = new Apple(3, "荔枝", new BigDecimal("9.99"), 40);

        appleList.add(apple1);
        appleList.add(apple12);
        appleList.add(apple2);
        appleList.add(apple3);

        Map<Integer, List<Apple>> groupBy = appleList.stream().collect(Collectors.groupingBy(Apple::getId));

        System.err.println("groupBy:" + groupBy);
/**
 * List -> Map
 * 需要注意的是：
 * toMap 如果集合对象有重复的key，会报错Duplicate key ....
 *  apple1,apple12的id都为1。
 *  可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
 */
        Map<Integer, Apple> appleMap = appleList.stream().collect(Collectors.toMap(Apple::getId, a -> a, (k1, k2) -> k1));
        System.out.println(JSON.toJSONString(appleMap));
        Map<Integer, BigDecimal> collect = appleList.stream().collect(Collectors.toMap(Apple::getId, a -> a.getMoney(), (k1, k2) -> k1));
        System.out.println("=========================================================================================");
        System.out.println(JSON.toJSONString(collect));
        Map<Integer, BigDecimal> collect2 = appleList.stream().collect(Collectors.toMap(Apple::getId,Apple::getMoney));
        System.out.println("=========================================================================================");
        System.out.println(JSON.toJSONString(collect2));
    }
}

class testParam {
    public static void main(String[] args) {
        Map m = new HashMap();
        setm(m);
        System.out.println(JSON.toJSONString(m));
    }

    private static void setm(Map m) {
        m.put("aaa","bbb");
    }
}