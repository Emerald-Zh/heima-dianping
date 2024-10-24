package com.hmdp.interceptor;

import cn.hutool.http.HttpStatus;
import com.hmdp.utils.ThreadLocalUtls;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ghp
 * @title 登录拦截器
 * @description
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 前置拦截器，用于判断用户是否登录
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前用户是否已登录
        if (ThreadLocalUtls.getUser() == null){
            // 当前用户未登录，直接拦截
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            return false;
        }
        // 用户存在，直接放行
        return true;
    }
}
