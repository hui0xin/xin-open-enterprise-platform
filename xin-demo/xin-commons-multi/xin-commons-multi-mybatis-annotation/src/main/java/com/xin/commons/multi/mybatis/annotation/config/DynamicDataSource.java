package com.xin.commons.multi.mybatis.annotation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/** 
 * 当前数据源
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /** 线程本地环境*/
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /** 设置数据源类型*/
    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
        log.info("设置数据源为：{}",dataSource);
    }

    /** 清除数据源类型*/
    public static void clearDataSource() {
        contextHolder.remove();
    }
    
    @Override  
    protected Object determineCurrentLookupKey() {
        log.info("当前数据源为：{}",contextHolder.get());
        return contextHolder.get();
    }  
} 