package com.logycoco.seckill.Response;

import lombok.Data;

/**
 * @author hall
 * @date 2020-07-06 23:42
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }
}
