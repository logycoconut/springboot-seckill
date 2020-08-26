package com.logycoco.seckill.service;

import com.logycoco.seckill.enity.OrderInfo;
import com.logycoco.seckill.enity.User;
import com.logycoco.seckill.prefix.SeckillKey;
import com.logycoco.seckill.utils.CodecUtils;
import com.logycoco.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hall
 * @date 2020-07-27 07:37
 */
@Service
public class SecKillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Value("${seckill.url.salt}")
    private String urlSalt;

    /**
     * 减少库存并且创建订单
     *
     * @param user  当前用户
     * @param goods 商品信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void doSecKill(User user, GoodsVo goods) {
        boolean isSuccess = this.goodsService.reduceStock(goods.getId());
        if (isSuccess) {
            this.orderService.createOrder(user, goods);
        } else {
            // 标记商品售空
            this.redisService.set(SeckillKey.SOLD_OVER, String.valueOf(goods.getId()), true);
        }
    }

    /**
     * 生成随机URL值
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return URL
     */
    public String generateUrl(long userId, long goodsId) {
        return CodecUtils.md5Hex("" + userId + goodsId, urlSalt);
    }


    /**
     * 返回秒杀结果
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return 如果存在订单则返回订单Id,
     *         否则判断商品是否售空,
     *              售空返回-1,
     *              还有库存返回0
     */
    public long getSeckillResult(long userId, long goodsId) {
        OrderInfo orderInfo = this.orderService.getOrderInfoByUserIdAndGoodsId(userId, goodsId);
        if (orderInfo != null) {
            return orderInfo.getId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * 获取商品的售空状态
     *
     * @param goodsId 商品Id
     * @return 如果售空则返回true
     */
    private boolean getGoodsOver(long goodsId) {
        return this.redisService.exist(SeckillKey.SOLD_OVER, "" + goodsId);
    }
}
