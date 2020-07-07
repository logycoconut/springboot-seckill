package com.logycoco.seckill.web;

import com.logycoco.seckill.Response.Result;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

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
            return Result.error();
        }
        return ResponseEntity.created(URI.create("/user/register")).build();
    }

}
