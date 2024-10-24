package com.hmdp.constants;

public class SystemConstants {
    private SystemConstants() {
    }

    /**
     * 图片存储路径
     */
    public static final String IMAGE_UPLOAD_DIR = "D:\\lesson\\nginx-1.18.0\\html\\hmdp\\imgs\\";
    /**
     * 登录验证码
     */
    public static final String LOGIN_CODE = "code";
    /**
     * 存入Session中用户信息的key
     */
    public static final String LOGIN_USER = "user";
    /**
     * 用户昵称前缀
     */
    public static final String USER_NICK_NAME_PREFIX = "user_";
    /**
     * 默认页面大小
     */
    public static final int DEFAULT_PAGE_SIZE = 5;
    /**
     * 页面最大大小
     */
    public static final int MAX_PAGE_SIZE = 10;
}
