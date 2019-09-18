package com.temp.common.common.encrypt;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by liq on 2017/11/3.
 */
public class ByteUtil {

	/**
	 * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
	 */
	public static byte[] getBooleanArray(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}

	/**
	 * 把byte转为bit字符串
	 */
	public static String byteToBitString(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
				+ (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1)
				+ (byte) ((b >> 0) & 0x1);
	}

	/**
	 * 二进制字符串转byte
	 */
	public static byte decodeBinaryString(String byteStr) {
		int re, len;
		if (null == byteStr) {
			return 0;
		}
		len = byteStr.length();
		if (len != 4 && len != 8) {
			return 0;
		}
		if (len == 8) {// 8 bit处理
			if (byteStr.charAt(0) == '0') {// 正数
				re = Integer.parseInt(byteStr, 2);
			} else {// 负数
				re = Integer.parseInt(byteStr, 2) - 256;
			}
		} else {// 4 bit处理
			re = Integer.parseInt(byteStr, 2);
		}
		return (byte) re;
	}

	/**
	 * byte[]转换二进制字符串
	 */
	public static String bytesToBitString(byte[] b) {
		String temp = "";
		for (int i = 0; i < b.length; i++) {
			temp += ByteUtil.byteToBitString(b[i]);
		}
		return temp;
	}

	/**
	 * 字节合并
	 */
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	/**
	 * int转byte[]
	 */
	public static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

	/**
	 * 设置byte指定bit的值
	 */
	public static byte changeBit(byte b, int index, int value) {
		if (1 == value) {
			return (byte) (b | ((byte) 0x1 << (index % 8)));
		} else {
			return (byte) (b & (~((byte) 0x1 << (index % 8))));
		}
	}

	/**
	 * 判断某个数的第 i 位是0 还是 1
	 */
	public static boolean getBit(int num, int i) {
		return ((num & (1 << i)) != 0);// true 表示第i位为1,否则为0
	}

	/**
	 * 将字节转换成int
	 */
	public static int getIntByByte(byte b) {
		return Integer.parseInt(ByteUtil.byteToBitString(b), 2);
	}

	/**
	 * byte[]转换成hexString
	 */
	public static String bytes2hexString(byte[] b) {
		String bString = ByteUtil.bytesToBitString(b);
		if (StringUtils.isBlank(bString) || bString.length() % 8 != 0) {
			return null;
		}
		StringBuffer tmp = new StringBuffer();
		int iTmp;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * hexString转换成byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	// 高位在前，低位在后
	public static byte[] int2bytes(int num) {
		byte[] result = new byte[4];
		result[0] = (byte) ((num >>> 24) & 0xff);
		result[1] = (byte) ((num >>> 16) & 0xff);
		result[2] = (byte) ((num >>> 8) & 0xff);
		result[3] = (byte) ((num >>> 0) & 0xff);
		return result;
	}

	// 高位在前，低位在后
	public static int bytes2int(byte[] bytes) {
		int result = 0;
		if (bytes.length == 4) {
			int a = (bytes[0] & 0xff) << 24;
			int b = (bytes[1] & 0xff) << 16;
			int c = (bytes[2] & 0xff) << 8;
			int d = (bytes[3] & 0xff);
			result = a | b | c | d;
		}
		return result;
	}

	public static byte int2byte(int num) {
		return int2bytes(num)[3];
	}

	public static byte[] int2bytes(int num, int digit) {
		byte[] temp = int2bytes(num);
		byte[] result = new byte[digit];
		System.arraycopy(temp, 4 - digit, result, 0, digit);
		return result;
	}

	public static int convertInt(int high, int low) {
		byte[] bHigh = int2bytes(high, 2);
		byte[] bLow = int2bytes(low, 2);
		byte[] result = new byte[4];
		System.arraycopy(bHigh, 0, result, 0, 2);
		System.arraycopy(bLow, 0, result, 2, 2);
		return bytes2int(result);
	}
}
