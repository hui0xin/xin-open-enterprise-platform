package com.${packageName}.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @Description: stringUtil demo
 * @author: 系统
 */
@Slf4j
public class StringUtil {

    /**
     * String 转 任何类型 ，转换不了 则返回空
     */
    public static <T> T getTValueElseNull(String text, Class<T> clazz) {
        try {
            return JSON.parseObject(text, clazz, new Feature[0]);
        } catch (Exception ex) {
            log.error("String转换类型 class 失败: value: {} ,type: {},ex:{}", text, clazz.toString(), ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }
}