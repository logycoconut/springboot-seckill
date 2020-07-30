package com.logycoco.seckill.listener;

import com.logycoco.seckill.service.SecKillService;
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
public class AmqpListener {

    @Autowired
    private SecKillService secKillService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("seckill.order.queue2"),
            exchange = @Exchange("sexkill.order.exchange2")

    ))
    public void listenSeckill(String msg) {
        System.out.println(msg);
    }

}
