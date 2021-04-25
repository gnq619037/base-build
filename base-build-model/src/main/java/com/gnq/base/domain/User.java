package com.gnq.base.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_user")
public class User {
    private Long id;
    private String nickName;
}
