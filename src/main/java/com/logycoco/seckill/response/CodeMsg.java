package com.logycoco.seckill.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hall
 * @date 2020/7/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeMsg {
    private int code;
    private String msg;

    public static final CodeMsg BAD_REQUEST = new CodeMsg(400, "错误请求");
    public static final CodeMsg ERROR_URL = new CodeMsg(400001, "错误请求地址");
    public static final CodeMsg FORBIDDEN = new CodeMsg(403, "请求已被拒绝");
    public static final CodeMsg INTERNAL_SERVER_ERROR = new CodeMsg(500, "服务器异常");
    public static final CodeMsg KEY_INIT_ERROR = new CodeMsg(500001, "密钥对初始化异常");
    public static final CodeMsg COOKIE_SET_ERROR = new CodeMsg(500002, "Cookie设置异常");
    public static final CodeMsg COOKIE_FIND_ERROR = new CodeMsg(500003, "Cookie寻找不到");
    public static final CodeMsg REDIS_TYPE_ERROR = new CodeMsg(500004, "对错误的类型进行增减");
    public static final CodeMsg ACCESS_LIMIT = new CodeMsg(500005, "访问高峰期, 请稍后访问");
    public static final CodeMsg SECKILL_OVER = new CodeMsg(500006, "商品已经秒杀完毕");
    public static final CodeMsg SECKILL_REPEAT = new CodeMsg(500007, "商品重复秒杀");

}
