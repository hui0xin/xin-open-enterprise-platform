package com.xin.commons.support.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import lombok.extern.slf4j.Slf4j;

/**
 * DES加密和解密工具,可以对字符串进行加密和解密操作
 * @author xin
 **/
@Slf4j
public class DesUtil {
    /**
     * 默认键值
     */
    private static final String strDefaultKey = "ok!@#$()";

    /**
     * 加密工具
     */
    private static Cipher encryptCipher = null;

    /**
     * 解密工具
     */
    private static Cipher decryptCipher = null;

    static {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            final Key key = getKey(strDefaultKey.getBytes());

            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (final Exception e) {
            throw new RuntimeException("DesUtil init static code block error", e);
        }
    }

    /**
     * 加密字符串
     *
     * @param str 需加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(final String str) {
        return HexUtil.encodeHex(encrypt(str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 加密字节数组
     *
     * @param data 需加密的字节数组
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(final byte[] data) {
        try {
            return encryptCipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("encrypt error", e);
        }
    }

    /**
     * 解密字符串
     *
     * @param str 需解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(final String str) {
        try {
            return new String(decrypt(HexUtil.decodeHex(str)), StandardCharsets.UTF_8);
        } catch (final Exception e) {
            throw new RuntimeException("decrypt error", e);
        }
    }

    /**
     * 解密字节数组
     *
     * @param data 需解密的字节数组
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(final byte[] data) {
        try {
            return decryptCipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("decrypt error", e);
        }
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param data 构成该字符串的字节数组
     * @return 生成的密钥
     */
    public static Key getKey(final byte[] data) {
        // 创建一个空的8位字节数组（默认值为0）
        // 将原始字节数组转换为8位
        final byte[] bytes = Arrays.copyOf(data, 8);
        return new javax.crypto.spec.SecretKeySpec(bytes, "DES");
    }
}