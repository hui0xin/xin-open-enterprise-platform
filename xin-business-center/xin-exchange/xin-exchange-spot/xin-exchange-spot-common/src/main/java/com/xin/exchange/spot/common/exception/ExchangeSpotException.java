package com.xin.exchange.spot.common.exception;

import com.xin.commons.support.exception.BizException;
import com.xin.exchange.spot.common.errorcode.ExchangeSpotErrorCode;
import lombok.Getter;

/**
 * @Description: 业务异常类
 * @author: 系统
 */
@Getter
public class ExchangeSpotException extends BizException {

    private int code;

    private String message;

    public ExchangeSpotException() {
        super();
    }

    public ExchangeSpotException(final ExchangeSpotErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ExchangeSpotException(final ExchangeSpotErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
