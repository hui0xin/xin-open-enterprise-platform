package com.xin.commons.support.exception;

import com.xin.commons.support.errorcode.SystemErrorCode;
import lombok.Getter;

/**
 * 系统异常类
 * @author: xin
 */
@Getter
public class SystemException extends BizException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2127079256702935600L;

    private int code;

    private String message;

    public SystemException() {
        super();
    }

    public SystemException(final SystemErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public SystemException(final SystemErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
