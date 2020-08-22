package com.xin.oauth2.common.exception;

import com.xin.oauth2.common.errorcode.OAuth2ErrorCode;
import com.xin.commons.support.exception.BizException;
import lombok.Getter;

/**
 * 业务层异常类
 */
@Getter
public class OAuth2Exception extends BizException {

    private int code;
    private String message;

    public OAuth2Exception() {
        super();
    }

    public OAuth2Exception(final OAuth2ErrorCode errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }

    public OAuth2Exception(final OAuth2ErrorCode errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
