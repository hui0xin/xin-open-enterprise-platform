package com.xin.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * API网关服务
 */
@SpringBootApplication(scanBasePackages = {"com.xin"})
@EnableFeignClients(basePackages = {"com.xin"})
public class ApiGateWayApplication {

    public static void main(String[] args) {

        SpringApplication.run(ApiGateWayApplication.class, args);
    }

}
