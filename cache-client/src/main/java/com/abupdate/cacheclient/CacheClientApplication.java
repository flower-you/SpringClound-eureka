package com.abupdate.cacheclient;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan(basePackages = "com.abupdate.cacheclient.mapper")
@SpringBootApplication
@EnableEurekaClient
public class CacheClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheClientApplication.class, args);
    }

}
