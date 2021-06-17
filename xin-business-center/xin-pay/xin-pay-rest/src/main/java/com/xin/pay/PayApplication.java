package com.xin.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 支付系统
 */
@SpringBootApplication(scanBasePackages = {"com.xin.pay", "com.xin.commons"})
public class PayApplication {

    public static void main(String[] args) {

        SpringApplication.run(PayApplication.class, args);
    }

}
