package com.xin.oauth2.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {

	/**
	 * 加密
	 * @param str
	 * @return
	 */
	public static final String encode(String str) {
		String asB64 = null;
		try {
			asB64 = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			new RuntimeException(e);
		}
		return asB64;
	}
	
	/**
	 * 解码
	 * @param asB64
	 * @return
	 */
	public static final String decode(String asB64) {
		try {
			// 解码
			byte[] asBytes = Base64.getDecoder().decode(asB64);
			return (new String(asBytes, "utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			new RuntimeException(e);
		}
		return null;
	}
	
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String str = "xiangkui|2D591FB3825AC7BACA9FCE1A6AE283C0";
		String asB64 = encode(str);
		// 编码
		System.out.println(asB64); 

		// 解码
		String d = decode(asB64);
		String[] split = d.split("\\|");
		System.out.println(split[0] +" "+split[1]); // 输出为: some string
		
	}

}	
