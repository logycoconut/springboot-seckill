package com.logycoco.seckill.service;

import com.logycoco.seckill.enity.OrderInfo;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.mapper.OrderMapper;
import com.logycoco.seckill.prefix.OrderKey;
import com.logycoco.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hall
 * @date 2020-07-27 07:53
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 创建订单
     *
     * @param user  当前用户
     * @param goods 商品信息
     */
    public void createOrder(User user, GoodsVo goods) {
        OrderInfo orderInfo = OrderInfo.builder()
                .userId(user.getId())
                .goodsId(goods.getId())
                .deliveryAddrId(0L)
                .goodsName(goods.getGoodsName())
                .goodsCount(1)
                .goodsPrice(goods.getGoodsPrice())
                .orderChannel(0)
                .status(0)
                .createDate(new Date()).build();

        this.orderMapper.insert(orderInfo);

        this.redisService.set(OrderKey.SECKILL_ORDER,
                "" + orderInfo.getUserId() + "_" + orderInfo.getGoodsId(), "orderExist");
    }

    /**
     * 判断重复下单
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return 如果存在返回true
     */
    public Boolean getOrderInfoByUserIdAndGoodsId(long userId, long goodsId) {
        return null != this.redisService.get(OrderKey.SECKILL_ORDER, "" + userId + goodsId, String.class);
    }

}
