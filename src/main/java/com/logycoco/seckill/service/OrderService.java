package com.logycoco.seckill.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
     * 判断重复下单, 在redis中查询有无记录
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return 如果存在返回true
     */
    public Boolean getOrderInfoByUserIdAndGoodsIdInRedis(long userId, long goodsId) {
        return null != this.redisService.get(OrderKey.SECKILL_ORDER, "" + userId + goodsId, String.class);
    }

    /**
     * 根据用户Id和商品Id查询订单
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return 订单信息
     */
    public OrderInfo getOrderInfoByUserIdAndGoodsId(long userId, long goodsId) {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("goods_id", goodsId);
        return this.orderMapper.selectOne(wrapper);
    }

    /**
     * 根据订单Id查询订单
     *
     * @param orderId 订单Id
     * @return 订单信息
     */
    public OrderInfo getOrderInfoById(long orderId) {
        return this.orderMapper.selectById(orderId);
    }
}
