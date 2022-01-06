package com.xin.security.oauth2.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @Description:MD5加密
 */
@Component
public class PasswordEncoderUtil implements PasswordEncoder {

    /**
     * md5加密
     */
    @Override
    public String encode(CharSequence charSequence) {

        return MD5(charSequence.toString());
    }

    /**
     * 匹配规则
     */
    @Override
    public boolean matches(CharSequence charSequence , String s) {

        return MD5(charSequence.toString()).equals(s);
    }

    /**
     * md5加密过程
     */
    public static String MD5(String key) {

        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}