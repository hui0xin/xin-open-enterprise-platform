package com.xin.commons.multi.mybatis.automatic.autoconfigure;

import com.xin.commons.multi.mybatis.automatic.autoconfigure.druid.DruidGlobalDataSourceProperties;
import com.xin.commons.multi.mybatis.automatic.strategy.DynamicDataSourceStrategy;
import com.xin.commons.multi.mybatis.automatic.strategy.LoadBalanceDynamicDataSourceStrategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.Ordered;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DynamicDataSourceProperties
 *
 * @see DataSourceProperties
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource.dynamic")
public class DynamicDataSourceProperties {

    /**
     * 必须设置默认的库,默认master
     */
    private String primary = "master";
    /**
     * 每一个数据源
     */
    private Map<String, DataSourceProperty> datasource = new LinkedHashMap<>();
    /**
     * 多数据源选择算法clazz，默认负载均衡算法
     */
    private Class<? extends DynamicDataSourceStrategy> strategy = LoadBalanceDynamicDataSourceStrategy.class;
    /**
     * aop切面顺序，默认优先级最高
     */
    private Integer order = Ordered.HIGHEST_PRECEDENCE;
    /**
     * Druid全局参数配置
     */
    @NestedConfigurationProperty
    private DruidGlobalDataSourceProperties druid = new DruidGlobalDataSourceProperties();
}
