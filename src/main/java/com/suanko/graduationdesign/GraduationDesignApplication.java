package com.suanko.graduationdesign;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.suanko.graduationdesign.dao")
@EnableScheduling//定时启动
public class GraduationDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignApplication.class, args);
    }

}
