package com.logycoco.seckill.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hall
 * @date 2020-07-08 21:57
 */
public class CookieUtils {
    private CookieUtils() {
        throw new IllegalStateException("这是一个工具类");
    }

    /**
     * 设置cookie
     * @param request 请求
     * @param response 响应
     * @param cookieName cookie名
     * @param cookieValue  cookie值
     * @param cookieTime cookie到期时间
     * @param httpOnly 设置Http-Only
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, String cookieTime, boolean httpOnly) {

    }
}
