package com.xin.commons.multi.mybatis.annotation.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** 
 * 自定义注解 + AOP的方式实现数据源动态切换。
 * 保证该AOP在@Transactional之前执行
 *
 * 连接点(Join point) 表示你想在什么地方插代码，比如方法执行的地方或者处理异常的地方
 * 切入点(Pointcut) 用来匹配一堆连接点
 * 通知(Advice) 在连接点上干的事儿(执行的代码)
 * 切面(Aspect) 切面文件里具体指明在哪个切入点执行什么通知(Advice)
 * 引入(Introduction) 给类新增方法或者成员
 * 目标对象(Target object) 在谁身上切
 * 织入(weaving) 将切面和目标对象绑一块儿
 *
 * before(前置通知)： 在方法开始执行前执行
 * after(后置通知)： 在方法执行后执行
 * afterReturning(返回后通知)： 在方法返回后执行
 * afterThrowing(异常通知)： 在抛出异常时执行
 * around(环绕通知)： 在方法执行前和执行后都会执行
 *
 * 执行顺序 around > before > around > after > afterReturning
 */
@Aspect  
@Component
@Order(-1)
@Slf4j
public class DynamicDataSourceAspect{

    //这种方式是拦截 类的方法
    //@Pointcut("execution(* com.xin.commons.multi.mybatis.annotation.service.impl..*.*(..))")
    //public void aspect() {}

    //1）execution(public * *(..))——表示匹配所有public方法
    //2）execution(* set*(..))——表示所有以“set”开头的方法
    //3）execution(* com.xyz.service.AccountService.*(..))——表示匹配所有AccountService接口的方法
    //4）execution(* com.xyz.service.*.*(..))——表示匹配service包下所有的方法
    //5）execution(* com.xyz.service..*.*(..))——表示匹配service包和它的子包下的方法

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, DataSource ds) {
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