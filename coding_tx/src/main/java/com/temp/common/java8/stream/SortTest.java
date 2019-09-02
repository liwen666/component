package com.temp.common.java8.stream;

import com.alibaba.fastjson.JSON;
import com.temp.common.java8.stream.entity.FetcherDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author lw
 * date 2019/9/2  13:54
 * discribe 测试排序
 */
public class SortTest {
    public static void main(String[] args) {
        String areaCode ="1";
        List<FetcherDto> fetcherDtoList = new ArrayList<>();
        for(int i=2;i<10;i++){
            FetcherDto build = FetcherDto.builder().areaCode(i+"").id((long) i).build();
            fetcherDtoList.add(build);
        }
//        fetcherDtoList.add(FetcherDto.builder().id(10000l).areaCode("2").build());
        List<FetcherDto> collect = fetcherDtoList.stream().filter(e -> areaCode.equals(e.getAreaCode())).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));
        boolean b = fetcherDtoList.removeAll(collect);
        System.out.println(b);
        System.out.println(JSON.toJSONString(fetcherDtoList.stream().map(FetcherDto::getId).collect( Collectors.toList())));
        List<FetcherDto>fetcherDtos = new ArrayList<>();
        fetcherDtos.addAll(collect);
        fetcherDtos.addAll(fetcherDtoList);
        System.out.println(JSON.toJSONString(fetcherDtos.stream().map(FetcherDto::getId).collect( Collectors.toList())));



    }
}
