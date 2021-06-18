package com.xin.exchange.trade.common.exception;

import com.xin.commons.support.exception.BizException;
import com.xin.exchange.trade.common.errorcode.ExchangeTradeErrorCode;
import lombok.Getter;

/**
 * @Description: 业务异常类
 * @author: 系统
 */
@Getter
public class ExchangeTradeException extends BizException {

    private int code;

    private String message;

    public ExchangeTradeException() {
        super();
    }

    public ExchangeTradeException(final ExchangeTradeErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ExchangeTradeException(final ExchangeTradeErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
