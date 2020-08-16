package com.logycoco.seckill.prefix;

/**
 * @description 商品前缀
 * @author hall
 * @date 2020-08-16 14:20
 */
public class GoodsKey extends BasePrefix {

    public GoodsKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    public static final GoodsKey GOODS_LIST = new GoodsKey("goodList", 60);
    public static final GoodsKey GOODS_DETAIL = new GoodsKey("goodDetail", 60);
    public static final GoodsKey GOODS_STOCK = new GoodsKey("goodStock", 0);

}
