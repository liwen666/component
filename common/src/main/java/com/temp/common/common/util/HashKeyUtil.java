//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.temp.common.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class HashKeyUtil {
    private static final Log log = LogFactory.getLog(HashKeyUtil.class);
//    private static final String ALGORITHM = "DESede!a";
    static final byte[] keyBytes = new byte[]{17, 34, 79, 88, -120, 16, 64, 56, 40, 37, 121, 81, -53, -35, 85, 102, 119, 41, 116, -104, 48, 64, 54, -30};
    private static MessageDigest digest = null;
//    private static Random randGen = new Random();

    public HashKeyUtil() {
    }

    public static String encrypt(String info) {
        try {
            byte[] newInfo = encrypt(info.getBytes());
            return (new BASE64Encoder()).encode(newInfo);
        } catch (Exception var2) {
            return null;
        }
    }

    public static String decrypt(String info) {
        try {
            byte[] newInfo = decrypt((new BASE64Decoder()).decodeBuffer(info));
            if (newInfo != null) {
                return new String(newInfo);
            }
        } catch (IOException var2) {
            log.error(var2.getMessage());
        }

        return null;
    }

    public static byte[] encrypt(byte[] info) {
        return cryptanalyze(1, info);
    }

    public static byte[] decrypt(byte[] info) {
        return cryptanalyze(2, info);
    }

    private static byte[] cryptanalyze(int mode, byte[] info) {
        try {
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(mode, deskey);
            byte[] newInfo = cipher.doFinal(info);
            return newInfo;
        } catch (Exception var5) {
            log.error(var5.getMessage());
            return null;
        }
    }

    public static synchronized String hash(String data) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        } else {
            if (digest == null) {
                try {
                    digest = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException var3) {
                    throw new Exception("不支持该加密算法:MD5");
                }
            }

            try {
                digest.update(data.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException var2) {
                throw new Exception(var2.getMessage());
            }

            return encodeHex(digest.digest());
        }
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer hex = new StringBuffer(bytes.length * 2);

        for(int i = 0; i < bytes.length; ++i) {
            if ((bytes[i] & 255) < 16) {
                hex.append("0");
            }

            hex.append(Integer.toString(bytes[i] & 255, 16));
        }

        return hex.toString();
    }

    public static final String randomString(int length) {
        return length < 1 ? null : RandomStringUtils.randomAlphanumeric(length);
    }

    public static void main(String[] args) throws Exception {
        String szSrc = "This is a 3DES test.";

        System.out.println("加密前的字符串:" + szSrc);
        String encoded = encrypt(szSrc);
        System.out.println("加密后的字符串:" + encoded);
        String srcBytes = decrypt(encoded );
        System.out.println("解密后的字符串:" + srcBytes);
        String hash = hash(szSrc);
        System.out.println(hash);
    }
}
