package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.util;

/**
 * author lw
 * date 2019/8/26  16:10
 * discribe
 */
public class AssertUtils {
    public static void expect(boolean b, String message) {
        if(!b){
            throw new IllegalArgumentException(message);
        }
    }
}
