package com.temp.common.base.seal;

import java.util.Arrays;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class reversive {
    public static void main(String[] args) {
            String aaa="123456";
        System.out.println(
                aaa
        );
        char[] chars = aaa.toCharArray();
        StringBuffer stringBuffer = new StringBuffer(aaa);
        StringBuffer reverse = stringBuffer.reverse();
        System.out.println(reverse);


    }
}
