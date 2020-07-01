package com.logycoco.seckill.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author hall
 * @date 2020-06-29 23:22
 */
@Data
@TableName("sk_goods")
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
