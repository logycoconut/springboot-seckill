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

    /**
     * 查询商品列表
     *
     * @return 商品列表
     */
    public List<GoodsVo> listGoodsVo() {
        return this.goodsMapper.selectGoodsVoList();
    }

    public GoodsVo getGoodsVo(String goodsId) {
        return this.goodsMapper.selectSingleGoodsVo(goodsId);
    }

}
