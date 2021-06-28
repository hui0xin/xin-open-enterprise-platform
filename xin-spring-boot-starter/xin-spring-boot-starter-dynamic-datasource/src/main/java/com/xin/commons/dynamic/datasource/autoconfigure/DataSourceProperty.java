package com.xin.commons.dynamic.datasource.autoconfigure;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import com.xin.commons.dynamic.datasource.autoconfigure.druid.DruidDataSourceProperties;

import javax.sql.DataSource;

@Data
@Accessors(chain = true)
public class DataSourceProperty {

    /**
     * 连接池类型，如果不设置自动查找 Druid > HikariCp
     */
    private Class<? extends DataSource> type;
    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;
    /**
     * Druid参数配置
     */
    @NestedConfigurationProperty
    private DruidDataSourceProperties druid = new DruidDataSourceProperties();
}
