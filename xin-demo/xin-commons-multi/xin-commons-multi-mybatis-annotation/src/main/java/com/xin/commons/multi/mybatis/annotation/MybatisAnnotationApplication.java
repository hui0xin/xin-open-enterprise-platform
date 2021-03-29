package com.xin.commons.multi.mybatis.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 注意，多数剧院配置的时候，如果不排除 DataSourceAutoConfiguration
 * 会出现循环依赖问题
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MybatisAnnotationApplication {

    public static void main(String[] args) {

        SpringApplication.run(MybatisAnnotationApplication.class, args);
    }
}

