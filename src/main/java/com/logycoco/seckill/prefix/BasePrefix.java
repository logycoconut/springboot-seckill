package com.logycoco.seckill.prefix;

/**
 * @author hall
 * @date 2020-08-10 23:10
 */
public abstract class BasePrefix implements KeyPrefix {

    private String prefix;

    private int expireSeconds;

    public BasePrefix(String prefix) {
        this(prefix, 0);
    }

    public BasePrefix(String prefix, int expireSeconds) {
        this.prefix = prefix;
        this.expireSeconds = expireSeconds;
    }

    @Override
    public String getPrefix() {
        return this.getClass().getSimpleName() + ":" + prefix;
    }

    @Override
    public int getExpireSeconds() {
        return this.expireSeconds;
    }

}
