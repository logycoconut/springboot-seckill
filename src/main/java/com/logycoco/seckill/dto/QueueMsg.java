package com.logycoco.seckill.dto;

import com.logycoco.seckill.enity.User;
import lombok.Data;

/**
 * @author hall
 * @date 2020-07-27 07:47
 */
@Data
public class QueueMsg {
    private User user;
    private String goodsId;
}
