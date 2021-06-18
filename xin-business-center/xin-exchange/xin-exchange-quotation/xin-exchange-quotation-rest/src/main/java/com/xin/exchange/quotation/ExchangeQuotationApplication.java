package com.xin.exchange.quotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 行情
 */
@SpringBootApplication(scanBasePackages = {"com.xin.exchange.quotation", "com.xin.commons"})
public class ExchangeQuotationApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExchangeQuotationApplication.class, args);
    }

}
