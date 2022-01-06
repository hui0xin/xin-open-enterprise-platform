package com.xin.sso.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * auth服务
 */
@SpringBootApplication(scanBasePackages = {"com.xin.sso.jwt", "com.xin.commons"})
public class AuthJWTApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthJWTApplication.class, args);
    }

}
