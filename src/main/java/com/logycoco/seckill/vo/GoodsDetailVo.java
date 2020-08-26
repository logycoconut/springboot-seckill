package com.logycoco.seckill.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author hall
 * @date 2020-07-16 21:20
 */
@Data
@Builder
public class GoodsDetailVo {
    private GoodsVo goods;
    private int remainSeconds = 0;
    private int seckillStatus = 0;
}
