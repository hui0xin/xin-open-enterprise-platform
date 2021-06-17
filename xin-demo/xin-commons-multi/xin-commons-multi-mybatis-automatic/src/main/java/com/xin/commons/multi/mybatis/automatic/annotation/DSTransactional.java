package com.xin.commons.multi.mybatis.automatic.annotation;

import java.lang.annotation.*;

/**
 * 事物
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DSTransactional {
}
