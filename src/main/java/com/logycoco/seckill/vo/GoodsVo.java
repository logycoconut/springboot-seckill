package com.logycoco.seckill.vo;

import com.logycoco.seckill.enity.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author hall
 * @date 2020-07-16 21:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsVo extends Goods {
    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private Integer version;
}
