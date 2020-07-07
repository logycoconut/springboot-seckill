package com.logycoco.seckill.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.exception.GlobalException;
import com.logycoco.seckill.mapper.UserMapper;
import com.logycoco.seckill.response.CodeMsg;
import com.logycoco.seckill.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author hall
 * @date 2020-07-06 21:13
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册 ( 未进行唯一验证 )
     *
     * @param user 用户
     * @return 成功注册则返回true
     */
    public Boolean register(User user) {
        // 生成salt
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        // 加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        return this.userMapper.insert(user) == 1;
    }


    public Boolean login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", user.getNickname());

        User res = this.userMapper.selectOne(wrapper);
        Assert.notNull(res, "没有此用户");

        String loginPassword = CodecUtils.md5Hex(user.getPassword(), res.getSalt());
        String resPassword = res.getPassword();
        if (!resPassword.equals(loginPassword)) {
            throw new GlobalException(new CodeMsg(401, "登录失败"));
        }
        return true;
    }
}
