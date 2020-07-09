package com.logycoco.seckill;

import com.logycoco.seckill.config.JwtConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author hall
 * @date 2020-06-23 00:18
 */
@SpringBootApplication
@EnableConfigurationProperties({JwtConfiguration.class})
@MapperScan("com.logycoco.seckill.mapper")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
