package com.xin.commons.dynamic.datasource.override;

import java.io.Serializable;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.ibatis.lang.UsesJava7;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;

public class PageMapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398559729838L;
    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, PageMapperMethod> methodCache;

    public PageMapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, PageMapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }

            if (this.isDefaultMethod(method)) {
                return this.invokeDefaultMethod(proxy, method, args);
            }
        } catch (Throwable var5) {
            throw ExceptionUtil.unwrapThrowable(var5);
        }

        PageMapperMethod mapperMethod = this.cachedMapperMethod(method);
        return mapperMethod.execute(this.sqlSession, args);
    }

    private PageMapperMethod cachedMapperMethod(Method method) {
        PageMapperMethod mapperMethod = (PageMapperMethod)this.methodCache.get(method);
        if (mapperMethod == null) {
            mapperMethod = new PageMapperMethod(this.mapperInterface, method, this.sqlSession.getConfiguration());
            this.methodCache.put(method, mapperMethod);
        }

        return mapperMethod;
    }

    @UsesJava7
    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        Constructor<Lookup> constructor = Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }

        Class<?> declaringClass = method.getDeclaringClass();
        return ((Lookup)constructor.newInstance(declaringClass, 15)).unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
    }

    private boolean isDefaultMethod(Method method) {
        return (method.getModifiers() & 1033) == 1 && method.getDeclaringClass().isInterface();
    }
}
