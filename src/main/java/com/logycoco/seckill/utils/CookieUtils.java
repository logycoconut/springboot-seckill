package com.logycoco.seckill.utils;

import com.logycoco.seckill.exception.GlobalException;
import com.logycoco.seckill.response.CodeMsg;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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

    private static String getDomain(HttpServletRequest request) {
        String serverName = request.getRequestURL().toString();
        serverName = serverName.substring(7, serverName.indexOf('/', 7));

        if (serverName.indexOf(':') != -1) {
            serverName = serverName.substring(0, serverName.indexOf(':', 0));
        }

        return serverName;
    }
}
