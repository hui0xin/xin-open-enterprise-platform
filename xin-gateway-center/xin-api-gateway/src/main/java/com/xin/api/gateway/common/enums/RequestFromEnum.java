package com.xin.api.gateway.common.enums;


/**
 * Created by xin
 * 规则枚举
 */
public enum RequestFromEnum {

    APP("app", "app端"),
    PC("pc", "pc端"),
    MINI("mini", "小程序"),
    INNER("inner", "内部接口"),
    OUTSIDE("outside", "外链");

    private final String code;
    private final String name;

    RequestFromEnum(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

}
