package com.logycoco.seckill.service;

import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.prefix.SeckillKey;
import com.logycoco.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hall
 * @date 2020-07-27 07:37
 */
@Service
public class SecKillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    /**
     * 减少库存并且创建订单
     *
     * @param user  当前用户
     * @param goods 商品信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void doSecKill(User user, GoodsVo goods) {
        boolean isSuccess = this.goodsService.reduceStock(goods.getId());
        if (isSuccess) {
            this.orderService.createOrder(user, goods);
        } else {
            //TODO 在redis中标记售空
            this.redisService.set(SeckillKey.soldOver, String.valueOf(goods.getId()), true);
        }
    }

}
