package com.xin.commons.dynamic.datasource.spel;

/**
 * 动态数据源SPEL处理器
 */
public interface DynamicDataSourceSpelResolver {

    /**
     * 处理spel解析后的参数
     *
     * @param spelValue spel参数
     * @return 返回数据源名称
     */
    String resolve(String spelValue);
}
