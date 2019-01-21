package com.temp.common.base.sort.comparable.compator.com;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Test{
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        User user1 = new User();
        user1.setName("a");
        user1.setOrder(3);
        User user2 = new User();
        user2.setName("b");
        user2.setOrder(2);
        List <User>list = new ArrayList <User>();
        list.add(user2);
        list.add(user1);
         
        Collections.sort(list,new Comparator(){
			@Override
			public int compare(Object o1, Object o2) {
				return ((User)o1).getOrder().compareTo(((User)o2).getOrder());
			}
        });
        for(User u : list){
            System.out.println(u.getName());
        }
    }
}