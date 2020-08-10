package com.logycoco.seckill.prefix;

/**
 * @author hall
 * @date 2020-08-10 23:21
 */
public class SeckillKey extends BasePrefix {

    private SeckillKey(String prefix) {
        super(prefix);
    }

    public static final SeckillKey soldOver = new SeckillKey("soldOver");
}
