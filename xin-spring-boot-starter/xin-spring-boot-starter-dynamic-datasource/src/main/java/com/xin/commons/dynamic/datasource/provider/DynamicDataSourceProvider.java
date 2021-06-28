package com.xin.commons.dynamic.datasource.provider;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 多数据源加载接口，默认的实现为从yml信息中加载所有数据源
 * 你可以自己实现从其他地方加载所有数据源
 *
 * @see YmlDynamicDataSourceProvider
 * @see AbstractJdbcDataSourceProvider
 */
public interface DynamicDataSourceProvider {

    /**
     * 加载所有数据源
     *
     * @return 所有数据源，key为数据源名称
     */
    Map<String, DataSource> loadDataSources();
}
