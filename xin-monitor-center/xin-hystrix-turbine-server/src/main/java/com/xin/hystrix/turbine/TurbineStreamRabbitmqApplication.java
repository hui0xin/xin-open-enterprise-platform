package com.xin.hystrix.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * @EnableTurbineStream  service---> rabbitmq -------> turbine ------> Dashboard
 * @EnableTurbine        service---> turbine ------> Dashboard
 */
@EnableTurbineStream
@SpringBootApplication
public class TurbineStreamRabbitmqApplication {
    public static void main(String[] args) {

        SpringApplication.run(TurbineStreamRabbitmqApplication.class, args);
    }
}