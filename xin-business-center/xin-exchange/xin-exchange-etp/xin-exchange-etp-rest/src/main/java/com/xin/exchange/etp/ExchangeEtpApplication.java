package com.xin.exchange.etp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * etp
 */
@SpringBootApplication(scanBasePackages = {"com.xin.exchange.etp", "com.xin.commons"})
public class ExchangeEtpApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExchangeEtpApplication.class, args);
    }

}
