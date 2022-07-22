package com.gnq.base.test.impl;/**
 * @Desc:
 * @Author: guonanqing
 * @Date: 2022/2/7 10:09
 */

import com.gnq.base.test.TestService;
import com.gnq.base.test.TestTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc:
 * @Author: guonanqing
 * @Date: 2022/2/7 10:09
 * @Version: 1.0
 */

@Service
public class TestTwoServiceImpl implements TestTwoService {
    @Autowired
    TestService testService;
}
