package com.xin.exchange.trade.config;

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

    private static final String BASE_PACKAGE = "com.xin.exchange.trade";

    @Bean
    public Docket api() {
        final Docket docket =
                new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                        .paths(PathSelectors.ant("/**"))
                        //扫描的包 如果不做过滤，会扫描所有的包，这里根据自己的需要修改
                        .paths(Predicates.or(
                                PathSelectors.regex("/demo/xx/.*"),
                                PathSelectors.regex("/demo/bb/.*")
                        ))
                        .build()
                        //.globalOperationParameters(pars)
                        .apiInfo(apiInfo());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("撮合")
                .description("撮合-api文档")
                .termsOfServiceUrl("http://www.xin.com/")
                .contact(new Contact("xin", "http://www.xin.com/", ""))
                .version("1.0")
                .build();

    }
}
