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

    public static final CodeMsg BAD_REQUEST = new CodeMsg(400, "bad request");
    public static final CodeMsg FORBIDDEN = new CodeMsg(403, "forbidden");
    public static final CodeMsg INTERNAL_SERVER_ERROR = new CodeMsg(500, "server error");
    public static final CodeMsg KEY_INIT_ERROR = new CodeMsg(500001, "key init error");
    public static final CodeMsg COOKIE_SET_ERROR = new CodeMsg(500002, "cookie set error");
    public static final CodeMsg COOKIE_FIND_ERROR = new CodeMsg(500003, "cookie find error");
    public static final CodeMsg REDIS_TYPE_ERROR = new CodeMsg(500004, "对错误的类型进行增减");

}
