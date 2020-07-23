package com.logycoco.seckill.web;

import com.logycoco.seckill.response.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hall
 * @date 2020-07-23 07:53
 */
@Controller
@RequestMapping("seckill")
public class SeckillController {

    @PostMapping("doSeckill")
    public Result<Void> doSeckill() {


        return Result.success(null);
    }

}
