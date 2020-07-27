package com.logycoco.seckill.service;

import com.logycoco.seckill.enity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hall
 * @date 2020-07-27 07:37
 */
public class SecKillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    /**
     * 减少库存并且创建订单
     * @param user
     * @param goodsId
     */
    @Transactional(rollbackFor = Exception.class)
    public void doSecKill(User user, String goodsId) {
        Boolean isSuccess = this.goodsService.reduceStock(goodsId);
        if (isSuccess) {
            this
        }
    }

}
