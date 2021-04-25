package com.gnq.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gnq.base.mapper")
public class BaseBuildApp {
    public static void main(String[] args) {
        SpringApplication.run(BaseBuildApp.class, args);
    }
}
