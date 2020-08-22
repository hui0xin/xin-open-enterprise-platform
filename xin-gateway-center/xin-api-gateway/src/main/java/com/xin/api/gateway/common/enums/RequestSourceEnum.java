package com.xin.api.gateway.common.enums;

/**
 * 请求来源枚举类
 */
public enum RequestSourceEnum {

    ANDROID_SOURCE("1", "安卓"),
    IOS_SOURCE("2", "iOS"),
    H5_SOURCE("3", "H5");

    private final String value;
    private final String name;

    RequestSourceEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
