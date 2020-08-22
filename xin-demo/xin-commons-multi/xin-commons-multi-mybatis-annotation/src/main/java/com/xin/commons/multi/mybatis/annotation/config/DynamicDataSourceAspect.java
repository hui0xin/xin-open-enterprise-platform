package com.xin.commons.multi.mybatis.annotation.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** 
 * 自定义注解 + AOP的方式实现数据源动态切换。
 * 保证该AOP在@Transactional之前执行
 */
@Aspect  
@Component
@Order(-1)
@Slf4j
public class DynamicDataSourceAspect {

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, DataSource ds) throws Throwable {
        log.info("Use DataSource : {} > {}",ds.value().name(),point.getSignature());
        DynamicDataSource.setDataSource(ds.value().name());
        log.info("[annotation.set] datasource====》{}",ds.value());
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DataSource ds) {
        log.info("Revert DataSource : {} > {}",ds.value().name(),point.getSignature());
        DynamicDataSource.clearDataSource();
        log.info("[annotation.remove] datasource====》{}",ds.value().name());
    }

}  