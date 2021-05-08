package com.gnq.base.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_shiro_url")
public class ShiroUrl {
    private Integer id;
    private String url;
    private String filterType;


}
