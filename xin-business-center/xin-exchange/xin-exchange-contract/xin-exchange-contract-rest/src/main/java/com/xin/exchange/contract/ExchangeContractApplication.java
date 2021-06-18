package com.xin.exchange.contract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 合约
 */
@SpringBootApplication(scanBasePackages = {"com.xin.exchange.contract", "com.xin.commons"})
public class ExchangeContractApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExchangeContractApplication.class, args);
    }

}
