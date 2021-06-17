package com.xin.commons.multi.mybatis.automatic.strategy;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机策略
 * @since 1.0.0
 */
public class RandomDynamicDataSourceStrategy implements DynamicDataSourceStrategy {

    @Override
    public DataSource determineDataSource(List<DataSource> dataSources) {
        return dataSources.get(ThreadLocalRandom.current().nextInt(dataSources.size()));
    }
}
