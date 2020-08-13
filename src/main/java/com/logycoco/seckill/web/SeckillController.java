package com.logycoco.seckill.web;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.logycoco.seckill.dto.QueueMsg;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.interceptor.LoginInterceptor;
import com.logycoco.seckill.response.Result;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hall
 * @date 2020-07-23 07:53
 */
@Controller
@RequestMapping("seckill")
public class SeckillController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("doSeckill")
    public Result<Void> doSeckill(@RequestParam long goodsId) {
        User user = LoginInterceptor.getLoginUser();
        QueueMsg msg = new QueueMsg(user, goodsId);
        rabbitTemplate.convertAndSend(JSON.toJSONString(msg));

        return Result.success(null);
    }

}
