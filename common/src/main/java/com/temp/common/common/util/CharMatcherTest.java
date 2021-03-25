package com.temp.common.common.util;
 
import com.google.common.base.CharMatcher;
import org.junit.Test;
 
/**
 * CharMatcher：字符匹配工具类
 * User: Realfighter
 * Date: 2014/8/16
 * Time: 13:06
 */
public class CharMatcherTest {
 
    private static String sequence = "HELLO   RealFighter ~!@#$%^&*() ，,.。   \n123(*&gS   你好\t234啊   abc  \n";
 
    //打印方法
    private static void print(Object obj) {
        System.out.println(String.valueOf(obj));
    }
 
    @Test
    public void testCharMatcher() {
        //原始字符串
        print(sequence);
        //使用JAVA_ISO_CONTROL去除所有控制字符\n\t
        String str = CharMatcher.javaIsoControl().removeFrom(sequence);
        print(str);
        System.out.println("-----------------------------------------------------------------------");

        //筛选出所有的字母(包含中文)或数字
        str = CharMatcher.javaLetterOrDigit().retainFrom(sequence);
        print(str);
        System.out.println("-----------------------------------------------------------------------");


        //匹配sequence中的数字并全部替换成*号
        str = CharMatcher.javaDigit().replaceFrom(sequence,"*");
        print(str);
        System.out.println("-----------------------------------------------------------------------");

        //去除首尾连续匹配到的大写字符
        str = CharMatcher.javaUpperCase().trimFrom(sequence);
        print(str);
        //去除首部连续匹配到的大写字符
        str = CharMatcher.javaUpperCase().trimLeadingFrom(sequence);
        print(str);
        System.out.println("-----------------------------------------------------------------------");


        //去除尾部连续匹配到的大写字符
        str = CharMatcher.javaUpperCase().trimTrailingFrom(sequence);
        print(str);
        System.out.println("-----------------------------------------------------------------------");

        //将匹配到的大写字符替换成问号
        str = CharMatcher.javaUpperCase().collapseFrom(sequence,'?');
        print(str);
        System.out.println("-----------------------------------------------------------------------");

        //去除首尾空白符后将匹配到的小写字符替换为问号
        str = CharMatcher.javaLowerCase().trimAndCollapseFrom(sequence,'?');
        print(str);


    }


}
 