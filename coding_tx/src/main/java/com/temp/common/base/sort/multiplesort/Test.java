package com.temp.common.base.sort.multiplesort;

import com.temp.common.base.util.SortUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


public class Test {
    @SuppressWarnings("unchecked")
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
        list.add(user2);
        list.add(user1);
        list.add(user3);
        list.add(user4);
        System.out.println(list);
        System.out.println("======================================================");
        List<User> collect = list.stream().filter(user -> user.isTop() == true).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("======================================================");
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                System.out.println("o1---order:" + ((User) o1).getOrder());
                System.out.println("o2---order:" + ((User) o2).getOrder());
                if (((User) o1).isTop()) {
                    if (((User) o2).isTop()) {
                        return ((User) o1).getOrder().compareTo(((User) o2).getOrder());
                    } else {
                        return -1;
                    }
                }
                int i = ((User) o1).getOrder().compareTo(((User) o2).getOrder());
                System.out.println(i);
                return i;
            }
        });
        System.out.println(list);
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                System.out.println("o1---order:" + ((User) o1).getOrder());
                System.out.println("o2---order:" + ((User) o2).getOrder());
                if (((User) o2).isTop()) {
                    if (((User) o1).isTop()) {
                        return ((User) o2).getOrder().compareTo(((User) o1).getOrder());
                    } else {
                        return -1;
                    }
                }
                int i = ((User) o2).getOrder().compareTo(((User) o1).getOrder());
                System.out.println(i);
                return i;
            }
        });
        System.out.println(list);

    }

}