package com.logycoco.seckill.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logycoco.seckill.MainApplication;
import com.logycoco.seckill.dto.QueueMsg;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.mapper.UserMapper;
import com.logycoco.seckill.utils.RsaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author hall
 * @date 2020-06-29 23:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class TestDemo {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSelect() {
        List<User> users = this.userMapper.selectList(null);
        users.forEach(System.out::println);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", "demoData");

        User res = this.userMapper.selectOne(wrapper);

        System.out.println(res);
    }

    @Test
    public void generateKeys() throws IOException, NoSuchAlgorithmException {
        String pubKeyPath = "/Users/hall/Documents/WorkSpace/seckill/src/main/resources/rsa/rsa.pub";
        String priKeyPath = "/Users/hall/Documents/WorkSpace/seckill/src/main/resources/rsa/rsa.pri";

        RsaUtils.generateKey(pubKeyPath, priKeyPath, "123456");
    }

    @Test
    public void testLocalDateTime()  {
        LocalDateTime date = LocalDateTime.now().plusMinutes(1);
        Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(instant);
        System.out.println(from);
    }

    @Test
    public void testString() throws JsonProcessingException {

        QueueMsg msg = new QueueMsg(new User(1234L, "jackma"), "goodsId");
        amqpTemplate.convertAndSend(new ObjectMapper().writeValueAsString(msg));
    rabbitTemplate.convertAndSend("seckill.order.exchange2", "insert", new ObjectMapper().writeValueAsString(msg));
    }

}
