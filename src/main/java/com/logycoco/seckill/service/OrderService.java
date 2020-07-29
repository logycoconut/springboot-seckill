package com.logycoco.seckill.service;

import com.logycoco.seckill.enity.OrderInfo;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.mapper.OrderMapper;
import com.logycoco.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author hall
 * @date 2020-07-27 07:53
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 创建订单
     * @param user 当前用户
     * @param goods 商品信息
     *              return
     */
    public OrderInfo createOrder(User user, GoodsVo goods) {
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

        return orderInfo;
    }

}
