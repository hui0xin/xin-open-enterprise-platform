package com.xin.commons.support.errorcode;

import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.CommonConstant;
import com.xin.commons.support.il8n.LocaleUtils;

/**
 * http 错误码
 * @author: xin
 */
public enum HttpErrorCode implements ErrorCode {
    /**
     * 错误的请求
     */
    BAD_REQUEST(400),
    /**
     * 未授权不能访问
     */
    UNAUTHORIZED(401),
    /**
     * 访问禁止
     */
    FORBIDDEN(403),
    /**
     * 请求地址不存在
     */
    NOT_FOUND(404),
    /**
     * 不支持该HTTP Method
     */
    METHOD_NOT_ALLOWED(405),
    /**
     * 当前媒体类型不支持
     */
    UNSUPPORTED_MEDIA_TYPE(415),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    HttpErrorCode(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(CommonConstant.HTTPCODEPREFIX+code);
    }

    public String getMessage(final Object... args) {
        return LocaleUtils.getMessage(CommonConstant.HTTPCODEPREFIX+code, args);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }

}
