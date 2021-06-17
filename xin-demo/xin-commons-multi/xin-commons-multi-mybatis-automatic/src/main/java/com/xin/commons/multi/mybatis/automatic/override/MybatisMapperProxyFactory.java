package com.xin.commons.multi.mybatis.automatic.override;

import lombok.Getter;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>从 {@link MapperProxyFactory} copy 过来 </p>
 * <li> 使用 MybatisMapperMethod </li>
 * @since 2018-06-09
 */
public class MybatisMapperProxyFactory<T> {
    
    @Getter
    private final Class<T> mapperInterface;
    @Getter
    private final Map<Method, MybatisMapperProxy.MapperMethodInvoker> methodCache = new ConcurrentHashMap<>();
    
    public MybatisMapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    protected T newInstance(MybatisMapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

    public T newInstance(SqlSession sqlSession) {
        final MybatisMapperProxy<T> mapperProxy = new MybatisMapperProxy<>(sqlSession, mapperInterface, methodCache);
        return newInstance(mapperProxy);
    }
}
