package com.xin.commons.support.exception;

import com.xin.commons.support.errorcode.HttpErrorCode;
import lombok.Getter;

/**
 * 系统异常类
 * @author: xin
 */
@Getter
public class HttpException extends BizException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2127079256702935600L;

    private int code;

    private String message;

    public HttpException() {
        super();
    }

    public HttpException(final HttpErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public HttpException(final HttpErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
