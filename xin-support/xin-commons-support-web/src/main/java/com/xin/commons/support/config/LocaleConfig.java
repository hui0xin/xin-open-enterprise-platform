package com.xin.commons.support.config;

import com.xin.commons.support.il8n.CustomCookieLocaleResolver;
import com.xin.commons.support.il8n.MultiTerminalLocaleChangeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import java.util.Locale;

/**
 * 多语言配置
 */
@Configuration
public class LocaleConfig{

    /**
     * 设置默认为中文
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver localeResolver = new CustomCookieLocaleResolver();
        //保存7天有效
        localeResolver.setLanguageTagCompliant(true);
        localeResolver.setCookieMaxAge(604800);
        localeResolver.setDefaultLocale(Locale.CHINA);
        localeResolver.setCookieName("locale");
        localeResolver.setCookiePath("/");
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {

        return new MultiTerminalLocaleChangeInterceptor();
    }

}

 