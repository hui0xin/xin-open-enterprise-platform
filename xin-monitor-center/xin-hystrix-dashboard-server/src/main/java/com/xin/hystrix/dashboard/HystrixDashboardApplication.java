package com.xin.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @EnableHystrixDashboard 展示熔断器仪表盘
 * @EnableCircuitBreaker 开启服务保护机制
 */
@EnableHystrixDashboard
@EnableCircuitBreaker
@SpringBootApplication
public class HystrixDashboardApplication {
    public static void main(String[] args) {

        SpringApplication.run(HystrixDashboardApplication.class, args);
    }

}