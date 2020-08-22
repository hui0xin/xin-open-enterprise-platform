package com.xin.oauth2.common.utils;


import java.security.MessageDigest;
import java.util.Random;

public class MD5Util {
	private MD5Util() {};

	private static int len = 20;
	
	//  设置字符
	public static final char[] chars = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();

	// 设置随机数
	private static Random random = new Random();

	public static final String getSalt() {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (int i = 0; i < len; i++) {
			index = random.nextInt(chars.length); // 获取随机chars下标
			sb.append(chars[index]);
		}
		return sb.toString();
	}

	
	 /** 
     * 对字符串md5加密(大写+数字) 
     * 
     * @param str 传入要加密的字符串 
     * @return  MD5加密后的字符串 
     */  
      
    public static final String MD5(String s) {  
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};         
  
        try {  
            byte[] btInput = s.getBytes();  
            // 获得MD5摘要算法的 MessageDigest 对象  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
            // 使用指定的字节更新摘要  
            mdInst.update(btInput);  
            // 获得密文  
            byte[] md = mdInst.digest();  
            // 把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    } 
  
}
