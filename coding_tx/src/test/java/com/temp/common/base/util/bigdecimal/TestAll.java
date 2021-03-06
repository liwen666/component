package com.temp.common.base.util.bigdecimal;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Date;

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

    @Test
    public void name() {

        int i = Integer.parseInt("999999999");
        System.out.println(i);
    }

    @Test
    public void switchCas() {
        int a=7;
        switch (a){
            case 1:
            case 4:
                System.out.println("aaaaa");
                break;
            case 3:
                System.out.println("bbbbb");
                break;
                default:
                    System.out.println("ccccc");
        }
    }

    @Test
    public void testStr() {
        boolean contains = "%{".contains("%{");
        System.out.println(contains);


    }

    @Test
    public void dateFinal() {
    }
}
