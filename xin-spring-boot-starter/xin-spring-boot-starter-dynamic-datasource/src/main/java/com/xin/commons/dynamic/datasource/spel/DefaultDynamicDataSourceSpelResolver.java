package com.xin.commons.dynamic.datasource.spel;

/**
 * 默认的spel参数处理器
 */
public class DefaultDynamicDataSourceSpelResolver implements DynamicDataSourceSpelResolver {

    @Override
    public String resolve(String spelValue) {
        return spelValue;
    }
}
