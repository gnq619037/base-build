package com.gnq.base.controller;

import com.gnq.base.BaseException;
import com.gnq.base.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/fail")
    public void test(){
        System.out.println(1/0);
        throw new BaseException(1, "");
    }
}
