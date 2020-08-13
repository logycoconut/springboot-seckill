package com.logycoco.seckill.service;

import com.alibaba.fastjson.JSON;
import com.logycoco.seckill.exception.GlobalException;
import com.logycoco.seckill.prefix.KeyPrefix;
import com.logycoco.seckill.response.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author hall
 * @date 2020-08-10 22:21
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 在redis中设值
     *
     * @param prefix 前缀
     * @param key    键名
     * @param value  值
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        int expireSeconds = prefix.getExpireSeconds();
        String realKey = prefix.getPrefix() + ":" + key;
        String valueStr = JSON.toJSONString(value);

        if (StringUtils.isEmpty(valueStr)) {
            return false;
        }

        if (expireSeconds <= 0) {
            operations.set(realKey, valueStr);
        } else {
            operations.set(realKey, valueStr, expireSeconds, TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * 获取redis中的值
     *
     * @param prefix 前缀
     * @param key    键名
     * @param clazz  类class
     * @return 泛型对象
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String realKey = prefix.getPrefix() + ":" + key;

        String value = operations.get(realKey);

        return JSON.parseObject(value, clazz);
    }

    /**
     * 判断redis中是否存在指定键值对
     *
     * @param prefix 前缀
     * @param key    键名
     * @return 是否存在
     */
    public Boolean exist(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + ":" + key;
        return redisTemplate.hasKey(realKey);
    }

    /**
     * 删除键值对
     *
     * @param prefix 前缀
     * @param key    键名
     * @return 是否成功删除
     */
    public Boolean delete(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + ":" + key;
        return redisTemplate.delete(realKey);
    }

    /**
     * 值减一
     *
     * @param prefix 前缀
     * @param key    键名
     * @return 修改后的值
     */
    public Long decr(KeyPrefix prefix, String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String realKey = prefix.getPrefix() + ":" + key;        try {
            return operations.decrement(realKey);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.REDIS_TYPE_ERROR);
        }
    }

    /**
     * 值加一
     *
     * @param prefix 前缀
     * @param key    键名
     * @return 修改后的值
     */
    public Long incr(KeyPrefix prefix, String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String realKey = prefix.getPrefix() + ":" + key;
        try {
            return operations.increment(realKey);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.REDIS_TYPE_ERROR);
        }
    }
}
