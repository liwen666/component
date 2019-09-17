package com.temp.common.base.util.bigdecimal;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * author lw
 * date 2019/9/10  13:50
 * discribe 测试
 */
public class TestAll {
    public static void main(String[] args) {
        String brandNo = "188,188888";
        System.out.println(getBrandNos(brandNo));
    }

    private static String getBrandNos(String brandNoList) {
        StringBuilder in = new StringBuilder();
        for (String s : brandNoList.split(",")) {
            in.append(s + "','");
        }
        return in.substring(0, in.length() - 2);
    }


    @Test
    public void testString() {
        String tar = "";
        System.out.println(StringUtils.isEmpty(tar));
        tar = null;
        System.out.println(StringUtils.isEmpty(tar));
        tar = "   ";
        System.out.println(StringUtils.isEmpty(tar));
    }


    @Test
    public void str() {
        System.out.println("".equals(("".split(",")[0])));

        System.out.println(System.currentTimeMillis());
    }
}
