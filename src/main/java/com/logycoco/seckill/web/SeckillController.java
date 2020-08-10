package com.logycoco.seckill.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logycoco.seckill.dto.QueueMsg;
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

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("doSeckill")
    public Result<Void> doSeckill(@RequestParam long goodsId) throws JsonProcessingException {

        // TODO 请求入队
        QueueMsg msg = new QueueMsg(null, goodsId);
        rabbitTemplate.convertAndSend(MAPPER.writeValueAsString(msg));

        return Result.success(null);
    }

}
