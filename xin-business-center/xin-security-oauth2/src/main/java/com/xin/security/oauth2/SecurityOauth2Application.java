package com.xin.security.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * oauth2.0 database demo
 */
@SpringBootApplication(scanBasePackages = {"com.xin.security.oauth2", "com.xin.commons"})
public class SecurityOauth2Application {

    public static void main(String[] args) {

        SpringApplication.run(SecurityOauth2Application.class, args);
    }

}
