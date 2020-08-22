package com.xin.api.gateway.common.exception;

import com.xin.api.gateway.common.errorcode.GateWayErrorCodeEnum;
import com.xin.commons.support.exception.BizException;
import lombok.Getter;

/**
 * 业务层异常类
 */
@Getter
public class GateWayException extends BizException {

    private int code;

    public GateWayException() {
        super();
    }

    public GateWayException(final GateWayErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public GateWayException(final GateWayErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
