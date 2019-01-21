package com.temp.common.base.sort.comparable.compator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test{ 
    public static void main(String[] args) {
        User user1 = new User();
        user1.setName("a");
        user1.setOrder(1);
        User user2 = new User();
        user2.setName("b");
        user2.setOrder(2);
        User user3 = new User();
        user3.setName("c");
        user3.setOrder(0);
        List <User>list = new ArrayList<User>();
        //此处add user2再add user1
        list.add(user2);
        list.add(user1);
        list.add(user3);
        Collections.sort(list);
        for(User u : list){
            System.out.print(u.getName());
        }
    }
}