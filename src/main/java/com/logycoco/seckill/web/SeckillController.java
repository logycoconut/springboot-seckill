package com.logycoco.seckill.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.util.concurrent.RateLimiter;
import com.logycoco.seckill.dto.QueueMsg;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.interceptor.LoginInterceptor;
import com.logycoco.seckill.prefix.GoodsKey;
import com.logycoco.seckill.prefix.OrderKey;
import com.logycoco.seckill.response.CodeMsg;
import com.logycoco.seckill.response.Result;
import com.logycoco.seckill.service.GoodsService;
import com.logycoco.seckill.service.OrderService;
import com.logycoco.seckill.service.RedisService;
import com.logycoco.seckill.vo.GoodsVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hall
 * @date 2020-07-23 07:53
 */
@RestController
@RequestMapping("seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private final RateLimiter limiter = RateLimiter.create(10);

    private final Map<Long, Boolean> localOverMap = new HashMap<>();

    @PostMapping("doSeckill")
    public Result<String> doSeckill(@RequestParam long goodsId) {
        // 获取登录用户
        User user = LoginInterceptor.getLoginUser();

        // 均匀的放行请求
        if (!limiter.tryAcquire(1000, TimeUnit.SECONDS)) {
            return Result.error(CodeMsg.ACCESS_LIMIT);
        }

        // 在内存中判断库存是否不足
        boolean isOver = localOverMap.get(goodsId);
        if (isOver) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        // 预减库存
        long stock = this.redisService.decr(GoodsKey.GOODS_STOCK, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        // 判断重复秒杀
        boolean orderExist = this.orderService.getOrderInfoByUserIdAndGoodsId(user.getId(), goodsId);
        if (orderExist) {
            return Result.error(CodeMsg.SECKILL_REPEAT);
        }

        // 入队
        QueueMsg msg = new QueueMsg(user, goodsId);
        rabbitTemplate.convertAndSend(JSON.toJSONString(msg));

        return Result.success("秒杀成功");
    }

    /**
     * 将秒杀数据都放入redis中
     *
     * @throws Exception 不可预期的异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVos = this.goodsService.listGoodsVo();
        if (goodsVos == null) {
            return;
        }
        goodsVos.forEach(goodsVo -> {
            this.redisService.set(GoodsKey.GOODS_STOCK, "" + goodsVo.getId(), goodsVo.getStockCount());
            //初始化商品都是没有处理过的
            localOverMap.put(goodsVo.getId(), false);
        });
    }
}
