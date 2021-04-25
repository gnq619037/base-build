package com.gnq.base.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnq.base.domain.User;

import java.util.List;

public interface TestService {

    List<User> getAllUser();

    IPage<User> selectUserPage(Page<User> page);
}
