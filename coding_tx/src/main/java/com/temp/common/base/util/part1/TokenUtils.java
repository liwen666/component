package com.temp.common.base.util.part1;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TokenUtils {
    public static String getUserId(String tokenId) {
        if (StringUtils.isEmpty(tokenId)) {
            return null;
        } else if (tokenId.length() < 32) {
            System.out.println("token is not avaliable");
        } else {
            return tokenId.substring(0, 32);
        }
        return null;
    }
    public static List<Map<String, Object>> sortByTwoField(List<Map<String, Object>> allNoticList) throws Exception{
        Collections.sort(allNoticList, new Comparator<Map<String,Object>>(){
            @Override
            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                if(StringUtils.isNotEmpty(map1.get("ORDERID")==null?null:map1.get("ORDERID")+"")){
                    if(StringUtils.isNotBlank(map2.get("ORDERID")==null?null:map2.get("ORDERID")+"")){
                        if(StringUtils.isNotBlank((String)map2.get("SENDDATE")) && StringUtils.isNotBlank((String)map1.get("SENDDATE"))){
                            return map2.get("SENDDATE").toString().compareTo(map1.get("SENDDATE").toString());
                        }else{
                            return 0;
                        }
                    }else{
                        return -1;
                    }
                }
                //按SENDDATE字段降序
                if(StringUtils.isNotBlank((String)map2.get("SENDDATE")) && StringUtils.isNotBlank((String)map1.get("SENDDATE"))){
                    return map2.get("SENDDATE").toString().compareTo(map1.get("SENDDATE").toString());
                }else{
                    return 0;
                }
            }
        });
        return allNoticList;
    }
}
