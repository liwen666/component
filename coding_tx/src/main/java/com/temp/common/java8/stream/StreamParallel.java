package com.temp.common.java8.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class StreamParallel {
    public static void main(String[] args) {
        ArrayList<Object> objects = Lists.newArrayList();
        for(int i=0;i<10000;i++){
            objects.add(i);
        }
        System.out.println(objects.size());
        ArrayList<Object> objects1 = Lists.newArrayList();
//        objects.parallelStream().forEach(e->{
        objects.stream().forEach(e->{
            try {
                Thread.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            objects1.add(e);
        });
        System.out.println(objects1.size());
    }
}
