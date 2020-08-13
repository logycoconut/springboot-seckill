package com.logycoco.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.logycoco.seckill.dto.QueueMsg;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.service.GoodsService;
import com.logycoco.seckill.service.SecKillService;
import com.logycoco.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hall
 * @date 2020-07-30 22:14
 */
@Service
@Slf4j
public class AmqpListener {

    @Autowired
    private SecKillService secKillService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 监听队列中的秒杀请求
     *
     * @param msg 用户信息以及商品Id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("seckill.order.queue"),
            exchange = @Exchange("seckill.order.exchange")
    ))
    public void listenSeckill(String msg) {
        QueueMsg queueMsg = JSON.parseObject(msg, QueueMsg.class);
        User user = queueMsg.getUser();
        GoodsVo goodsVo = goodsService.getGoodsVo(queueMsg.getGoodsId());

        if (goodsVo == null || user == null) {
            return;
        }

        secKillService.doSecKill(user, goodsVo);
    }

}
