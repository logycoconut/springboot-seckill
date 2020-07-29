package com.logycoco.seckill.web;

import com.logycoco.seckill.response.Result;
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

    @PostMapping("doSeckill")
    public Result<Void> doSeckill(@RequestParam String goodsId) {

        // TODO 请求入队

        return Result.success(null);
    }

}
