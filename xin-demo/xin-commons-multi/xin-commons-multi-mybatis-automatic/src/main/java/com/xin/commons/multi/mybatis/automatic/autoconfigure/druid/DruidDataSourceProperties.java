package com.xin.commons.multi.mybatis.automatic.autoconfigure.druid;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Properties;

/**
 * Druid常用参数
 */
@Data
@Accessors(chain = true)
public class DruidDataSourceProperties {

    private Integer initialSize;
    private Integer maxActive;
    private Integer minIdle;
    private Long maxWait;
    private Long timeBetweenEvictionRunsMillis;
    private Long minEvictableIdleTimeMillis;
    private Long maxEvictableIdleTimeMillis;
    private String validationQuery;
    private Integer validationQueryTimeout;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean testWhileIdle;
    private Boolean poolPreparedStatements;
    private Integer maxOpenPreparedStatements;
    private Boolean sharePreparedStatements;
    private Properties connectionProperties;
    private String filters;
}