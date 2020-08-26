package com.logycoco.seckill.web;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.logycoco.seckill.dto.QueueMsg;
import com.logycoco.seckill.enity.OrderInfo;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.interceptor.LoginInterceptor;
import com.logycoco.seckill.prefix.CodeKey;
import com.logycoco.seckill.prefix.GoodsKey;
import com.logycoco.seckill.response.CodeMsg;
import com.logycoco.seckill.response.Result;
import com.logycoco.seckill.service.GoodsService;
import com.logycoco.seckill.service.OrderService;
import com.logycoco.seckill.service.RedisService;
import com.logycoco.seckill.service.SecKillService;
import com.logycoco.seckill.utils.ImageUtils;
import com.logycoco.seckill.vo.GoodsVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hall
 * @date 2020-07-23 07:53
 */
@RestController
@RequestMapping("seckill")
public class SeckillController implements InitializingBean {

    private final RateLimiter limiter = RateLimiter.create(10);
    private final Map<Long, Boolean> localOverMap = new HashMap<>();
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SecKillService secKillService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 秒杀
     *
     * @param goodsId  商品Id
     * @param md5Value 随机md5值
     * @return 秒杀结果
     */
    @PostMapping("{md5}/doSeckill")
    public Result<String> doSeckill(@PathVariable("md5") String md5Value, @RequestParam long goodsId) {
        // 获取登录用户
        User user = LoginInterceptor.getLoginUser();
/**
        // 判断接口是否正确
        if (!md5Value.equals(secKillService.generateUrl(user.getId(), goodsId))) {
            return Result.error(CodeMsg.ERROR_URL);
        }

        // 均匀的放行请求
        if (!limiter.tryAcquire(1000, TimeUnit.SECONDS)) {
            return Result.error(CodeMsg.ACCESS_LIMIT);
        }

        // 在内存中判断库存是否不足
        boolean isOver = localOverMap.get(goodsId);
        if (isOver) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        // 预减库存, 重新缓存并二次预减的原因是卖光商品
        long stock = this.redisService.decr(GoodsKey.GOODS_STOCK, "" + goodsId);
        if (stock < 0) {
            afterPropertiesSet();
            long resetStock = this.redisService.decr(GoodsKey.GOODS_STOCK, "" + goodsId);
            if (resetStock < 0) {
                localOverMap.put(goodsId, true);
                return Result.error(CodeMsg.SECKILL_OVER);
            }
        }

        // 判断重复秒杀
        boolean orderExist = this.orderService.getOrderInfoByUserIdAndGoodsIdInRedis(user.getId(), goodsId);
        if (orderExist) {
            return Result.error(CodeMsg.SECKILL_REPEAT);
        }

        // 入队
        QueueMsg msg = new QueueMsg(user, goodsId);
        rabbitTemplate.convertAndSend(JSON.toJSONString(msg)); **/

        return Result.success("秒杀成功");
    }

    /**
     * 获取验证码
     */
    @GetMapping(value = "verifyCode", produces = "image/jpg")
    public Result<Void> getVerifyCode(@RequestParam long goodsId, HttpServletResponse response) {
        // 获取登录用户
        User user = LoginInterceptor.getLoginUser();
        String verifyCode = ImageUtils.generateVerifyCode(4);

        // 将验证码存入redis中
        this.redisService.set(CodeKey.VERIFY_CODE, "" + user.getId() + goodsId, verifyCode);

        BufferedImage bufferedImage = ImageUtils.createVerifyCodeImage(verifyCode);
        try (OutputStream out = response.getOutputStream()) {
            ImageIO.write(bufferedImage, "JPEG", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(null);
    }

    /**
     * 获取秒杀链接
     *
     * @param goodsId 商品Id
     * @return 秒杀链接
     */
    @GetMapping("url")
    public Result<String> getSeckillUrl(@RequestParam long goodsId, @RequestParam(defaultValue = "0") String verifyCode) {
        // 获取登录用户
        User user = LoginInterceptor.getLoginUser();

//        //验证码校验
//        boolean check = verifyCode.equalsIgnoreCase(redisService.get(CodeKey.VERIFY_CODE, "" + user.getId() + goodsId, String.class));
//        if (!check) {
//            return Result.error(CodeMsg.ERROR_CODE);
//        }

        String url = secKillService.generateUrl(user.getId(), goodsId);
        return Result.success(url);
    }
    /**
     * 将秒杀数据都放入redis中
     */
    @Override
    public void afterPropertiesSet() {
        List<GoodsVo> goodsVos = this.goodsService.listGoodsVo();
        if (goodsVos == null) {
            return;
        }
        goodsVos.forEach(goodsVo -> {
            this.redisService.set(GoodsKey.GOODS_STOCK, "" + goodsVo.getId(), goodsVo.getStockCount());
            // 初始化商品都是没有处理过的
            localOverMap.put(goodsVo.getId(), false);
        });
    }

    /**
     * 获取订单Id
     */
    @GetMapping("result")
    public Result<String> result(@RequestParam long goodsId) {
        // 获取登录用户
        User user = LoginInterceptor.getLoginUser();

        // long型传到前端有精度丢失
        String result = "" + this.secKillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(result);
    }

}
