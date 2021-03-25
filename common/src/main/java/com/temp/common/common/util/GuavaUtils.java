package com.temp.common.common.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import org.junit.Test;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/24  20:50
 */
public class GuavaUtils {
//    驼峰转下划线
    @Test
    public void convertLineToHumpTest() {
        String resultStr = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "studentName");
        System.out.println("  >>> 驼峰转下划线 : {}"+ resultStr);
    }
//    下划线转驼峰
    @Test
    public void convertHumpToLineTest() {
        String resultStr = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "student_name");
        System.out.println("  >>> 下划线转驼峰 : {}"+ resultStr);
    }
    // 拆分器
    @Test
    public void Splitter() {
        Iterable<String> split = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux");
        split.forEach(e->{
            System.out.println(e+"---");
        });
    }

    @Test
    public void bbbccc() {
       byte[] bytes = "bbbccc".getBytes(Charsets.UTF_8);

    }

}
