package com.${packageName}.common.exception;

import com.xin.commons.support.exception.BizException;
import com.${packageName}.common.errorcode.${className}ErrorCode;
import lombok.Getter;

/**
 * @Description: 业务异常类
 * @author: 系统
 */
@Getter
public class ${className}Exception extends BizException {

    private int code;

    private String message;

    public ${className}Exception() {
        super();
    }

    public ${className}Exception(final ${className}ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ${className}Exception(final ${className}ErrorCode errorCode, final Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
