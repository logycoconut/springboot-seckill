package com.logycoco.seckill.utils;

import com.logycoco.seckill.enity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author hall
 * @date 2020-07-02 21:31
 */
public class JwtUtils {

    private JwtUtils() {
        throw new IllegalStateException("这是一个工具类");
    }

    /**
     * 根据私钥生成token
     *
     * @param user       用户信息
     * @param privateKey 私钥
     * @param expireTime 到期时间
     * @return token
     */
    public static String generateToken(User user, PrivateKey privateKey, int expireTime) {
        Date expireDate = Date.from(
                LocalDateTime.now().plusMinutes(expireTime).atZone(ZoneId.systemDefault()).toInstant()
        );
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("nickname", user.getNickname())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .setExpiration(expireDate)
                .compact();
    }

    /**
     * 公钥解析token获取用户信息
     *
     * @param publicKey 公钥
     * @param token     token
     * @return User
     */
    public static User parseToken(PublicKey publicKey, String token) {
        Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        return new User((Long) claims.get("id"), (String) claims.get("nickname"));
    }
}
