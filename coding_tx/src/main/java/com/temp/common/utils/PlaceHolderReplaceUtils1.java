package com.temp.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * <p>
 * 描述${}占位符替换工具
 * </p>
 *
 * @author LW
 * @since 2021/3/29  10:22
 */
@Slf4j
public class PlaceHolderReplaceUtils1 {
    public static String replacePlaceHolder(String str, Map<String, String> m) {
        char[] chars = str.toCharArray();
        StringBuilder strRel = new StringBuilder();
        StringBuilder token = new StringBuilder();
        boolean add = true;
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case 36:
                    for (int j = i; j < chars.length; j++) {
                        if (chars[j] == 32) {
                            continue;
                        } else if (chars[j] == 123) {
                            add = false;
                            break;
                        }
                    }
                    break;
                case 125:
                    if (add) {
                        break;
                    }
                    String val = m.get(token.toString());
                    if (null == val) {
                        log.error("替换占位符失败，未查询到对应的key值，key:{},value:{}", token.toString(), m);
                        throw new RuntimeException("替换占位符失败，未查询到对应的key值，key:{}" + token.toString() + ".value:{}" + m);
                    }
                    strRel.append(val);
                    token.setLength(0);
                    add = true;
                    continue;
                default:
                    if (!add && chars[i] != 32 && chars[i] != 123) {
                        token.append(chars[i]);
                    }
            }
            if (add) {
                strRel.append(chars[i]);
            }
        }
        return strRel.toString();
    }
}
