package com.temp.common.base.nmr;


import com.alibaba.fastjson.JSON;
import com.temp.common.base.nmr.high.Main;
import com.temp.common.base.nmr.high.Murmur3Hash;
import com.temp.common.common.util.JsonUtils;

import java.util.*;
import java.util.function.Consumer;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/27  10:13
 */
public class NmrTest {
    public static void main(String[] args) {
//        int MURMUR_HASH_SEED = 25342;
        int MURMUR_HASH_SEED = 25342;
        int numPartitions = 10;
        long first =System.currentTimeMillis();
        HashFunctionMurmur3 hashFunctionMurmur3 = new HashFunctionMurmur3(MURMUR_HASH_SEED);
        Map<Integer, List<Integer>> map = new HashMap();
        for (int i = 0; i < 100000000; i++) {
            int nmr = Math.abs(hashFunctionMurmur3.hashCode(i + "") % numPartitions);
            List<Integer> integers = map.get(nmr) == null ? new ArrayList<>() : map.get(nmr);
            integers.add(i);
            map.put(nmr, integers);

        }
        System.out.println(System.currentTimeMillis()-first);
//        System.out.println(JsonUtils.obj2StringPretty(map));
        System.out.println("-----------------------------------------------------------------------");

        first= System.currentTimeMillis();
        HashFunctionDefault hashFunctionDefault = new HashFunctionDefault();
        Map<Integer, List<Integer>> map2 = new HashMap();
        for (int i = 0; i < 100000000; i++) {
            int nmr = Math.abs(hashFunctionDefault.hashCode(i + "") % numPartitions);
            List<Integer> integers = map2.get(nmr) == null ? new ArrayList<>() : map2.get(nmr);
            integers.add(i);
            map2.put(nmr, integers);

        }

//        System.out.println(JsonUtils.obj2StringPretty(map2));
        System.out.println(System.currentTimeMillis()-first);
        System.out.println("-----------------------------------------------------------------------");

        first= System.currentTimeMillis();

        Map<Integer, List<Integer>> map3 = new HashMap();
        for (int i = 0; i < 100000000; i++) {
            int nmr = (int) Math.abs(Murmur3Hash.hash(i+"") % numPartitions);
            List<Integer> integers = map3.get(nmr) == null ? new ArrayList<>() : map3.get(nmr);
            integers.add(i);
            map3.put(nmr, integers);

        }

        System.out.println(System.currentTimeMillis()-first);
//        System.out.println(JsonUtils.obj2StringPretty(map3));
        System.out.println("-----------------------------------------------------------------------");


    }

    private static byte[] intToByteLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public static int byte2int(byte[] res) {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

}
