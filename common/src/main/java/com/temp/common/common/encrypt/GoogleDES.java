package com.temp.common.common.encrypt;

import org.springframework.util.StringUtils;

public class GoogleDES {

	private static final String DES_KEY = "12345678";

	public static String encryptKey(String key) {
		String secretKey = null;
		if (!StringUtils.isEmpty(key)) {
			DESUtil des = new DESUtil(DES_KEY);
			secretKey = des.encryptStr(key);
		}
		return secretKey;
	}

	public static String decryptKey(String secretKey) {
		String key = null;
		if (!StringUtils.isEmpty(secretKey)) {
			DESUtil des = new DESUtil(DES_KEY);
			key = des.decryptStr(secretKey);
		}
		return key;
	}

}
