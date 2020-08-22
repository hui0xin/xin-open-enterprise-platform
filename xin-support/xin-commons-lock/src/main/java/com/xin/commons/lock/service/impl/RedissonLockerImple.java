package com.xin.commons.lock.service.impl;

import com.xin.commons.lock.errorcode.LockErrorCode;
import com.xin.commons.lock.exception.LockException;
import com.xin.commons.lock.service.RedissonLocker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 互斥性：分布式锁需要保证在不同节点的不同线程的互斥。这是最根本的。
 * 可重入性：同一个节点上的同一个线程如果获取了锁之后也可以再次获取这个锁。
 * 锁超时：和本地锁一样支持锁超时，防止死锁。
 * 高可用：加锁和解锁需要高效，同时也需要保证高可用防止分布式锁失效，可以增加降级。
 * 支持阻塞和非阻塞：和 ReentrantLock 一样支持 lock 和 trylock 以及 tryLock(long timeOut)。
 * 支持公平锁和非公平锁(可选)：公平锁的意思是按照请求加锁的顺序获得锁，非公平锁就相反是无序的。这个一般来说实现的比较少。
 * 可重入锁（Reentrant Lock）
 * 公平锁（Fair Lock）
 * 联锁（MultiLock）
 * 红锁（RedLock）
 * 读写锁（ReadWriteLock）
 * 信号量（Semaphore）
 * 可过期性信号量（PermitExpirableSemaphore）
 * 闭锁（CountDownLatch）
 *
 * @author: xin
 */
@Slf4j
@Component
public class RedissonLockerImple implements RedissonLocker {

    @Autowired
    private RedissonClient redissonClient;

    //默认超时时间 5秒
    private static final int expireTime = 5;

    //默认时间类型 秒
    private static final TimeUnit timeType = TimeUnit.SECONDS;

    //---------------------------------------重入锁（Reentrant Lock）---------------------------------------------------------------

    /**
     * 加锁 lock() 方法尝试获取锁，如果成功获得锁，则继续往下执行，
     * 否则等待锁被释放，然后再继续尝试获取锁，直到成功获得锁。
     * 拿不到lock就不罢休，不然线程就一直block
     * @param lockKey 锁的名称
     * @return
     */
    @Override
    public RLock lock(String lockKey) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            lock.lock(expireTime, timeType);
            return lock;
        } catch (Exception e) {
            log.error("lock失败：{}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.LOCK_FAIL);
        }
    }

    /**
     * 带超时的锁
     *
     * @param lockKey 锁的名称
     * @param timeout 超时时间   单位：秒
     */
    @Override
    public RLock lock(String lockKey, int timeout) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            lock.lock(timeout, timeType);
            return lock;
        } catch (Exception e) {
            log.error("lock失败：{}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.LOCK_FAIL);
        }
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param unit    时间单位
     * @param timeout 超时时间
     */
    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            lock.lock(timeout, unit);
            return lock;
        } catch (Exception e) {
            log.error("lock失败：{}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.LOCK_FAIL);
        }
    }

    /**
     * 尝试获取锁 马上返回，拿到lock就返回true，不然返回false。
     * 该方法的使用需要注意（小心）
     * 在业务逻辑做操作 if(tryLock(lockKey, waitTime, leaseTime)){执行具体逻辑}else{不再执行}
     * @param lockKey
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, timeType);
        } catch (Exception e) {
            log.error("tryLock失败：{}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.LOCK_FAIL);
        }
    }

    /**
     * 尝试获取锁 该方法的使用需要注意（小心）
     * 在业务逻辑做操作 if(tryLock(lockKey, waitTime, leaseTime)){执行具体逻辑}else{不再执行}
     *
     * @param lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {
            log.error("tryLock失败: {}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.LOCK_FAIL);
        }
    }

    /**
     * 释放锁
     * @param lockKey 锁的名称
     */
    @Override
    public void unlock(String lockKey) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            lock.unlock();
        } catch (Exception e) {
            log.error("unLock失败: {}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.UNLOCK_FAIL);
        }
    }

    /**
     * 释放锁
     * @param lock
     */
    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }


    //--------------------------------以下不建议使用-除非理解透彻--------------------------------------------------

    /**
     * 分布式锁的异步执行 解锁 unlockAsync(key)
     * 在业务逻辑做操作 if(asyncReentrantLock(lockKey, waitTime, leaseTime)){执行具体逻辑}else{不再执行}
     *
     * @param lockKey
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     */
    public Boolean asyncReentrantLock(String lockKey, int waitTime, int leaseTime) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            Future<Boolean> result = lock.tryLockAsync(waitTime, leaseTime, timeType);
            Boolean res = result.get();
            return res;
        } catch (Exception e) {
            log.error("asyncReentrantLock失败: {}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.LOCK_FAIL);
        }
    }

    /**
     * 公平锁
     * 当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     *
     * @param lockKey
     * @param leaseTime
     */
    public boolean fairLock(String lockKey, int waitTime, int leaseTime) {
        try {
            RLock fairLock = redissonClient.getFairLock(lockKey);
            //该方法没有返回值，一定会加锁
            //fairLock.lock(leaseTime,timeType);
            //该方法需要 判断锁的持有情况
            return fairLock.tryLock(waitTime, leaseTime, timeType);
        } catch (Exception e) {
            log.error("fairLock失败: {}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.LOCK_FAIL);
        }
    }

    /**
     * 公平锁 的异步执行
     * 保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     *
     * @param lockKey
     * @param leaseTime
     */
    public boolean fairAsyncLock(String lockKey, int waitTime, int leaseTime) {
        try {
            RLock fairLock = redissonClient.getFairLock(lockKey);
            Future<Boolean> result = fairLock.tryLockAsync(waitTime, leaseTime, timeType);
            Boolean res = result.get();
            return res;
        } catch (Exception e) {
            log.error("fairAsyncLock失败: {}", ExceptionUtils.getStackTrace(e));
            throw new LockException(LockErrorCode.LOCK_FAIL);
        }
    }

}
