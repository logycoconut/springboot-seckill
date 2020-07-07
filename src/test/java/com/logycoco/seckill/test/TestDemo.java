package com.logycoco.seckill.test;

import com.logycoco.seckill.MainApplication;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.mapper.UserMapper;
import com.logycoco.seckill.utils.RsaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

    @Test
    public void testSelect() {
        List<User> users = this.userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void generateKeys() throws IOException, NoSuchAlgorithmException {
        String pubKeyPath = "/Users/hall/Documents/WorkSpace/seckill/src/main/resources/rsa/rsa.pub";
        String priKeyPath = "/Users/hall/Documents/WorkSpace/seckill/src/main/resources/rsa/rsa.pri";

        RsaUtils.generateKey(pubKeyPath, priKeyPath, "123456");
    }
}
