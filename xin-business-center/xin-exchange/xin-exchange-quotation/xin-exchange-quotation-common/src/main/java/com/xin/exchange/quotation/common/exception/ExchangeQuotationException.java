package com.xin.exchange.quotation.common.exception;

import com.xin.commons.support.exception.BizException;
import com.xin.exchange.quotation.common.errorcode.ExchangeQuotationErrorCode;
import lombok.Getter;

/**
 * @Description: 业务异常类
 * @author: 系统
 */
@Getter
public class ExchangeQuotationException extends BizException {

    private int code;

    private String message;

    public ExchangeQuotationException() {
        super();
    }

    public ExchangeQuotationException(final ExchangeQuotationErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ExchangeQuotationException(final ExchangeQuotationErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
