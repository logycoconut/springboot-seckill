package com.logycoco.seckill.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hall
 * @date 2020-07-06 23:42
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 返回成功结果
     *
     * @param data 数据
     * @return 结果对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    /**
     * 返回错误代码
     *
     * @param codeMsg 错误信息
     * @return 错误信息
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<>(codeMsg);
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMsg();
    }
}
