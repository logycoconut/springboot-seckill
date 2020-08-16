package com.logycoco.seckill.test;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

/**
 * @author hall
 * @date 2020-06-29 23:17
 */
public class TestDemo1 {

    @Test
    public void testRateLimiter() {
        RateLimiter r = RateLimiter.create(5);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire() + "s");
        }
        /**
         * output: 基本上都是0.2s执行一次，符合一秒发放5个令牌的设定。
         * get 1 tokens: 0.0s
         * get 1 tokens: 0.182014s
         * get 1 tokens: 0.188464s
         * get 1 tokens: 0.198072s
         * get 1 tokens: 0.196048s
         * get 1 tokens: 0.197538s
         * get 1 tokens: 0.196049s
         */
    }
}
