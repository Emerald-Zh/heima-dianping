package com.hmdp.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.model.dto.UserDTO;
import com.hmdp.utils.ThreadLocalUtls;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.constants.RedisConstants.LOGIN_USER_KEY;
import static com.hmdp.constants.RedisConstants.LOGIN_USER_TTL;

/**
 * @author ghp
 * @title 刷新 Redis 中 token 的拦截器
 * @description
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、获取token，并判断token是否存在
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)){
            // token不存在，说明当前用户未登录，不需要刷新直接放行
            return true;
        }
        // 2、判断用户是否存在
        String tokenKey = LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        if (userMap.isEmpty()){
            // 用户不存在，说明当前用户未登录，不需要刷新直接放行
            return true;
        }
        // 3、用户存在，则将用户信息保存到ThreadLocal中，方便后续逻辑处理，比如：方便获取和使用用户信息，Redis获取用户信息是具有侵入性的
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        ThreadLocalUtls.saveUser(BeanUtil.copyProperties(userMap, UserDTO.class));
        // 4、刷新token有效期
        stringRedisTemplate.expire(token, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return true;
    }
}
