package com.logycoco.seckill.service;

import com.logycoco.seckill.mapper.GoodsMapper;
import com.logycoco.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hall
 * @date 2020-07-16 21:27
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public static final int MAX_ATTEMPT_AMOUNT = 5;

    /**
     * 查询商品列表
     *
     * @return 商品列表
     */
    public List<GoodsVo> listGoodsVo() {
        return this.goodsMapper.selectGoodsVoList();
    }

    /**
     * 根据商品Id获取商品VO
     *
     * @param goodsId 商品Id
     * @return 商品VO
     */
    public GoodsVo getGoodsVo(long goodsId) {
        return this.goodsMapper.selectSingleGoodsVo(goodsId);
    }

    /**
     * 将商品库存减一
     *
     * @param goodsId 商品Id
     * @return 操作结果
     */
    public Boolean reduceStock(long goodsId) {
        int attemptAmount = 0;
        int res = 0;

        do {
            attemptAmount++;
            int version = this.goodsMapper.getVersionByGoodsId(goodsId);
            res = this.goodsMapper.reduceStockByVersion(goodsId, version);

            if (res != 0) {
                break;
            }
        } while (attemptAmount < MAX_ATTEMPT_AMOUNT);

        return res > 0;
    }
}
