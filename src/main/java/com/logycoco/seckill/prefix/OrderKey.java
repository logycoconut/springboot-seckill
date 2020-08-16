package com.logycoco.seckill.prefix;

/**
 * @author hall
 * @description
 * @date 2020-08-16 15:30
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }

    public static final OrderKey SECKILL_ORDER = new OrderKey("seckill");

}

