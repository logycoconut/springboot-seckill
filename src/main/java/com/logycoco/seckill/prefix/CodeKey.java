package com.logycoco.seckill.prefix;

/**
 * @author hall
 * @description 验证码
 * @date 2020-08-16 14:20
 */
public class CodeKey extends BasePrefix {

    public CodeKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    public static final CodeKey VERIFY_CODE = new CodeKey("verifyCode", 120);

}

