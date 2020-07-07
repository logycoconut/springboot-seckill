package com.logycoco.seckill.exception;

import com.logycoco.seckill.response.CodeMsg;
import com.logycoco.seckill.response.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hall
 * @date 2020/7/7
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result<Void> exceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            return Result.error(((GlobalException) e).getCodeMsg());
        } else {
            return Result.error(CodeMsg.INTERNAL_SERVER_ERROR);
        }
    }
}
