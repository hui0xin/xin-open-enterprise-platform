package com.xin.commons.multi.shardingjdbc.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 *  数据源及分表配置
 */
@Configuration
@MapperScan(basePackages = "com.xin.commons.multi.shardingjdbc.mapper",
        sqlSessionTemplateRef  = "perpetualSqlSessionTemplate")
public class DataSourceConfig {

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    /**
     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     */
    @Bean(name="dataSource0")
    @ConfigurationProperties(prefix = "spring.datasource.db0")
    public DataSource dataSource0(){

        return DataSourceBuilder.create().type(this.dataSourceType).build();
    }
    /**
     * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     */

    @Bean(name="dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource1(){

        return DataSourceBuilder.create().type(this.dataSourceType).build();
    }

    /**
     * 配置数据源规则，即将多个数据源交给sharding-jdbc管理，并且可以设置默认的数据源，
     * 当表没有配置分库规则时会使用默认的数据源
     */
    @Bean
    public DataSourceRule dataSourceRule(@Qualifier("dataSource0") DataSource dataSource0,
                                         @Qualifier("dataSource1") DataSource dataSource1){
        Map<String, DataSource> dataSourceMap = new HashMap<>(); //设置分库映射
        dataSourceMap.put("dataSource0", dataSource0);
        dataSourceMap.put("dataSource1", dataSource1);
        /**设置默认库，两个库以上时必须设置默认库。默认库的数据源名称必须是dataSourceMap的key之一*/
        return new DataSourceRule(dataSourceMap, "dataSource0");
    }

    /**
     * 配置数据源策略和表策略，具体策略需要自己实现
     */
    @Bean
    public ShardingRule shardingRule(DataSourceRule dataSourceRule){

        //表策略
        TableRule orderTableRule = TableRule.builder("perpetual_coin")
                .actualTables(Arrays.asList("perpetual_coin_0", "perpetual_coin_1"))
                .tableShardingStrategy(new TableShardingStrategy("symbol", new ModuloTableShardingAlgorithm()))
                .dataSourceRule(dataSourceRule)
                .build();
//        TableRule orderItemTableRule = TableRule.builder("t_order_item")
//                .actualTables(Arrays.asList("t_order_item_0", "t_order_item_1"))
//                .tableShardingStrategy(new TableShardingStrategy("symbol", new ModuloTableShardingAlgorithm()))
//                .dataSourceRule(dataSourceRule)
//                .build();
        /**
         * 绑定表策略，在查询时会使用主表策略计算路由的数据源，
         * 因此需要约定绑定表策略的表的规则需要一致，可以一定程度提高效率
         */
        //List<BindingTableRule> bindingTableRules = new ArrayList<>();
        //bindingTableRules.add(new BindingTableRule(Arrays.asList(orderTableRule, orderItemTableRule)));
        return ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))//, orderItemTableRule))
                //.bindingTableRules(bindingTableRules)
                .databaseShardingStrategy(new DatabaseShardingStrategy("symbol", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("symbol", new ModuloTableShardingAlgorithm()))
                .build();
    }

    /**
     * 创建sharding-jdbc的数据源DataSource，MybatisAutoConfiguration会使用此数据源
     */
    @Bean(name="dataSource")
    public DataSource shardingDataSource(ShardingRule shardingRule) throws SQLException {
        return ShardingDataSourceFactory.createDataSource(shardingRule);
    }

    /**
     * 需要手动配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactitonManager(@Qualifier("dataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "perpetualSqlSessionFactory")
    @Primary
    public SqlSessionFactory perpetualSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper/**/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "perpetualSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate perpetualSqlSessionTemplate(@Qualifier("perpetualSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
