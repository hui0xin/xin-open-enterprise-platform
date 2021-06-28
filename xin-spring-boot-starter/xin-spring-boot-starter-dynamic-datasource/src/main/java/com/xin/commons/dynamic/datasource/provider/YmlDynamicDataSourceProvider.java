package com.xin.commons.dynamic.datasource.provider;

import com.xin.commons.dynamic.datasource.DynamicDataSourceCreator;
import com.xin.commons.dynamic.datasource.autoconfigure.DataSourceProperty;
import com.xin.commons.dynamic.datasource.autoconfigure.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * YML数据源提供者
 */
@Slf4j
public class YmlDynamicDataSourceProvider implements DynamicDataSourceProvider {

    /**
     * 多数据源参数
     */
    private DynamicDataSourceProperties properties;
    /**
     * 多数据源创建器
     */
    private DynamicDataSourceCreator dynamicDataSourceCreator;

    public YmlDynamicDataSourceProvider(DynamicDataSourceProperties properties, DynamicDataSourceCreator dynamicDataSourceCreator) {
        this.properties = properties;
        this.dynamicDataSourceCreator = dynamicDataSourceCreator;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> dataSourcePropertiesMap = properties.getDatasource();
        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size());
        for (Map.Entry<String, DataSourceProperty> item : dataSourcePropertiesMap.entrySet()) {
            dataSourceMap.put(item.getKey(), dynamicDataSourceCreator.createDataSource(item.getValue()));
        }
        return dataSourceMap;
    }
}
