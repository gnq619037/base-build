package com.gnq.base.test.impl;

import com.alibaba.fastjson.JSONObject;
import com.gnq.base.domain.User;
import com.gnq.base.mapper.UserMapper;
import com.gnq.base.test.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        List<User> users = userMapper.selectList(null);
        System.out.println("users:"+ JSONObject.toJSONString(users));
        return users;
    }
}
