package com.xin.commons.support.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密Utils
 * @author: xin
 */
@Slf4j
public class MD5Util {
    public static final String KEY_MD5 = "MD5";

    public static String getResult(String inputStr) {
        log.debug("=======加密前的数据:" + inputStr);

        String result = null;

        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            byte[] digest = md.digest();
            result=byteArrayToHex(digest).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("MD5加密后:" + result);
        return result;
    }


    public static String byteArrayToHex(byte[] byteArray) {
        // 初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray =new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

        int index = 0;

        for (byte b : byteArray) {

            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];

            resultCharArray[index++] = hexDigits[b& 0xf];

        }

        // 字符数组组合成字符串返回

        return new String(resultCharArray);
    }

    public static byte[] encode2bytes(String source) {
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            result = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String MD5(String str) {
        String digest = null;
        StringBuffer buffer = new StringBuffer();

        try {
            MessageDigest digester = MessageDigest.getInstance("md5");
            byte[] digestArray = digester.digest(str.getBytes());

            for(int i = 0; i < digestArray.length; ++i) {
                buffer.append(String.format("%02x", digestArray[i]));
            }

            digest = buffer.toString();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return digest;
    }

  public static void main(String[] args)  {
      String result="JH07a99043f7adf107df905bd838d52209659e59f062c75f81259d22786d6c44aa1002110001111000170337358a94c9ba219b4355abae38d4af517d8d";
      String result1 = getResult(result);
      System.out.println(result1);
  }
}