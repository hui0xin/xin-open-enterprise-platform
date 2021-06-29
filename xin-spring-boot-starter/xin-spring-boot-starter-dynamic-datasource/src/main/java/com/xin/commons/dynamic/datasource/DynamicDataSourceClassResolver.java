package com.xin.commons.dynamic.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * 获取对mybatis-plus的支持
 */
@Slf4j
public class DynamicDataSourceClassResolver {

    private boolean mpEnabled = false;

    private Field mapperInterfaceField;

    public DynamicDataSourceClassResolver() {
        Class<?> proxyClass = null;
        try {
            proxyClass = Class.forName("com.baomidou.mybatisplus.core.override.PageMapperProxy");
        } catch (ClassNotFoundException e) {
            try {
                proxyClass = Class.forName("org.apache.ibatis.binding.MapperProxy");
            } catch (ClassNotFoundException e1) {
            }
        }
        if (proxyClass != null) {
            try {
                mapperInterfaceField = proxyClass.getDeclaredField("mapperInterface");
                mapperInterfaceField.setAccessible(true);
                mpEnabled = true;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public Class<?> targetClass(MethodInvocation invocation) throws IllegalAccessException {
        if (mpEnabled) {
            Object target = invocation.getThis();
            return Proxy.isProxyClass(target.getClass()) ? (Class) mapperInterfaceField.get(Proxy.getInvocationHandler(target)) : target.getClass();
        }
        return invocation.getMethod().getDeclaringClass();
    }
}
