package com.xin.commons.support.il8n;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;

/**
 * 自定义的国际化Message资源加载类
 **/
public class CustomResourceBundleMessageSource extends ResourceBundleMessageSource {
    /**
     * 获取指定前辍对应的所有的Message集合
     *
     * @param locale       @see Locale
     * @param codePrefixes code前辍
     * @return Map[Key, Value]
     */
    public Map<String, String> getMessages(final Locale locale, final String... codePrefixes) {
        final Map<String, String> messagesMap = new HashMap<>(128);
        if (ArrayUtils.isEmpty(codePrefixes)) {
            return messagesMap;
        }

        final Set<String> basenames = this.getBasenameSet();
        for (final String basename : basenames) {
            final ResourceBundle bundle = getResourceBundle(basename, locale);
            if (bundle != null) {
                for (final String key : bundle.keySet()) {
                    if (StringUtils.startsWithAny(key, codePrefixes)) {
                        messagesMap.put(key, bundle.getString(key));
                    }
                }
            }
        }
        return messagesMap;
    }
}
