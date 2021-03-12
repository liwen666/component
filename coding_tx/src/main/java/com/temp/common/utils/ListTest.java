package com.temp.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/12  17:06
 */
@Slf4j
public class ListTest {
    // 数据条数
    static int nums = 10000000;

    public static void main(String[] args) {
        List arrayList = new ArrayList();
        List linkedList = new LinkedList();

        log.info("*****这里是从尾端add测试*****");
        long arrayStartTime = System.currentTimeMillis();
        for (int i = 0; i < nums; i++) {
            arrayList.add(i);
        }
        log.info("ArrayList结束，耗时:{}毫秒。", System.currentTimeMillis() - arrayStartTime);

        long linkedStartTime = System.currentTimeMillis();
        for (int i = 0; i < nums; i++) {
            linkedList.add(i);
        }
        log.info("LinkedList结束，耗时:{}毫秒。", System.currentTimeMillis() - linkedStartTime);

        log.info("*****这里是从头部add测试*****");
        long arrayStartTime1 = System.currentTimeMillis();
        arrayList.add(1, 111);
        log.info("ArrayList结束，耗时:{}毫秒。", System.currentTimeMillis() - arrayStartTime1);

        long linkedStartTime1 = System.currentTimeMillis();
        linkedList.add(1, 111);
        log.info("LinkedList结束，耗时:{}毫秒。", System.currentTimeMillis() - linkedStartTime1);

        log.info("*****这里是从尾部remove测试*****");
        long arrayStartTime2 = System.currentTimeMillis();
        arrayList.remove(9999999);
        log.info("ArrayList结束，耗时:{}毫秒。", System.currentTimeMillis() - arrayStartTime2);

        long linkedStartTime2 = System.currentTimeMillis();
        linkedList.remove(9999999);
        log.info("LinkedList结束，耗时:{}毫秒。", System.currentTimeMillis() - linkedStartTime2);
        System.out.println(arrayList.size());
        System.out.println(linkedList.size());

        log.info("*****这里是从尾部get测试*****");
        long arrayStartTime3 = System.currentTimeMillis();
        System.out.println(arrayList.get(9999999));
        log.info("ArrayList结束，耗时:{}毫秒。", System.currentTimeMillis() - arrayStartTime3);

        long linkedStartTime3 = System.currentTimeMillis();
        System.out.println(linkedList.get(9999999));
        log.info("LinkedList结束，耗时:{}毫秒。", System.currentTimeMillis() - linkedStartTime3);

    }

}
