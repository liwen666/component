package com.temp.common.java8.stream.group;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
public class StreamTestData {
    Integer id;
    String name;
    Integer age;
    String subject;
    Integer grade;

    @Override
    public String toString() {
        return "StreamTestData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", subject='" + subject + '\'' +
                ", grade=" + grade +
                '}';
    }

    public List<StreamTestData> createList() {
        List<StreamTestData> listData = new ArrayList<>();
        //组一：1001 1
        StreamTestData data1 = new StreamTestData();
        data1.setId(1);
        data1.setAge(18);
        data1.setName("name1");
        data1.setSubject("1001");
        data1.setGrade(1);
        listData.add(data1);
        StreamTestData data2 = new StreamTestData();
        data2.setId(2);
        data2.setAge(18);
        data2.setName("name2");
        data2.setSubject("1001");
        data2.setGrade(1);
        listData.add(data2);
        //组二：1001 2
        StreamTestData data4 = new StreamTestData();
        data4.setId(4);
        data4.setAge(18);
        data4.setName("name4");
        data4.setSubject("1001");
        data4.setGrade(2);
        listData.add(data4);
        //组三：1002 2
        StreamTestData data3 = new StreamTestData();
        data3.setId(3);
        data3.setAge(18);
        data3.setName("name3");
        data3.setSubject("1002");
        data3.setGrade(2);
        listData.add(data3);
        //组三：1002 3
        StreamTestData data5 = new StreamTestData();
        data5.setId(3);
        data5.setAge(18);
        data5.setName("name5");
        data5.setSubject("1002");
        data5.setGrade(3);
        listData.add(data5);
        return listData;
    }


    //分组条件一subject：StreamTestData::getSubject
//分组条件二grade: StreamTestData::getGrade
    @Test
    public void group() {
        List<StreamTestData> dataList = createList();
        Map<String, Map<Integer, List<StreamTestData>>> groups2 = dataList.stream().collect(
                Collectors.groupingBy(StreamTestData::getSubject, Collectors.groupingBy(StreamTestData::getGrade)));
        int max = 0;
        List<StreamTestData> maxList = new ArrayList<>();

        for (Map<Integer, List<StreamTestData>> group1 : groups2.values()) {
            for (List<StreamTestData> group : group1.values()) {
                if (group.size() > max) {
                    max = group.size();
                    maxList = group;
                }
            }
        }
        System.out.println("人数最多的组人数为：" + max);
        if (maxList.size() > 0) {
            maxList.forEach(list -> {
                System.out.println(list.toString());
            });
        }
    }
}
