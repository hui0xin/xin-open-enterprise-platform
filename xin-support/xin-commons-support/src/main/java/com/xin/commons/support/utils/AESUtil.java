package com.xin.commons.support.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.Validate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES加解密算法工具类
 * @author xin
 **/
public class AESUtil {
    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key     加密密钥
     * @return String 加密后的字节数组
     */
    public static String encrypt(final String content, final String key) {
        Validate.notNull(content, "content参数不能为null");
        Validate.notNull(key, "key参数不能为null");

        try {
            final byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            final byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));

            // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new Base64().encodeToString(encrypted);
        } catch (NoSuchPaddingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param key     解密密钥
     * @return String
     */
    public static String decrypt(final String content, final String key) {
        Validate.notNull(content, "content参数不能为null");
        Validate.notNull(key, "key参数不能为null");

        try {
            final byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            final byte[] encrypted1 = new Base64().decode(content);
            final byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException
                | InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }
    }
}
