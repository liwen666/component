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
}
