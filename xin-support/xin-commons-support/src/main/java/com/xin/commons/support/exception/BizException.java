package com.xin.commons.support.exception;

import lombok.Getter;

/**
 * 通用业务异常类
 * @author: xin
 **/
@Getter
public class BizException extends RuntimeException {

    private int code;

    /**
     * 构造函数
     */
    public BizException() {
        super();
    }

    /**
     * 信息
     *
     * @param msg
     */
    public BizException(final String msg) {
        super(msg);
    }

    /**
     * @param msg   信息
     * @param cause 原因
     */
    public BizException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

}
