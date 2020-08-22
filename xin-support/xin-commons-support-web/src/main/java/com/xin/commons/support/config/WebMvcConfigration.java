package com.xin.commons.support.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * web 拦截器
 * 这里可以配置多个拦截
 */
@Configuration
public class WebMvcConfigration extends WebMvcConfigurationSupport {

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //自动过滤后缀.do
        configurer.setUseSuffixPatternMatch(false);
        //自动去掉 '/'
        configurer.setUseTrailingSlashMatch(false);
    }

    /**
     * 添加到连接器里面
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(localeChangeInterceptor);
    }
}