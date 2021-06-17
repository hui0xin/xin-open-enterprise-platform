package com.xin.commons.multi.mybatis.automatic.spel;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 动态数据源SPEL解析器
 * @since 2.3.3
 */
public interface DynamicDataSourceSpelParser {

    /**
     * 解析SPEL
     *
     * @param invocation 动态代理方法对象
     * @param key        ds的值
     * @return 解析SPEL后获取的数据源名称
     */
    String parse(MethodInvocation invocation, String key);
}