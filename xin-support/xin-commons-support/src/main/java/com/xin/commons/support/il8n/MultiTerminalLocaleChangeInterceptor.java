package com.xin.commons.support.il8n;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 多端(web,android,iphone)国际化语言切换拦截器
 **/
public class MultiTerminalLocaleChangeInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler) {
        //设置多语言
        LocaleUtils.setLocale(request, response);
        return true;
    }
}
