package com.temp.common.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * author lw
 * date 2019/8/28  23:02
 * discribe
 */
public class SqlUtil {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer i : list) {
            stringBuilder.append(i + ",");
        }
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
        ;
        System.out.println(substring);
    }


}
