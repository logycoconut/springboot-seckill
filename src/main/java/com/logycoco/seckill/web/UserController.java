package com.logycoco.seckill.web;

import com.logycoco.seckill.response.CodeMsg;
import com.logycoco.seckill.response.Result;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author hall
 * @date 2020-07-06 20:58
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public Result<Void> register(@Valid User user) {
        Boolean boo = this.userService.register(user);
        if (Boolean.FALSE.equals(boo)) {
            return Result.error(CodeMsg.BAD_REQUEST);
        }
        return Result.success(null);
    }

    @PostMapping("login")
    public Result<Void> login(@Valid User user) {
        Boolean boo = this.userService.login(user);
        if (Boolean.FALSE.equals(boo)) {
            return Result.error(CodeMsg.BAD_REQUEST);
        }
        return Result.success(null);
    }

}
