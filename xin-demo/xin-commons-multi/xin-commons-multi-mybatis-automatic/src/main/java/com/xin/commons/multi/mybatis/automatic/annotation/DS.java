package com.xin.commons.multi.mybatis.automatic.annotation;

import java.lang.annotation.*;

/**
 * 注解在类上或方法上来切换数据源
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {

    /**
     * 组名或者具体数据源名称或者spel参数(#开头)
     * @return 数据源名称
     */
    String value();
}