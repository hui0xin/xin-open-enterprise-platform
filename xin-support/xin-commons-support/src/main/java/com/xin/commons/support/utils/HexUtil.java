package com.xin.commons.support.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;

/**
 *
 **/
@Slf4j
public class HexUtil {
    /**
     * 将byte数组转换为表示16进制值的字符串
     *
     * @param data 需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String encodeHex(final byte[] data) {
        if (data == null) {
            throw new NullPointerException("input argument 'data' is null");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("input argument 'data' length is zero");
        }
        return new String(Hex.encodeHex(data, false));
    }

    /**
     * 将表示16进制值的字符串转换为byte数组
     *
     * @param str 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] decodeHex(final String str) {
        Validate.notEmpty(str);
        try {
            return Hex.decodeHex(str.toCharArray());
        } catch (final DecoderException e) {
            log.error("decodeHex:{}", e);
            throw new RuntimeException("HexUtil.decodeHex error", e);
        }
    }
}
