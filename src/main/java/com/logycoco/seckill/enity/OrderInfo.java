package com.logycoco.seckill.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author hall
 * @date 2020-07-29 21:22
 */
@Data
@Builder
@TableName("sk_order_info")
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
