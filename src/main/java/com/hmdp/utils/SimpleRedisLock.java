package com.hmdp.utils;

import cn.hutool.core.lang.UUID;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author：cabbage
 * @Package：com.hmdp.utils
 * @Project：hm-dianping
 * @Date：11/24/2024 10:46 PM
 * @Filename：SimpleRedisLock
 */
public class SimpleRedisLock implements ILock{
    private String name;
    private StringRedisTemplate stringRedisTemplate;
    private static final String KEY_PREFIX = "lock:";
    //这里使用的UUID来自Hutool库，在tostring中可以有参数
    private static final String ID_PREFIX = UUID.randomUUID().toString(true) + "-";

    //这里视频没有提到，应该增加构造函数传参
    //否则会有SimpleRedisLock redisLock = new SimpleRedisLock("order:" + userId, stringRedisTemplate);报错应为 0 个实参，但实际为 2 个
    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean tryLock(long timeoutSec) {
         //获取锁,value中加入线程标志
        String threadID = ID_PREFIX+Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(KEY_PREFIX+name, threadID, timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unlock() {
        //获取线程标识
        String threadID = ID_PREFIX+Thread.currentThread().getId();
        //获取锁中的标识
        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX+name);
        //判断标志是否一致
        if (threadID.equals(id)){
            //释放锁
            stringRedisTemplate.delete(KEY_PREFIX+name);
        }
    }
}
