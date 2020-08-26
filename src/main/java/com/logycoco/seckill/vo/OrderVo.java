package com.logycoco.seckill.vo;

import com.logycoco.seckill.enity.OrderInfo;
import lombok.Builder;
import lombok.Data;

/**
 * @author hall
 * @date 2020/8/26
 */
@Data
@Builder
public class OrderVo {
    private GoodsVo goods;
    private OrderInfo order;
}
