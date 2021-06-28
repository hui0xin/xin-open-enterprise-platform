package com.xin.commons.dynamic.datasource.strategy;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机策略
 */
public class RandomDynamicDataSourceStrategy implements DynamicDataSourceStrategy {

    @Override
    public DataSource determineDataSource(List<DataSource> dataSources) {
        return dataSources.get(ThreadLocalRandom.current().nextInt(dataSources.size()));
    }
}
