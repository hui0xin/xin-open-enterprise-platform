package com.xin.sso.jwt.common.exception;

import com.xin.sso.jwt.common.errorcode.AuthJwtErrorCode;
import com.xin.commons.support.exception.BizException;
import lombok.Getter;

/**
 * 业务层异常类
 */
@Getter
public class AuthJwtException extends BizException {

    private int code;
    private String message;

    public AuthJwtException() {
        super();
    }

    public AuthJwtException(final AuthJwtErrorCode errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }

    public AuthJwtException(final AuthJwtErrorCode errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
