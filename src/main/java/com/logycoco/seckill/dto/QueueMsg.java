package com.logycoco.seckill.dto;

import com.logycoco.seckill.enity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hall
 * @date 2020-07-27 07:47
 */
@Data
@AllArgsConstructor
public class QueueMsg {
    private User user;
    private long goodsId;
}
