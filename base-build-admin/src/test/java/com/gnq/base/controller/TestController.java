package com.gnq.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnq.base.BaseBuildApp;
import com.gnq.base.BaseResponse;
import com.gnq.base.domain.User;
import com.gnq.base.test.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest(classes = BaseBuildApp.class)
@RunWith(SpringRunner.class)
public class TestController {
    @Autowired
    TestService testService;

    @Autowired
    TestController testController;

    @Test
    public void test(){
        List<User> userList = testService.getAllUser();
        System.out.println("测试：" + JSONObject.toJSONString(userList));
    }

    /**
     * 测试分页插件
     */
    @Test
    public void testPage(){
        Page<User> page = new Page<>();
        IPage<User> userIPage = testService.selectUserPage(page);
        System.out.println("分页：" + JSONObject.toJSONString(userIPage));
        BaseResponse<IPage<User>> baseResponse = new BaseResponse<IPage<User>>().message("测试").result(userIPage);
        System.out.println("test:"+JSONObject.toJSONString(baseResponse));
    }

    @Test
    public void testGlobalException(){
        int a = 0;
        log.error("ce");
        System.out.println("测试："+(1/a));
    }
}