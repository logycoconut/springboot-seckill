package com.logycoco.seckill.web;

import com.logycoco.seckill.service.GoodsService;
import com.logycoco.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author hall
 * @date 2020-07-13 21:31®
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("goodsList", this.goodsService.listGoodsVo());
        return "goods_list";
    }

    @GetMapping("detail/{id}")
    public String list(@PathVariable("id") String goodsId, Model model) {
        GoodsVo goodsVo = this.goodsService.getGoodsVo(goodsId);
        model.addAttribute("goods", goodsVo);
        //remainSeconds seckillStatus
        int remainSeconds = 0;
        int seckillStatus = 0;
        long startTime = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        if (now < startTime) {
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            seckillStatus = 2;
        } else {
            seckillStatus = 1;
        }

        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("seckillStatus", seckillStatus);

        return "goods_detail";
    }

}
