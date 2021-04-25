package com.gnq.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnq.base.domain.User;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义分页
     * @param page
     * @return
     */
    IPage<User> selectPageVo(Page<?> page);
}
