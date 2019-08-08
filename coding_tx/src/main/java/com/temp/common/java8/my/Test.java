package com.temp.common.java8.my;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author lw
 * date 2019/7/25  14:47
 * discribe 测试Stream
 */
public class Test {
    public static void main(String[] args) {
        String  data ="[{\"createTime\":1560575841000,\"delay\":0,\"id\":4,\"merchantGroupId\":1,\"modifyTime\":1560575843000,\"orderGroupId\":2,\"pushNumber\":1003,\"sort\":1},{\"createTime\":1560575862000,\"delay\":5,\"id\":5,\"merchantGroupId\":1,\"modifyTime\":1560575864000,\"orderGroupId\":2,\"pushNumber\":5,\"sort\":2},{\"delay\":1,\"id\":14,\"merchantGroupId\":3,\"orderGroupId\":5,\"pushNumber\":990,\"sort\":2},{\"createTime\":1560575841000,\"delay\":20,\"id\":16,\"merchantGroupId\":1,\"modifyTime\":1560575843000,\"orderGroupId\":1,\"pushNumber\":1000,\"sort\":3},{\"createTime\":1560575862000,\"delay\":5,\"id\":17,\"merchantGroupId\":1,\"modifyTime\":1560575864000,\"orderGroupId\":1,\"pushNumber\":5,\"sort\":2},{\"createTime\":1560575890000,\"delay\":10,\"id\":18,\"merchantGroupId\":1,\"modifyTime\":1560575893000,\"orderGroupId\":1,\"pushNumber\":10,\"sort\":3}]\n";
        List<GroupPushPolicy> groupPushPolicies = JSON.parseArray(data, GroupPushPolicy.class);
        System.out.println(JSON.toJSONString(groupPushPolicies));

        // Group tasks by their status
//        final Map<Streams.Status, List<Streams.Task>> map = tasks
//                .stream()
//                .collect( Collectors.groupingBy( Streams.Task::getStatus ) );
        /////////////////////////////////////////////////////////////////////
//        Map<Long, List<GroupPushPolicy>> collect = groupPushPolicies.stream().collect(Collectors.groupingBy(GroupPushPolicy::getMerchantGroupId));
//        System.out.println(JSON.toJSONString(collect));
//        System.out.println("--------------------------------------------");
//        Map<Long, Map<Long, List<GroupPushPolicy>>> collect1 = groupPushPolicies.stream().collect(Collectors.groupingBy(GroupPushPolicy::getMerchantGroupId, Collectors.groupingBy(GroupPushPolicy::getOrderGroupId)));
//        System.out.println(JSON.toJSONString(collect1));
//        List<List<GroupPushPolicy>>  result= new ArrayList<>();
//        Collection<Map<Long, List<GroupPushPolicy>>> values = collect1.values();
//        values.stream().forEach(e->{
//            Collection<List<GroupPushPolicy>> values1 = e.values();
//            values1.stream().forEach(e1->{
//                result.add(e1);
//            });
//        });
//        System.out.println(JSON.toJSONString(result));
//

        /////////////////////////////////////////////////////////
        Map<Long, List<GroupPushPolicy>> collect = groupPushPolicies.stream().collect(Collectors.groupingBy(GroupPushPolicy::getMerchantGroupId));
        Map<Long, Map<Long, List<GroupPushPolicy>>> collect1 = groupPushPolicies.stream().collect(Collectors.groupingBy(GroupPushPolicy::getMerchantGroupId, Collectors.groupingBy(GroupPushPolicy::getOrderGroupId)));
        List<List<PushGroupPolicyVO>>  result= new ArrayList<>();
        Collection<Map<Long, List<GroupPushPolicy>>> values = collect1.values();
        values.stream().forEach(e->{
            Collection<List<GroupPushPolicy>> values1 = e.values();
            values1.stream().forEach(e1->{
                List <PushGroupPolicyVO> list = new ArrayList<>();
                e1.forEach(e2->{
                    list.add(PushGroupPolicyVO.builder().createTime(e2.getCreateTime()).modifyTime(e2.getModifyTime())
                            .delay(e2.getDelay()).id(e2.getId()).merchantGroupId(e2.getMerchantGroupId()).orderGroupId(e2.getOrderGroupId()).pushNumber(e2.getPushNumber())
                            .sort(e2.getSort()).build());
                });
                result.add(list);
            });
        });

        System.out.println(JSON.toJSONString(result));
//        DataConversion
    }

}
