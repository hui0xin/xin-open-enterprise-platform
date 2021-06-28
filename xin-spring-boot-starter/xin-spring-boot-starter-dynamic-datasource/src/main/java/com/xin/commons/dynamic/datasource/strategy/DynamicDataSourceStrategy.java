package com.xin.commons.dynamic.datasource.strategy;

import javax.sql.DataSource;
import java.util.List;

/**
 * 多数据源选择策略接口
 * 一般默认为负载均衡策略，默认提供了一个随机策略
 *
 * @see RandomDynamicDataSourceStrategy
 * @see LoadBalanceDynamicDataSourceStrategy
 */
public interface DynamicDataSourceStrategy {

    /**
     * 决定当前数据源
     *
     * @param dataSources 数据源选择库
     * @return dataSource 所选择的数据源
     */
    DataSource determineDataSource(List<DataSource> dataSources);
}
