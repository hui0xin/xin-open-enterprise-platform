package com.xin.commons.redis.exception;

import com.xin.commons.redis.errorcode.RedisErrorCode;
import com.xin.commons.support.exception.BizException;
import lombok.Getter;

/**
 * 业务层异常类
 * @author: xin
 */
@Getter
public class RedisException extends BizException {

    private int code;

    public RedisException() {
        super();
    }

    public RedisException(final RedisErrorCode errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public RedisException(final RedisErrorCode errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
