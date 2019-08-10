package com.temp.common.java8.stream;

import com.alibaba.fastjson.JSON;
import com.temp.common.java8.util.OrderRecord;
import com.temp.common.java8.util.TestDateUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author lw
 * date 2019/8/8  14:53
 * discribe 对集合进行排序
 */
public class StreamSort {

  @Test
    public  void sort() throws IOException {
      List<OrderRecord> orderRecords = TestDateUtil.orderRecordList();
      System.out.println(orderRecords.size());
      //正序排列
      List<OrderRecord> collect = orderRecords.stream().sorted(Comparator.comparing(OrderRecord::getId)).collect(Collectors.toList());
      //倒叙排列
      List<OrderRecord> collect2 = orderRecords.stream().sorted(Comparator.comparing(OrderRecord::getId).reversed()).collect(Collectors.toList());
      //倒序
      Collections.reverse(collect);
      System.out.println(JSON.toJSONString(orderRecords.stream().map(OrderRecord::getId).collect(Collectors.toList())));
      System.out.println(JSON.toJSONString(collect.stream().map(OrderRecord::getId).collect(Collectors.toList())));

      System.out.println("=========================================================================================");
      System.out.println(JSON.toJSONString(collect2.stream().map(OrderRecord::getId).collect(Collectors.toList())));

  }
}
