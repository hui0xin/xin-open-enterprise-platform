package com.xin.pay.common.errorcode;

import com.xin.commons.support.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务错误码
 */
@Getter
@AllArgsConstructor
public enum PayErrorCodeEnum implements ErrorCode {

    /**
     * 服务器未知错误
     */
    SYSTEM_ERROR(99, "服务器未知错误"),

    ;

    private final int code;
    private final String message;

}
