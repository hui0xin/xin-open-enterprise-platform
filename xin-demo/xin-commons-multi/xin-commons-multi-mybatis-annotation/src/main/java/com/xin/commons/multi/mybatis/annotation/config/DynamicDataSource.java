package com.xin.commons.multi.mybatis.annotation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

/** 
 * 当前数据源
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /** 线程本地环境*/
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /** 设置数据源类型*/
    public static void setDataSource(String dataSource) {
        Assert.notNull(dataSource, "dataSource cannot be null");
        contextHolder.set(dataSource);
        log.info("[this.set] datasource====》{}",dataSource);
    }

    /** 获取数据源类型*/
    public static String getDataSource() {

        return contextHolder.get();
    }

    /** 清除数据源类型*/
    public static void clearDataSource() {
        log.info("[this.remove] datasource====》{}",contextHolder.get());
        contextHolder.remove();

    }
    
    @Override  
    protected Object determineCurrentLookupKey() {
        log.info("当前数据源为:{}",this.getDataSource());
        return this.getDataSource();
    }  
} 