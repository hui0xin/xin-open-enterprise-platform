package com.xin.adsystem.config;

import com.google.common.base.Predicates;
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

    private static final String BASE_PACKAGE = "com.xin.pay";

    @Bean
    public Docket api() {
        final Docket docket =
                new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
//                        .paths(PathSelectors.ant("/**"))
                        //扫描的包
                        .paths(Predicates.or(
                                PathSelectors.regex("/adsystem/xx/.*"),
                                PathSelectors.regex("/adsystem/new/.*"),
                                PathSelectors.regex("/adsystem/share/.*")
                        ))
                        .build()
                        //.globalOperationParameters(pars)
                        .apiInfo(apiInfo());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xin-程序化广告系统")
                .description("xin-程序化广告系统-api文档")
                .termsOfServiceUrl("http://www.xinge.com/")
                .contact(new Contact("xin", "http://www.xin.com/", ""))
                .version("1.0")
                .build();

    }
}
