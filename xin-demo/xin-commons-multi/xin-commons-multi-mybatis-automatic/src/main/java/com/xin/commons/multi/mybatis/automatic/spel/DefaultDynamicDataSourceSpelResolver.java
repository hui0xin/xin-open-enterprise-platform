package com.xin.commons.multi.mybatis.automatic.spel;

/**
 * 默认的spel参数处理器
 * 直接返回 spel处理后的参数
 * @since 2.3.0
 */
public class DefaultDynamicDataSourceSpelResolver implements DynamicDataSourceSpelResolver {

    @Override
    public String resolve(String spelValue) {
        return spelValue;
    }
}
