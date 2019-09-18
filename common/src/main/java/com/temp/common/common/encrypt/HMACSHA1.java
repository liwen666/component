package com.temp.common.common.encrypt;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author liq
 */
public class HMACSHA1 {
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    public static final String key = "A96EE1E3E47D5DE79A53430BF28979EB";

    /**
     * HMACSHA1 是从 SHA1 哈希函数构造的一种键控哈希算法，被用作 HMAC（基于哈希的消息验证代码）。 此 HMAC
     * 进程将密钥与消息数据混合，使用哈希函数对混合结果进行哈希计算，将所得哈希值与该密钥混合，然后再次应用哈希函数。 输出的哈希值长度为 160 位。
     *
     * 在发送方和接收方共享机密密钥的前提下，HMAC 可用于确定通过不安全信道发送的消息是否已被篡改。
     * 发送方计算原始数据的哈希值，并将原始数据和哈希值放在一个消息中同时传送。 接收方重新计算所接收消息的哈希值，并检查计算所得的 HMAC 是否与传送的
     * HMAC 匹配。
     *
     * 因为更改消息和重新生成正确的哈希值需要密钥，所以对数据或哈希值的任何更改都会导致不匹配。
     * 因此，如果原始的哈希值与计算得出的哈希值相匹配，则消息通过身份验证。
     *
     * SHA-1（安全哈希算法，也称为 SHS、安全哈希标准）是由美国政府发布的一种加密哈希算法。 它将从任意长度的字符串生成 160 位的哈希值。
     *
     * HMACSHA1 接受任何大小的密钥，并产生长度为 160 位的哈希序列。
     */

    /*
     * 展示了一个生成指定算法密钥的过程 初始化HMAC密钥
     *
     * @return
     *
     * @throws Exception
     *
     * public static String initMacKey() throws Exception { //得到一个 指定算法密钥的密钥生成器
     * KeyGenerator KeyGenerator keyGenerator =KeyGenerator.getInstance(MAC_NAME);
     * //生成一个密钥 SecretKey secretKey =keyGenerator.generateKey(); return null; }
     */

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    public static byte[] HmacSHA1Encrypt(String encryptText, byte[] bEncryptKey) throws Exception {
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(bEncryptKey, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

}
