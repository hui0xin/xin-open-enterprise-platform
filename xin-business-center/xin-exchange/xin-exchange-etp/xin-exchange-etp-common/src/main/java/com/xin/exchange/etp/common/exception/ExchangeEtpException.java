package com.xin.exchange.etp.common.exception;

import com.xin.commons.support.exception.BizException;
import com.xin.exchange.etp.common.errorcode.ExchangeEtpErrorCode;
import lombok.Getter;

/**
 * @Description: 业务异常类
 * @author: 系统
 */
@Getter
public class ExchangeEtpException extends BizException {

    private int code;

    private String message;

    public ExchangeEtpException() {
        super();
    }

    public ExchangeEtpException(final ExchangeEtpErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ExchangeEtpException(final ExchangeEtpErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
