package com.hmdp.utils;

/**
 * @Author：cabbage
 * @Package：com.hmdp.utils
 * @Project：hm-dianping
 * @Date：11/24/2024 10:42 PM
 * @Filename：ILock
 */
public interface ILock {
    /**
     * 尝试获取锁
     *
     * @param timeoutSec 锁持有的超时时间，过期自动释放
     * @return true表示获取锁成功，false表示获取锁失败
     */
    boolean tryLock(long timeoutSec);

    /**
     * 释放锁
     */
    void unlock();
}