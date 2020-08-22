package com.xin.api.gateway.common.errorcode;


import com.xin.commons.support.code.ErrorCode;

/**
 * 业务错误码
 */
public enum ApiGateWayErrorCodeEnum implements ErrorCode {
    TOKEN_IS_EMPTY(1001, "token为空"),
    USER_IS_EMPTY(1003, "用户信息为空"),
    TOKEN_AUTH_FAILED(120033, "token认证失败"),
    SIG_AUTH_FAILED(120044, "签名校验失败"),
    GET_USER_FAIL(120052, "获取用户失败"),
    FORCE_UPGRADE(129900, "发现新版本"),
    SERVER_BUSY(129901, "服务器繁忙，请稍后重试"),
    USER_PHONE_IS_EXIST(129902, "该手机号码已经存在"),
    PLEASE_USE_OFFICIAL_APP(120076, "请下载正式版本使用");

    private final int code;

    private final String message;

    ApiGateWayErrorCodeEnum(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }
}
