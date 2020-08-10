package com.logycoco.seckill.prefix;

/**
 * @author hall
 * @date 2020-08-10 23:06
 */
public interface KeyPrefix {

    /**
     * 获取前缀
     * @return 前缀
     */
    String getPrefix();

    /**
     * 获取过期时间
     * @return 过期时间
     */
    int getExpireSeconds();

}
