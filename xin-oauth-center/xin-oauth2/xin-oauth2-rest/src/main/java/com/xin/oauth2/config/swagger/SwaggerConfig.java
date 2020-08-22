package com.xin.oauth2.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.xin.oauth2.controller";

    @Bean
    public Docket api() {
        final Docket docket =
                new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                        .paths(PathSelectors.ant("/**"))
                        .build()
                        //.globalOperationParameters(pars)
                        .apiInfo(apiInfo());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("OAuth2.0 认证服务")
                .description("OAuth2.0 认证服务-内部接口调用")
                .termsOfServiceUrl("http://www.xin.com/")
                .contact(new Contact("xin", "http://www.xin.com/", ""))
                .version("1.0")
                .build();

    }
}
