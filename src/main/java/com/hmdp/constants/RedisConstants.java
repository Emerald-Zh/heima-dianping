package com.hmdp.constants;

public class RedisConstants {

    // 登录相关

    /**
     * 登录验证码前缀
     */
    public static final String LOGIN_CODE_KEY = "login:code:";
    /**
     * 登录验证码有效期 200min
     */
    public static final Long LOGIN_CODE_TTL = 200L;
    /**
     * 登录用户前缀
     */
    public static final String LOGIN_USER_KEY = "login:user:";
    /**
     * 登录用户有效期 3600min
     */
    public static final Long LOGIN_USER_TTL = 3600L;
    /**
     * 空对象的有效器
     */
    public static final Long CACHE_NULL_TTL = 2L;


    // 店铺相关
    /**
     * 店铺缓存数据有效期
     */
    public static final Long CACHE_SHOP_TTL = 30L;
    /**
     * 缓存店铺数据前缀
     */
    public static final String CACHE_SHOP_KEY = "cache:shop:";
    /**
     * 缓存店铺类型前缀
     */
    public static final String CACHE_SHOP_TYPE_KEY = "cache:type:";
    /**
     * 店铺类型缓存数据有效期
     */
    public static final Long CACHE_SHOP_TYPE_TTL = 30L;


    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final Long LOCK_SHOP_TTL = 10L;
    public static final String SECKILL_STOCK_KEY = "seckill:stock:";
    public static final String BLOG_LIKED_KEY = "blog:liked:";
    public static final String FEED_KEY = "feed:";
    public static final String SHOP_GEO_KEY = "shop:geo:";
    public static final String USER_SIGN_KEY = "sign:";
}
