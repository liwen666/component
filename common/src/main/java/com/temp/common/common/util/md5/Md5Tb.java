package com.temp.common.common.util.md5;

import java.security.MessageDigest;

/**
 * author lw
 * date 2019/9/19  15:37
 * discribe 竞品
 */
public class Md5Tb {

    public static String md5(String paramString)
    {
        try
        {
            MessageDigest localObject = MessageDigest.getInstance("MD5");
            localObject.update(paramString.getBytes());
            byte[] test = localObject.digest();
            StringBuffer localStringBuffer = new StringBuffer("");
            int i = 0;
            while (i < test.length)
            {
                int k = test[i];
                int j = k;
                if (k < 0) {
                    j = k + 256;
                }
                if (j < 16) {
                    localStringBuffer.append("0");
                }
                localStringBuffer.append(Integer.toHexString(j));
                i += 1;
            }
            String str = localStringBuffer.toString();
            return str;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return paramString;
    }

    public static void main(String[] args) {
        String kjlkjflkda = md5("kjlkjflkda");
        System.out.println(kjlkjflkda);
        String s = MD5Tools.convertMD5(MD5Tools.convertMD5(kjlkjflkda));
        System.out.println(s);
    }
}
