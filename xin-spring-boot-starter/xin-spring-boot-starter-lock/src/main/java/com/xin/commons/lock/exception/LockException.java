package com.xin.commons.lock.exception;

import com.xin.commons.lock.errorcode.LockErrorCode;
import com.xin.commons.support.exception.BizException;
import lombok.Getter;

/**
 * 业务层异常类
 * @author: xin
 */
@Getter
public class LockException extends BizException {

    private int code;

    public LockException() {
        super();
    }

    public LockException(final LockErrorCode errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public LockException(final LockErrorCode errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
