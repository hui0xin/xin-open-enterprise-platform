package com.xin.hystrix.dashboard.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * EnableTurbineStream 开启turbine
 * @EnableTurbineStream  service---> rabbitmq -------> turbine ------> Dashboard
 * @EnableTurbine        service---> turbine ------> Dashboard
 * @EnableCircuitBreaker 开启服务保护机制
 */
@EnableHystrixDashboard
@EnableTurbineStream
@EnableCircuitBreaker
@SpringBootApplication
public class DashboardTurbineApplication {
    public static void main(String[] args) {

        SpringApplication.run(DashboardTurbineApplication.class, args);
    }
}