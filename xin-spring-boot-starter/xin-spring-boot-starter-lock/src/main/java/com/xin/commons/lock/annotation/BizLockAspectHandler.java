package com.xin.commons.lock.annotation;

import com.xin.commons.lock.service.RedissonLocker;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 给添加@KLock切面加锁处理
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
@Order(0)
@Slf4j
public class BizLockAspectHandler {

    @Autowired
    private RedissonLocker redissonLocker;

    @Before(value = "@annotation(bizLock)")
    public void bean(JoinPoint joinPoint, BizLock bizLock) throws Throwable {
        //获取锁
        Boolean result = redissonLocker.tryLock(bizLock.key(),bizLock.waitTime(),bizLock.leaseTime());
        //如果获取锁失败
        if(!result){
            log.error("获取锁失败，锁的key={}",bizLock.key());
            throw new Exception("加锁失败，key="+bizLock.key());
        }
    }

    /**
     * 之后的处理逻辑
     */
    @After(value = "@annotation(bizLock)")
    public void after(JoinPoint joinPoint, BizLock bizLock) throws Throwable {
        redissonLocker.unlock(bizLock.key());
    }

    /**
     * 异常之后的处理
     */
    @AfterThrowing(value = "@annotation(bizLock)", throwing = "ex")
    public void afterThrowing (JoinPoint joinPoint, BizLock bizLock, Throwable ex) throws Throwable {
        redissonLocker.unlock(bizLock.key());
        throw ex;
    }

}
