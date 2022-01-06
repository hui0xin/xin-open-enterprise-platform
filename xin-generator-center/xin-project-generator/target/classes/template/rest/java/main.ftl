package com.${packageName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ${projectDesc}
 */
@SpringBootApplication(scanBasePackages = {"com.${packageName}", "com.xin.commons"})
public class ${className}Application {

    public static void main(String[] args) {

        SpringApplication.run(${className}Application.class, args);
    }

}
