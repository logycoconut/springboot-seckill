package com.logycoco.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.logycoco.seckill.enity.Goods;
import com.logycoco.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    /**
     * 获取商品VO对象
     *
     * @param goodsId 商品Id
     * @return 商品VO对象
     */
    @Select("select * from (select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version from sk_goods_seckill sg left join sk_goods g on sg.goods_id = g.id) t where t.id = #{id}")
    GoodsVo selectSingleGoodsVo(long goodsId);

    /**
     * 获取最新版本号
     *
     * @param goodsId 商品Id
     * @return 版本号
     */
    @Select("select t.version from sk_goods_seckill t where t.goods_id = #{goodsId}")
    int getVersionByGoodsId(long goodsId);

    /**
     * 用乐观锁来更新产品库存
     *
     * @param goodsId    商品Id
     * @param oldVersion 版本号
     * @return 更新结果
     */
    @Update("update sk_goods_seckill t set t.stock_count = t.stock_count -1, t.version = t.version + 1 " +
            "where t.goods_id = #{goodsId} and t.stock_count > 0 and t.version = #{oldVersion}")
    int reduceStockByVersion(@Param("goodsId") long goodsId, @Param("oldVersion") int oldVersion);
}
