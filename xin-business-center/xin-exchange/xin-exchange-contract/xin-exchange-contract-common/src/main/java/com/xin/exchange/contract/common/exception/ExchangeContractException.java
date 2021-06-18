package com.xin.exchange.contract.common.exception;

import com.xin.commons.support.exception.BizException;
import com.xin.exchange.contract.common.errorcode.ExchangeContractErrorCode;
import lombok.Getter;

/**
 * @Description: 业务异常类
 * @author: 系统
 */
@Getter
public class ExchangeContractException extends BizException {

    private int code;

    private String message;

    public ExchangeContractException() {
        super();
    }

    public ExchangeContractException(final ExchangeContractErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ExchangeContractException(final ExchangeContractErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
