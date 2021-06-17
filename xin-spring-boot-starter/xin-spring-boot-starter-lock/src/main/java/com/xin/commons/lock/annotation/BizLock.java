package com.xin.commons.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加锁注解
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface BizLock {

    /**
     * 锁的名称
     * @return key
     */
    String key() default "";

    /**
     * 尝试加锁，最多等待时间
     * @return waitTime
     */
    long waitTime() default Long.MIN_VALUE;

    /**
     *上锁以后xxx秒自动解锁
     * @return leaseTime
     */
    long leaseTime() default Long.MIN_VALUE;

}
