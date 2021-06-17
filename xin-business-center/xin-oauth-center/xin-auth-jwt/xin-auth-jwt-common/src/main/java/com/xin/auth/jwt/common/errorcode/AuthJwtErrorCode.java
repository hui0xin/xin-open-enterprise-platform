package com.xin.auth.jwt.common.errorcode;

import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.CommonConstant;
import com.xin.commons.support.il8n.LocaleUtils;

/**
 * 业务错误码
 */
public enum AuthJwtErrorCode implements ErrorCode {

    /**
     * token为空
     */
    TOKEN_IS_NULL(17000),
    /**
     * 刷新token失败
     */
    REFRESH_TOKEN_IS_FAIL(17001),
    /**
     * 退出失败
     */
    LOGOUT_TOKEN_IS_FAIL(17002),
    /**
     * 获取用户信息失败
     */
    GET_USER_INFO_IF_FAIL(17003),
    /**
     * 用户信息为空
     */
    USER_INFO_IS_NULL(17004),

    ;

    private final int code;


    AuthJwtErrorCode(final int code) {
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
