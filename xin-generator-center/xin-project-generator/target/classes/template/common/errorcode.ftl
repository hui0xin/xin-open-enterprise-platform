package com.${packageName}.common.errorcode;

import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.CommonConstant;
import com.xin.commons.support.il8n.LocaleUtils;

/**
 * @Description: 业务错误码，错误信息在messages中配置
 * @author: 系统
 */
public enum ${className}ErrorCode implements ErrorCode {

    /**
     * 配置错误
     */
    COFIG_IS_ERROR(300011),

    /**
     * xxxxx
     */
    FILE_DOWN_FAIL(300012)
    ;

    private final int code;

    ${className}ErrorCode(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(CommonConstant.BIZCODEPREFIX+ this.code);
    }

    public String getMessage(final Object... args) {
        return LocaleUtils.getMessage(CommonConstant.BIZCODEPREFIX+ this.code, args);
    }
}
