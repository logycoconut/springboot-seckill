package com.logycoco.seckill.web;

import com.logycoco.seckill.prefix.GoodsKey;
import com.logycoco.seckill.response.Result;
import com.logycoco.seckill.service.GoodsService;
import com.logycoco.seckill.service.RedisService;
import com.logycoco.seckill.vo.GoodsDetailVo;
import com.logycoco.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hall
 * @date 2020-07-13 21:31®
 */
@RestController
@RequestMapping("goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 返回列表页
     */
    @GetMapping(value = "list", produces = "text/html")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 从缓存中取出列表页
        String listHtml = this.redisService.get(GoodsKey.GOODS_LIST, "", String.class);
        if (!StringUtils.isEmpty(listHtml)) {
            return listHtml;
        }

        // 手动渲染页面
        model.addAttribute("goodsList", this.goodsService.listGoodsVo());
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        listHtml = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(listHtml)) {
            this.redisService.set(GoodsKey.GOODS_LIST, "", listHtml);
        }

        return listHtml;
    }

    /**
     * 返回详情页
     */
    @GetMapping("detail/{id}")
    public Result<GoodsDetailVo> detail(@PathVariable("id") String goodsId) {

        GoodsVo goodsVo = this.goodsService.getGoodsVo(Long.parseLong(goodsId));

        // 计算剩余时间和状态
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

        GoodsDetailVo goodsDetailVo = GoodsDetailVo.builder()
                .goods(goodsVo)
                .remainSeconds(remainSeconds)
                .seckillStatus(seckillStatus).build();

        return Result.success(goodsDetailVo);
    }

}
