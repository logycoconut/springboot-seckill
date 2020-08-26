package com.logycoco.seckill.web;

import com.logycoco.seckill.enity.OrderInfo;
import com.logycoco.seckill.response.CodeMsg;
import com.logycoco.seckill.response.Result;
import com.logycoco.seckill.service.GoodsService;
import com.logycoco.seckill.service.OrderService;
import com.logycoco.seckill.vo.GoodsVo;
import com.logycoco.seckill.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hall
 * @date 2020/8/26
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("detail/{orderId}")
    public Result<OrderVo> info(@PathVariable("orderId") long orderId) {
        OrderInfo order = this.orderService.getOrderInfoById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = this.goodsService.getGoodsVo(goodsId);

        OrderVo orderVo = OrderVo.builder().goods(goods).order(order).build();
        return Result.success(orderVo);
    }

}