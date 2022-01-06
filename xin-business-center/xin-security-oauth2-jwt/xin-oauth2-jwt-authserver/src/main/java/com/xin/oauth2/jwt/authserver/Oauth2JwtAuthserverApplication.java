package com.xin.oauth2.jwt.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * oauth2.0 jwt 认证服务
 */
@SpringBootApplication(scanBasePackages = {"com.xin.oauth2.jwt.authserver", "com.xin.commons"})
public class Oauth2JwtAuthserverApplication {

    public static void main(String[] args) {

        SpringApplication.run(Oauth2JwtAuthserverApplication.class, args);
    }

}
