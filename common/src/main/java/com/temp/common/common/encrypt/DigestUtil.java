package com.temp.common.common.encrypt;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Slf4j
public class DigestUtil {

    /**
     * hmacSha256加密算法
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String hmacSHA256(String data, String key) throws Exception {

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
           // return sb.toString().toUpperCase();
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base64编码
     * @param data
     * @return
     */
    public static String base64Encode(String data) {

        try {
            return Base64.getEncoder().encodeToString(data.getBytes("utf-8"));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base64解码
     * @param data
     * @return
     */
    public static String base64Decode(String data){
        try{
            return new String( Base64.getDecoder().decode(data), "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 创建签名，参数按asc码排序，签名，base编码
     * @param map
     * @param secretKey
     * @return
     */
    public static String createSign(Map<String,String > map, String secretKey) {

        log.info("==========createSign:"  + JSON.toJSONString(map) + ";secretKey:" + secretKey );

        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).compareTo(o2.getKey());
                }
            });

            //构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append("&" + key + "=" + val);
                    }
                }
            }
            String params = sb.toString().replaceFirst("&", "");
            log.info("==========createSign:params:"  + params );
            String sign =  hmacSHA256(params, secretKey);
            return base64Encode(sign);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createSign_upper(Map<String,String > map, String secretKey) {

        log.info("==========createSign:"  + JSON.toJSONString(map) + ";secretKey:" + secretKey );

        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).compareTo(o2.getKey());
                }
            });

            //构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append("&" + key + "=" + val);
                    }
                }
            }
            String params = sb.toString().replaceFirst("&", "");
            log.info("==========createSign:params:"  + params );
            String sign =  hmacSHA256(params, secretKey);
            return sign.toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
