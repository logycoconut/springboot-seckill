package com.logycoco.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * @author hall
 * @date 2020-07-06 21:31
 */
public class CodecUtils {

    private CodecUtils() {
        throw new IllegalStateException("这是一个工具类");
    }

    /**
     * 生成盐
     *
     * @return salt
     */
    public static String generateSalt() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 对密码进行加密
     *
     * @param data 密码
     * @param salt 盐
     * @return 加密后的密码
     */
    public static String md5Hex(String data, String salt) {
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

}
