package com.logycoco.seckill.exception;

import com.logycoco.seckill.response.CodeMsg;
import lombok.Getter;

/**
 * @author hall
 * @date 2020/7/7
 */
@Getter
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super();
        this.codeMsg = codeMsg;
    }

}
