package com.temp.common.common.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;

/**
 * 对明文密码的加密解密工具类
 *
 * @date 2018/4/16
 */
public class CryptoUtils {


    private static final String IVKEY = "JRX_3DES_!@#%FEDSABWQUWY12876985";


    /**
     * 3DES加密
     *
     * @param data 要加密的数据
     * @return 加密后的数据
     * @throws Exception
     */
    public static String encrypt(String data) throws CryptException {
            return Base64.encodeBase64String(encryptBytes(data));
    }

    private static byte[] encryptBytes(String data) throws CryptException {
        try {
            Key deskey = null;
            DESedeKeySpec spec = null;
            spec = new DESedeKeySpec(IVKEY.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            deskey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new CryptException(e.getLocalizedMessage());
        }
    }


    /**
     * 3DES解密
     *
     * @param base64Str 加密后的数据
     * @return 解密后的数据
     * @throws Exception
     */
    public static String decrypt(String base64Str) throws CryptException {
        return new String(decryptBytes(Base64.decodeBase64(base64Str)));
    }

    private static byte[] decryptBytes(byte[] encryptBytes) throws CryptException {
        try {
            Key deskey = null;
            DESedeKeySpec spec = null;
            spec = new DESedeKeySpec(IVKEY.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            deskey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            return cipher.doFinal(encryptBytes);

        } catch (Exception e) {
            throw new CryptException(e.getLocalizedMessage());
        }
    }

    public static String encryptHex(String rawData) throws CryptException {
        return Hex.encodeHexString(encryptBytes(rawData));
    }

    public static String decryptHex(String hexData) throws CryptException {
        try {
            return new String(decryptBytes(Hex.decodeHex(hexData)));
        } catch (DecoderException e) {
            throw new CryptException(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) throws CryptException {
        String decrypt = CryptoUtils.decrypt("sauitFCYcPc=");
        System.out.println(decrypt);
        System.out.println("******************************************************");

        String s = Base64Utils.encodeToString("kkkkkkkkk".getBytes());
        System.out.println(s);
        System.out.println(new String(Base64Utils.decodeFromString(s)));

    }

}
