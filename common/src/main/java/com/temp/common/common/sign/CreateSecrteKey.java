package com.temp.common.common.sign; /**
 * 引进的包都是Java自带的jar包
 * 秘钥相关包
 * base64 编解码
 * 这里只用到了编码
 */

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class CreateSecrteKey {


    public class Keys {

    }

    public static final String KEY_ALGORITHM = "RSA";
    //public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    //获得公钥
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得私钥
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //byte[] privateKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    //map对象中存放公私钥
    public static Map<String, Object> initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static void main(String[] args) {
        Map<String, Object> keyMap;
        try {
            keyMap = initKey();
            String publicKey = getPublicKey(keyMap);
            System.out.println(publicKey);
            String privateKey = getPrivateKey(keyMap);
            System.out.println(privateKey);
            String signData = sign("待校验数据", privateKey);
            boolean result = verify("待校验数据", signData, publicKey);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    public static String sign(String data, String privateKeyString) throws Exception {
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyf.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKeyString)));
        java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
        signet.initSign(privateKey);
        signet.update(data.getBytes("utf-8"));
        byte[] signed = signet.sign();
        return Base64.encode(signed);
    }

    /**
     * 使用公钥判断签名是否与数据匹配
     * @param data 数据
     * @param sign 签名
     * @param publicKeyString 公钥
     * @return 是否篡改了数据
     */
    public static boolean verify(String data, String sign, String publicKeyString) throws Exception {
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyf.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKeyString)));
        java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
        signet.initVerify(publicKey);
        signet.update(data.getBytes("utf-8"));
        return signet.verify(Base64.decode(sign));
    }
}