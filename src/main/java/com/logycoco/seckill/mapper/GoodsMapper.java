package com.logycoco.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.logycoco.seckill.enity.Goods;
import com.logycoco.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author hall
 * @date 2020-07-16 21:28
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获取GoodsVo列表
     *
     * @return GoodsVo列表
     */
    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version from sk_goods_seckill sg left join sk_goods g on sg.goods_id = g.id")
    List<GoodsVo> selectGoodsVoList();

    @Select("select * from (select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version from sk_goods_seckill sg left join sk_goods g on sg.goods_id = g.id) t where t.id = #{id}")
    GoodsVo selectSingleGoodsVo(String goodsId);
}
