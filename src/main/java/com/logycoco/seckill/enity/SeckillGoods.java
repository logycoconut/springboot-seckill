package com.logycoco.seckill.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author hall
 * @date 2020-07-26 22:43
 */
@Data
@TableName("sk_goods_seckill")
public class SeckillGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private int version;
}
