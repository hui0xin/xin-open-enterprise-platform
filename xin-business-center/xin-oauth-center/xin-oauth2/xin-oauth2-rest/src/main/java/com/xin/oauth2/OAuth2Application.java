package com.xin.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.xin.oauth2", "com.xin.commons"})
public class OAuth2Application {

    public static void main(String[] args) {

        SpringApplication.run(OAuth2Application.class, args);
    }

}
