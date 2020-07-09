package com.logycoco.seckill.config;

import com.logycoco.seckill.exception.GlobalException;
import com.logycoco.seckill.response.CodeMsg;
import com.logycoco.seckill.utils.RsaUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @author hall
 * @date 2020-07-08 22:25
 */
@ConfigurationProperties(prefix = "seckill.jwt")
@Getter
@Setter
public class JwtConfiguration {
    private String pubKeyPath;
    private String priKeyPath;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Integer expireTime;
    private String cookieName;
    private Integer cookieTime;

    /**
     * 初始化时获取密钥对
     */
    @PostConstruct
    public void init() {
        try {
            this.publicKey = RsaUtils.getPublicKey(this.pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(this.priKeyPath);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.KEY_INIT_ERROR);
        }
    }
}
