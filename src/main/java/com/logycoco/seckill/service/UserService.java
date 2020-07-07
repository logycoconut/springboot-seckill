package com.logycoco.seckill.service;

import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.mapper.UserMapper;
import com.logycoco.seckill.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hall
 * @date 2020-07-06 21:13
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    public Boolean register(User user) {
        // 生成salt
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        // 加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        return this.userMapper.insert(user) == 1;
    }

}
