package com.gnq.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnq.base.BaseBuildApp;
import com.gnq.base.BaseResponse;
import com.gnq.base.domain.User;
import com.gnq.base.test.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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

//    public void testHttlp(){
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        // 创建Get请求
//        HttpGet httpGet = new HttpGet("http://localhost:12345/doGetControllerOne");
//
//        // 响应模型
//        CloseableHttpResponse response = null;
//        try {
//            // 由客户端执行(发送)Get请求
//            response = httpClient.execute(httpGet);
//            // 从响应模型中获取响应实体
//            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
//            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 释放资源
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}