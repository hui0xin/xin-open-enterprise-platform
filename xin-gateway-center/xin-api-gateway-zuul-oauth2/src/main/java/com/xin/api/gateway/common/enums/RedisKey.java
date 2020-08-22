package com.xin.api.gateway.common.enums;

import lombok.Getter;

/**
 * redis key
 */
@Getter
public enum RedisKey {

    /**
     * 是否从数据库读取路由
     */
    UPGRADE_KEY_FOR_ANDROID("gateway:upgrade:android", "强制升级-安卓端", 60 * 30L),

    UPGRADE_KEY_FOR_IOS("gateway:upgrade:iOS", "强制升级-iOS端", 60 * 30L),

    LOGIN_WHITE_LIST_PHONE_KEY("gateway:login_white_list:%s", "修改白名单时加redis锁", 10L),

    LOGIN_WHITE_LIST_KEY("gateway:login_white_list", "登录白名单", 60 * 60 * 24 * 30L);

    private String key;

    private String remark;

    private Long time;

    RedisKey(String key, String remark, Long time) {
        this.key = key;
        this.remark = remark;
        this.time = time;
    }
}
