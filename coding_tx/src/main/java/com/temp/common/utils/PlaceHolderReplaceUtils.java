package com.temp.common.utils;

import java.util.Map;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/29  10:22
 */
public class PlaceHolderReplaceUtils {
    public static String replacePlaceHolder(String str, Map m) {
        char[] chars = str.toCharArray();
        StringBuffer strRel = new StringBuffer();
        StringBuffer token = new StringBuffer();
        boolean add = true;
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case 36:
                    for(int j=i;j<chars.length;j++){
                        if(chars[j]==32){
                            continue;
                        }else if(chars[j]==123){
                            add = false;
                            break;
                        }
                    }
                    break;
                case 125:
                    if (add) {
                        break;
                    }
                    String val = (String) m.get(token.toString());
                    if(null==val){
                        val="${"+token.toString()+"}";
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
