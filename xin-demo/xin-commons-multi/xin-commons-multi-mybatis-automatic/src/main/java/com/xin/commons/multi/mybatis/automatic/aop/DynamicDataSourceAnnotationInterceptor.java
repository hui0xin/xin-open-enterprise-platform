package com.xin.commons.multi.mybatis.automatic.aop;

import com.xin.commons.multi.mybatis.automatic.annotation.DS;
import com.xin.commons.multi.mybatis.automatic.spel.DynamicDataSourceSpelParser;
import com.xin.commons.multi.mybatis.automatic.spel.DynamicDataSourceSpelResolver;
import com.xin.commons.multi.mybatis.automatic.support.DynamicDataSourceClassResolver;
import com.xin.commons.multi.mybatis.automatic.utils.DynamicDataSourceContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.reflect.Method;
import lombok.Setter;

/**
 * 动态数据源AOP核心拦截器
 */
public class DynamicDataSourceAnnotationInterceptor implements MethodInterceptor {

    /**
     * SPEL参数标识
     */
    private static final String SPEL_PREFIX = "#";

    @Setter
    private DynamicDataSourceSpelResolver dynamicDataSourceSpelResolver;

    @Setter
    private DynamicDataSourceSpelParser dynamicDataSourceSpelParser;

    private DynamicDataSourceClassResolver dynamicDataSourceClassResolver = new DynamicDataSourceClassResolver();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            DynamicDataSourceContextHolder.setDataSourceLookupKey(determineDatasource(invocation));
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceLookupKey();
        }
    }

    private String determineDatasource(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Class<?> declaringClass = dynamicDataSourceClassResolver.targetClass(invocation);
        DS ds = method.isAnnotationPresent(DS.class) ? method.getAnnotation(DS.class) : AnnotationUtils.findAnnotation(declaringClass, DS.class);
        String value = ds.value();
        if (!value.isEmpty() && value.startsWith(SPEL_PREFIX)) {
            String spelValue = dynamicDataSourceSpelParser.parse(invocation, value);
            return dynamicDataSourceSpelResolver.resolve(spelValue);
        }
        return value;
    }
}