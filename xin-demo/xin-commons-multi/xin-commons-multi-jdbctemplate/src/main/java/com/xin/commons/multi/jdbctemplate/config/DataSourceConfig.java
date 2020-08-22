package com.xin.commons.multi.jdbctemplate.config;

import javax.sql.DataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据源配置
 */
@Configuration
public class DataSourceConfig {

	@Primary
	@Bean(name = "mysqldatasource1")
	@ConfigurationProperties("spring.datasource.druid.mysql1")
	public DataSource dataSourceOne(){
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "mysqldatasource2")
	@ConfigurationProperties("spring.datasource.druid.mysql2")
	public DataSource dataSourceTwo(){
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "mysqlJdbcTemplate1")
	public JdbcTemplate primaryJdbcTemplate(
			@Qualifier("mysqldatasource1") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "mysqlJdbcTemplate2")
	public JdbcTemplate secondaryJdbcTemplate(
			@Qualifier("mysqldatasource2") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}


}
