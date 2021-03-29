package com.xin.commons.multi.mybatis.annotation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/** 
 * 多数据源配置类 
 */
@Configuration  
public class DataSourceConfig {

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    /** 数据源1 */
    @Bean(name = "dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource1() {

        return DataSourceBuilder.create().type(this.dataSourceType).build();
    }
  
    /** 数据源2 */
    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource dataSource2() {

        return DataSourceBuilder.create().type(this.dataSourceType).build();
    }

    /** 数据源3 */
    @Bean(name = "dataSource3")
    @ConfigurationProperties(prefix = "spring.datasource.db3")
    public DataSource dataSource3() {

        return DataSourceBuilder.create().type(this.dataSourceType).build();
    }

    /** 
     * 动态数据源: 通过AOP在不同数据源之间动态切换 
     */
    @Primary  
    @Bean(name = "dynamicDataSource")  
    public DataSource dynamicDataSource() {  
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源  
        dynamicDataSource.setDefaultTargetDataSource(dataSource1());
        // 配置多数据源  
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put(DBConfig.dataSource1.name(), dataSource1());
        dsMap.put(DBConfig.dataSource2.name(), dataSource2());
        dsMap.put(DBConfig.dataSource3.name(), dataSource3());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;  
    }

    /**
     * 配置@Transactional注解事物 
     */
    @Bean  
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());  
    }  
}  