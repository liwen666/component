package com.temp.common.base.sort;

import com.alibaba.fastjson.JSON;
import com.temp.common.base.sort.multiplesort.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Sort {
    public static void main(String[] args) {
        User user1 = new User();
        user1.setName("a");
        user1.setOrder(3);
        User user2 = new User();
        user2.setTop(true);
        user2.setName("b");
        user2.setOrder(2);
        User user3 = new User();
        user3.setName("c");
        user3.setOrder(1);
        user3.setTop(true);
        User user4 = new User();
        user4.setName("d");
        user4.setOrder(100);
        user4.setTop(true);
        List<User> list = new ArrayList<User>();
        User user5 = new User();
        user5.setName("e");
        user5.setOrder(1);
        User user6 = new User();
        user6.setName("f");
        user6.setOrder(20);
        list.add(user2);
        list.add(user1);
        list.add(user3);
        list.add(user4);
        list.add(user5);
        list.add(user6);

        System.out.println(list);
        System.out.println("1======================================================");
        Stream<User> sorted = list.stream().sorted();
        System.out.println(JSON.toJSONString(sorted));
        System.out.println("2======================================================");
        List<User> collect = list.stream().sorted(Comparator.comparing(User::getOrder)).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("3======================================================");
        list.stream().sorted(Comparator.comparing(User::getOrder)).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("4======================================================");
        Stream<User> userStream = list.stream().filter(task -> task.getOrder() == 1);
        List<User> collect1 = userStream.collect(Collectors.toList());
        System.out.println(collect1);
        //通过一个字段对一个集合进行过滤
//        userStream.map(User::getName).collect(Collectors.toList());
        System.out.println("5======================================================");


    }
}
