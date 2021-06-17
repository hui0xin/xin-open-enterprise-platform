package com.xin.oauth2.common.errorcode;

import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.CommonConstant;
import com.xin.commons.support.il8n.LocaleUtils;

/**
 * 业务错误码
 */
public enum OAuth2ErrorCode implements ErrorCode {

    /**
     * client_id为空
     */
    CLIENT_ID_IS_NULL(16000),
    /**
     * client_secret为空
     */
    CLIENT_SECRET_IS_NULL(16001),
    /**
     * client_secret不存在
     */
    CLIENT_SECRET_NOT_EXIST(16002),

    ;
    private final int code;


    OAuth2ErrorCode(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(CommonConstant.BIZCODEPREFIX+code);
    }

    public String getMessage(final Object... args) {
        return LocaleUtils.getMessage(CommonConstant.BIZCODEPREFIX+code, args);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }

}
