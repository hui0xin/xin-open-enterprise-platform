package com.xin.commons.support.log.feign;

import org.slf4j.Logger;

/**
 * info feign 日志
 * @author: xin
 */
public class InfoFeignLogger extends feign.Logger {

    private final Logger logger;

    public InfoFeignLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        if (logger.isInfoEnabled()) {
            if (isPrintLog(format)) {
                logger.info("feign--> " + String.format(methodTag(configKey) + format, args));
            }
        }
    }

    private boolean isPrintLog(String format) {
        if (format.startsWith("---> %s %s ")) {
            return true;
        } else if (format.startsWith("<--- HTTP")) {
            return true;
        } else if (format.equals("%s")) {
            return true;
        }
        return false;
    }
}