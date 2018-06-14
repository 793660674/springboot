package com.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zzy
 * @version 1.0
 * @since 1.0
 */
public class MD5Util {
	
	private static String encodingCharset = "UTF-8";

	/**
	 * MD5加密（标准）
	 * 
	 * @param str 需要加密的字符串
	 * @return String 加密后的字符串
	 */
	public static String md5(String str){
		return commonEncrypt(str, "MD5");
	}
	
	/**
	 * SHA1加密
	 * 
	 * @param str 需要加密的字符串
	 * @return String 加密后的字符串
	 */
	public static String sha1(String str){
		return commonEncrypt(str, "SHA");
	}
	
	/**
	 * 加密
	 * 
	 * @param str          加密字符串
	 * @param instanceType 加密类型
	 * @return String
	 */
	private static String commonEncrypt(String str, String instanceType){
		str = str.trim();
		byte value[];
		try {
			value = str.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = str.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(instanceType);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));
	}
	
	/**
	 * 十六进制转换
	 * 
	 * @param input
	 * @return String
	 */
	private static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}
	
}