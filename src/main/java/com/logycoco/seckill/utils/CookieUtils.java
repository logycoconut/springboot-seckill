package com.logycoco.seckill.utils;

import com.logycoco.seckill.exception.GlobalException;
import com.logycoco.seckill.response.CodeMsg;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

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
     *
     * @param request     请求
     * @param response    响应
     * @param cookieName  cookie名
     * @param cookieValue cookie值
     * @param cookieTime  cookie到期时间
     * @param httpOnly    设置Http-Only
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response,
                                 String cookieName, String cookieValue, Integer cookieTime, boolean httpOnly) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else {
                cookieValue = URLDecoder.decode(cookieValue, "utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setMaxAge(cookieTime);
            cookie.setDomain(getDomain(request));
            cookie.setPath("/");
            cookie.setHttpOnly(httpOnly);

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new GlobalException(CodeMsg.COOKIE_SET_ERROR);
        }
    }

    /**
     * 获取域名
     *
     * @param request 请求
     * @return 域名
     */
    private static String getDomain(HttpServletRequest request) {
        String serverName = request.getRequestURL().toString();
        serverName = serverName.substring(7, serverName.indexOf('/', 7));

        if (serverName.indexOf(':') != -1) {
            serverName = serverName.substring(0, serverName.indexOf(':', 0));
        }

        return serverName;
    }

    /**
     * 根据名字获取cookie值
     *
     * @param request    请求
     * @param cookieName cookie名
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        Cookie filterCookie = Arrays.stream(cookies).filter(cookie -> cookieName.equals(cookie.getValue()))
                .findAny().orElse(null);
        if (filterCookie == null) {
            throw new GlobalException(CodeMsg.COOKIE_FIND_ERROR);
        }
        return filterCookie.getValue();
    }
}
