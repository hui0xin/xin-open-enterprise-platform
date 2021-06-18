package com.xin.exchange.spot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 现货
 */
@SpringBootApplication(scanBasePackages = {"com.xin.exchange.spot", "com.xin.commons"})
public class ExchangeSpotApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExchangeSpotApplication.class, args);
    }

}
