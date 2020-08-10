package com.logycoco.seckill.service;

import com.logycoco.seckill.prefix.KeyPrefix;
import com.logycoco.seckill.prefix.SeckillKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hall
 * @date 2020-08-10 22:21
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public <T> void set(KeyPrefix prefix, String key, T value) {
        // TODO redis赋值
    }
}
