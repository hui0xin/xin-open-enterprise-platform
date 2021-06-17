package com.xin.pay.common.exception;

import com.xin.commons.support.exception.BizException;
import com.xin.pay.common.errorcode.PayErrorCodeEnum;
import lombok.Getter;

/**
 * 业务层异常类
 */
@Getter
public class PayException extends BizException {

    private int code;

    private String message;

    public PayException() {
        super();
    }

    public PayException(final PayErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }

    public PayException(final PayErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
