package com.xin.commons.support.il8n;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * 自定义CookieLocaleResolver
 * 为了兼容language语言en-US/en_US两种模式
 */
public class CustomCookieLocaleResolver extends CookieLocaleResolver {
    private static final char UNDERLINE = '_';
    private static final char DASH = '-';

    @Override
    protected Locale parseLocaleValue(final String locale) {
        if (locale == null) {
            return this.getDefaultLocale();
        }
        return StringUtils.parseLocaleString(locale.replace(DASH, UNDERLINE));
    }

    @Override
    protected String toLocaleValue(final Locale locale) {

        return locale.toString();
    }
}
