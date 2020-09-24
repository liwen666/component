package com.temp.common.base.util;

import java.util.HashMap;
import java.util.Map;

public class CompareStrUtil {

    // 长字符串向短字符串比
    public static String compareStrLong(String char1, String char2, String colour) {
        String bcolor = "<span style='white-space:pre-wrap;background-color: rgba(218,248,212,0.92);color:" + colour + "'>";
        String ecolor = "</span>";
        StringBuffer sb = new StringBuffer();
        char[] a = new char[char1.length()];
        for (int i = 0; i < char1.length(); i++) {
            a[i] = char1.charAt(i);
        }
        char[] b = new char[char2.length()];
        for (int i = 0; i < char2.length(); i++) {
            b[i] = char2.charAt(i);
        }
        // 不同字符集合
        Map<Object, Object> map1 = new HashMap<Object, Object>();
        // 包含字符集合
        Map<Object, Object> map2 = new HashMap<Object, Object>();
        if (char1.length() > char2.length()) {
            for (int i = 0; i < a.length; i++) {
                if (i == a.length - 1) {
                    if (i > 1) {
                        if (String.valueOf(b).contains(String.valueOf(a[i - 1]) + String.valueOf(a[i]))) {
                            map2.put(i - 1, a[i - 1]);
                            map2.put(i, a[i]);
                        }else{
                            map1.put(i, a[i]);
                        }
                    } else {
                        map2.put(i, a[i]);
                    }
                } else {
                    if (String.valueOf(b).contains(String.valueOf(a[i]) + String.valueOf(a[i + 1]))) {
                        if (i > 1) {
                            if (String.valueOf(b).contains(String.valueOf(a[i - 1]) + String.valueOf(a[i]))) {
                                map2.put(i - 1, a[i - 1]);
                                map2.put(i, a[i]);
                            }
                        } else {
                            map2.put(i, a[i]);
                        }
                    } else {
                        if (i > 0) {
                            if (String.valueOf(b).contains(String.valueOf(a[i - 1]) + String.valueOf(a[i]))) {
                                map2.put(i - 1, a[i - 1]);
                                map2.put(i, a[i]);
                            } else {
                                map1.put(i, a[i]);
                            }
                        } else {
                            map1.put(i, a[i]);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < b.length; i++) {
                if (i == b.length - 1) {
                    if (i > 1) {
                        if (String.valueOf(a).contains(String.valueOf(b[i - 1]) + String.valueOf(b[i]))) {
                            map2.put(i - 1, b[i - 1]);
                            map2.put(i, b[i]);
                        }else{
                            map1.put(i, b[i]);
                        }
                    } else {
                        map2.put(i, b[i]);
                    }
                } else {
                    if (String.valueOf(a).contains(String.valueOf(b[i]) + String.valueOf(b[i + 1]))) {
                        if (i > 1) {
                            if (String.valueOf(a).contains(String.valueOf(b[i - 1]) + String.valueOf(b[i]))) {
                                map2.put(i - 1, b[i - 1]);
                                map2.put(i, b[i]);
                            }
                        } else {
                            map2.put(i, b[i]);
                        }
                    } else {
                        if (i > 0) {
                            if (String.valueOf(a).contains(String.valueOf(b[i - 1]) + String.valueOf(b[i]))) {
                                map2.put(i - 1, b[i - 1]);
                                map2.put(i, b[i]);
                            } else {
                                map1.put(i, b[i]);
                            }
                        } else {
                            map1.put(i, b[i]);
                        }
                    }
                }
            }
        }
        if (char1.length() > char2.length()) {
            for (int i = 0; i < a.length; i++) {
                if (map1.get(i) != null) {
                    sb.append(bcolor).append(map1.get(i)).append(ecolor);
                } else if (map2.get(i) != null) {
                    sb.append(map2.get(i));
                }
            }
        } else if (char1.length() <= char2.length()) {
            for (int i = 0; i < b.length; i++) {
                if (map1.get(i) != null) {
                    sb.append(bcolor).append(map1.get(i)).append(ecolor);
                } else if (map2.get(i) != null) {
                    sb.append(map2.get(i));
                }
            }
        }
        return sb.toString();
    }

    // 获取对比标题 
    public static String getcompareStr(String char1, String char2, String colour, String tag1, String tag2) {
        String bcolor = "<font color='" + colour + "'>" + tag1;
        String ecolor = tag2 + "</font>";
        StringBuffer sb = new StringBuffer();
        char[] a = new char[char1.length()];
        for (int i = 0; i < char1.length(); i++) {
            a[i] = char1.charAt(i);
        }
        char[] b = new char[char2.length()];
        for (int i = 0; i < char2.length(); i++) {
            b[i] = char2.charAt(i);
        }
        // 不同字符集合
        Map<Object, Object> map1 = new HashMap<Object, Object>();
        // 包含字符集合
        Map<Object, Object> map2 = new HashMap<Object, Object>();
        for (int i = 0; i < a.length; i++) {
            if (i == a.length - 1) {
                if (i > 1) {
                    if (String.valueOf(b).contains(String.valueOf(a[i - 1]) + String.valueOf(a[i]))) {
                        map2.put(i - 1, a[i - 1]);
                        map2.put(i, a[i]);
                    }else{
                        map1.put(i, a[i]);
                    }
                } else {
                    map2.put(i, a[i]);
                }
            } else {
                if (String.valueOf(b).contains(String.valueOf(a[i]) + String.valueOf(a[i + 1]))) {
                    if (i > 1) {
                        if (String.valueOf(b).contains(String.valueOf(a[i - 1]) + String.valueOf(a[i]))) {
                            map2.put(i - 1, a[i - 1]);
                            map2.put(i, a[i]);
                        }
                    } else {
                        map2.put(i, a[i]);
                    }
                } else {
                    if (i > 0) {
                        if (String.valueOf(b).contains(String.valueOf(a[i - 1]) + String.valueOf(a[i]))) {
                            map2.put(i - 1, a[i - 1]);
                            map2.put(i, a[i]);
                        } else {
                            map1.put(i, a[i]);
                        }
                    } else {
                        map1.put(i, a[i]);
                    }
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            if (map1.get(i) != null) {
                sb.append(bcolor).append(map1.get(i)).append(ecolor);
            } else if (map2.get(i) != null) {
                sb.append(map2.get(i));
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String aaa="            abc";
        String bbb="abcdddd";
        String red = CompareStrUtil.compareStrLong(aaa, bbb, "red");
        System.out.println(red);
//        System.out.println(green);
    }


}