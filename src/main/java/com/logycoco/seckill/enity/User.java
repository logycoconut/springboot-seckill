package com.logycoco.seckill.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author hall
 * @date 2020-06-29 23:22
 */
@Data
@TableName("sk_user")
public class User {
    private Long id;
    @NotNull
    private String nickname;
    @NotNull
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
