package com.logycoco.seckill.interceptor;

import com.logycoco.seckill.config.JwtConfiguration;
import com.logycoco.seckill.exception.GlobalException;
import com.logycoco.seckill.response.CodeMsg;
import com.logycoco.seckill.utils.CookieUtils;
import com.logycoco.seckill.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hall
 * @date 2020-07-13 22:12
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtConfiguration configuration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String cookieValue = CookieUtils.getCookieValue(request, configuration.getCookieName());

        try {
            JwtUtils.parseToken(configuration.getPublicKey(), cookieValue);
            return true;
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.FORBIDDEN);
        }
    }
}
