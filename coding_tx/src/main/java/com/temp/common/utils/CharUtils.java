package com.temp.common.utils;

import com.temp.common.common.util.JsonUtils;
import groovy.lang.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/12  15:55
 */
public class CharUtils {
    public static void main(String[] args) {

        //字符串转char数组
        String bigmd5 = "j321431jjjjjjjsjflkajlfkda";
        char[] bigchar = bigmd5.toCharArray();
        for (char c : bigchar) {
            //判断char是否为数字
            if (Character.isDigit(c)) {
                System.out.println("数字");
            } else if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                System.out.println("字母");
            } else {
                byte[] bytes = ("" + c).getBytes();
                if (bytes.length == 2) {
                    int[] ints = new int[2];
                    ints[0] = bytes[0] & 0xff;
                    ints[1] = bytes[1] & 0xff;

                    if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
                        System.out.println("汉字");
                    }
                }
            }
        }
    }

    private static String conversionLine(StringBuffer line) {
        char ch;
        String str = line.toString();
        int index;
        boolean hasElseSign =
                false;
        while (null != str) {
            str = str.trim();
            if (0 != str.length()) {
                if (hasElseSign == false) {
                    for (index = 0; index < str.length(); index++) {
                        ch = str.charAt(index);
                        if ((ch == '/')) {
                            if (str.charAt(index + 1) == '/') {//是否有单行注释，如有，截取字符串
                                str = str.substring(0, index);
                                break;
                            }
                            if (str.charAt(index + 1) == '*') {//是否有多行注释
                                hasElseSign = true;
                                break;
                            }
                        }
                    }
                    if (hasElseSign) continue;
                } else {
                    for (index = 0; index < str.length(); index++) {
                        ch = str.charAt(index);
                        if ((ch == '*') && (index < str.length() - 1) && (str.charAt(index + 1) == '/')) {
                            hasElseSign = false;
                            break;
                        }
                    }
                    continue;
                }
            }
        }
        return str;
    }
}
class Char{
    public static void main(String[] args) {
        String str="abcdefghijklmnopqrstuvwxyz1234567890!@$%^&*()_+=-|/\\{}[]'\"?><,.\r\n\t";
        char[] chars = str.toCharArray();
        List<Tuple2<String,Integer>> charArr= new ArrayList<>();
        for(char c:chars){
            charArr.add(new Tuple2<String, Integer>(c+"",c+0));
        }
        charArr.forEach(e-> System.out.println(JsonUtils.obj2StringPretty(e)));
    }
}
