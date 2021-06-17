package com.xin.commons.multi.mybatis.automatic.support;

import com.xin.commons.multi.mybatis.automatic.provider.DynamicDataSourceProvider;
import com.xin.commons.multi.mybatis.automatic.strategy.DynamicDataSourceStrategy;
import com.xin.commons.multi.mybatis.automatic.utils.DynamicDataSourceContextHolder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 核心动态数据源组件
 * @since 1.0.0
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean {


    private static final String UNDERLINE = "_";

    /**
     * 所有数据库
     */
    protected Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
    /**
     * 分组数据库
     */
    protected Map<String, DynamicGroupDataSource> groupDataSources = new HashMap<>();

    @Setter
    protected DynamicDataSourceProvider provider;

    @Setter
    protected Class<? extends DynamicDataSourceStrategy> strategy;

    @Setter
    protected String primary;

    @Override
    public DataSource determineDataSource() {
        String lookupKey = DynamicDataSourceContextHolder.getDataSourceLookupKey();
        if (StringUtils.isEmpty(lookupKey)) {
            return determinePrimaryDataSource();
        } else if (!groupDataSources.isEmpty() && groupDataSources.containsKey(lookupKey)) {
            log.debug("从 {} 组数据源中返回数据源", lookupKey);
            return groupDataSources.get(lookupKey).determineDataSource();
        } else if (dataSourceMap.containsKey(lookupKey)) {
            log.debug("从 {} 单数据源中返回数据源", lookupKey);
            return dataSourceMap.get(lookupKey);
        }
        return determinePrimaryDataSource();
    }

    private DataSource determinePrimaryDataSource() {
        log.debug("从默认数据源中返回数据");
        return groupDataSources.containsKey(primary) ? groupDataSources.get(primary).determineDataSource() : dataSourceMap.get(primary);
    }

    /**
     * 添加数据源
     *
     * @param ds         数据源名称
     * @param dataSource 数据源
     */
    public synchronized void addDataSource(String ds, DataSource dataSource) {
        dataSourceMap.put(ds, dataSource);
        if (ds.contains(UNDERLINE)) {
            String group = ds.split(UNDERLINE)[0];
            if (groupDataSources.containsKey(group)) {
                groupDataSources.get(group).addDatasource(dataSource);
            } else {
                try {
                    DynamicGroupDataSource groupDatasource = new DynamicGroupDataSource(group, strategy.newInstance());
                    groupDatasource.addDatasource(dataSource);
                    groupDataSources.put(group, groupDatasource);
                } catch (Exception e) {
                    log.error("添加数据源失败", e);
                    dataSourceMap.remove(ds);
                }
            }
        }
        log.info("动态数据源-加载 {} 成功", ds);
    }

    /**
     * 删除数据源
     *
     * @param ds 数据源名称
     */
    public synchronized void removeDataSource(String ds) {
        if (dataSourceMap.containsKey(ds)) {
            DataSource dataSource = dataSourceMap.get(ds);
            dataSourceMap.remove(ds);
            if (ds.contains(UNDERLINE)) {
                String group = ds.split(UNDERLINE)[0];
                if (groupDataSources.containsKey(group)) {
                    groupDataSources.get(group).removeDatasource(dataSource);
                }
            }
            log.info("动态数据源-删除 {} 成功", ds);
        }
        log.warn("动态数据源-未找到 {} 数据源");
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, DataSource> dataSources = provider.loadDataSources();
        log.info("初始共加载 {} 个数据源", dataSources.size());
        //添加并分组数据源
        for (Map.Entry<String, DataSource> dsItem : dataSources.entrySet()) {
            addDataSource(dsItem.getKey(), dsItem.getValue());
        }
        //检测默认数据源设置
        if (groupDataSources.containsKey(primary)) {
            log.info("当前的默认数据源是组数据源,组名为 {} ，其下有 {} 个数据源", primary, groupDataSources.size());
        } else if (dataSourceMap.containsKey(primary)) {
            log.info("当前的默认数据源是单数据源，数据源名为 {}", primary);
        } else {
            throw new RuntimeException("请检查primary默认数据库设置");
        }
    }
}