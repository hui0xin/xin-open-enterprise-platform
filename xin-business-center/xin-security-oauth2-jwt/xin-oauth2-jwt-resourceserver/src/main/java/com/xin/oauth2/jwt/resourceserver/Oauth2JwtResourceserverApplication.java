package com.xin.oauth2.jwt.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * oauth2.0 jwt 资源服务
 */
@SpringBootApplication(scanBasePackages = {"com.xin.oauth2.jwt.resourceserver", "com.xin.commons"})
public class Oauth2JwtResourceserverApplication {

    public static void main(String[] args) {

        SpringApplication.run(Oauth2JwtResourceserverApplication.class, args);
    }

}
