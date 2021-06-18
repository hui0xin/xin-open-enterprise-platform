package com.xin.exchange.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 撮合
 */
@SpringBootApplication(scanBasePackages = {"com.xin.exchange.trade", "com.xin.commons"})
public class ExchangeTradeApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExchangeTradeApplication.class, args);
    }

}
