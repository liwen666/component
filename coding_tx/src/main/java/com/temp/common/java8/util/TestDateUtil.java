package com.temp.common.java8.util;

import com.alibaba.fastjson.JSON;
import lombok.Cleanup;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * author lw
 * date 2019/8/8  14:53
 * discribe 数据构造工具
 */
public class TestDateUtil {

    public static void main(String[] args) throws IOException {
        List<OrderRecord> orderRecords = orderRecordList();
    }
    public static List<OrderRecord>  orderRecordList() throws IOException {
        @Cleanup  InputStream resourceAsStream = TestDateUtil.class.getClassLoader().getResourceAsStream("data.txt");
        byte[]bytes = new byte[resourceAsStream.available()];
        resourceAsStream.read(bytes);
        String data = new String(bytes,"utf-8");
        List<OrderRecord> orderRecords = JSON.parseArray(data, OrderRecord.class);
        return orderRecords;
    }
}
