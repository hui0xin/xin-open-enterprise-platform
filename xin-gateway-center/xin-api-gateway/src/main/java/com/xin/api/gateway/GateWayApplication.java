package com.xin.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关服务
 * 协议转换，路由转发
 * 流量聚合，对流量进行监控，日志输出
 * 作为整个系统的前端工程，对流量进行控制，有限流的作用
 * 作为系统的前端边界，外部流量只能通过网关才能访问系统
 * 可以在网关层做权限的判断
 * 可以在网关层做缓存
 */
@EnableFeignClients(basePackages = {"com.xin"})
@SpringBootApplication(scanBasePackages = {"com.xin"})
public class GateWayApplication {

    public static void main(String[] args) {

        SpringApplication.run(GateWayApplication.class, args);
    }

}
