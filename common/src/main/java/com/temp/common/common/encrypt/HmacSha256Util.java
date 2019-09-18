package com.temp.common.common.encrypt;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

@Slf4j
public class HmacSha256Util {

	public static void main(String[] args) {
		String encrypt = encrypt("x", "xxxx");
		System.out.println(encrypt);
	}
	
	private static String HMAC_SHA256 = "HmacSHA256";
	private static String UTF8 = "UTF-8";

	public static String encrypt(String value, String salt) {
		return encrypt(value, salt.getBytes(Charset.forName(UTF8)));
	}
	public static String encrypt(String value, byte[] salt) {
		String restul = null;
		try {
			Mac mac = Mac.getInstance(HMAC_SHA256);
			SecretKeySpec key = new SecretKeySpec(salt, HMAC_SHA256);
			mac.init(key);
			byte[] messageByte = value.getBytes(Charset.forName(UTF8));
			byte[] bytes = mac.doFinal(messageByte);
			restul = toHex(bytes);
		} catch (Exception e) {
			log.error("加密失败,算法:"+HMAC_SHA256+",原因:"+e.getMessage(), e);
		}
		return restul;
	}
	
	private static String toHex(byte input[]) {
	    if (input == null)
	      return null;
	    StringBuilder output = new StringBuilder(input.length * 2);
	    for (int i = 0; i < input.length; i++) {
	      int current = input[i] & 0xff;
	      if (current < 16)
	        output.append('0');
	      output.append(Integer.toString(current, 16));
	    }
	    return output.toString();
	  }

}
