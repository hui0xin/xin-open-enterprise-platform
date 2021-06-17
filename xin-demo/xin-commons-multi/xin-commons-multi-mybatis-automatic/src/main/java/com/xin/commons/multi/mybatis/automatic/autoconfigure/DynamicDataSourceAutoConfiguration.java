package com.xin.commons.multi.mybatis.automatic.autoconfigure;

import com.xin.commons.multi.mybatis.automatic.aop.DynamicDataSourceAnnotationAdvisor;
import com.xin.commons.multi.mybatis.automatic.aop.DynamicDataSourceAnnotationInterceptor;
import com.xin.commons.multi.mybatis.automatic.autoconfigure.druid.DruidDynamicDataSourceConfiguration;
import com.xin.commons.multi.mybatis.automatic.provider.DynamicDataSourceProvider;
import com.xin.commons.multi.mybatis.automatic.provider.YmlDynamicDataSourceProvider;
import com.xin.commons.multi.mybatis.automatic.spel.DefaultDynamicDataSourceSpelParser;
import com.xin.commons.multi.mybatis.automatic.spel.DefaultDynamicDataSourceSpelResolver;
import com.xin.commons.multi.mybatis.automatic.spel.DynamicDataSourceSpelParser;
import com.xin.commons.multi.mybatis.automatic.spel.DynamicDataSourceSpelResolver;
import com.xin.commons.multi.mybatis.automatic.support.DynamicDataSourceCreator;
import com.xin.commons.multi.mybatis.automatic.support.DynamicRoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 动态数据源核心自动配置类
 * @see DynamicDataSourceProvider
 * @see DynamicRoutingDataSource
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Import(DruidDynamicDataSourceConfiguration.class)
public class DynamicDataSourceAutoConfiguration {

    @Autowired
    private DynamicDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider(DynamicDataSourceCreator dynamicDataSourceCreator) {
        return new YmlDynamicDataSourceProvider(properties, dynamicDataSourceCreator);
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceCreator dynamicDataSourceCreator() {
        DynamicDataSourceCreator dynamicDataSourceCreator = new DynamicDataSourceCreator();
        dynamicDataSourceCreator.setDruidProperties(properties.getDruid());
        return dynamicDataSourceCreator;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRoutingDataSource dynamicRoutingDataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setPrimary(properties.getPrimary());
        dynamicRoutingDataSource.setStrategy(properties.getStrategy());
        dynamicRoutingDataSource.setProvider(dynamicDataSourceProvider);
        return dynamicRoutingDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor(DynamicDataSourceSpelParser dynamicDataSourceSpelParser, DynamicDataSourceSpelResolver dynamicDataSourceSpelResolver) {
        DynamicDataSourceAnnotationInterceptor interceptor = new DynamicDataSourceAnnotationInterceptor();
        interceptor.setDynamicDataSourceSpelParser(dynamicDataSourceSpelParser);
        interceptor.setDynamicDataSourceSpelResolver(dynamicDataSourceSpelResolver);
        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(interceptor);
        advisor.setOrder(properties.getOrder());
        return advisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceSpelParser dynamicDataSourceSpelParser() {
        return new DefaultDynamicDataSourceSpelParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceSpelResolver dynamicDataSourceSpelResolver() {
        return new DefaultDynamicDataSourceSpelResolver();
    }
}