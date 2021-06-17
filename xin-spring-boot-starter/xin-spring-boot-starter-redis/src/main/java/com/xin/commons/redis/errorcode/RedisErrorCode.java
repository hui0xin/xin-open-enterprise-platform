package com.xin.commons.redis.errorcode;

import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.CommonConstant;
import com.xin.commons.support.il8n.LocaleUtils;

/**
 * 业务错误码
 * @author: xin
 */
public enum RedisErrorCode implements ErrorCode {

    /**
     * redis执行失败
     */
    REDIS_EXECUTE_FAIL(12000),
    /**
     * redis失效时间不能为空
     */
    REDIS_EXPIRE_IS_EMPTY(12001),
    /**
     * redis失效时间不能小于0
     */
    REDIS_EXPIRE_LT_0(12002),
    /**
     * redis的key不能为空
     */
    REDIS_KEY_IS_EMPTY(12003),
    /**
     * redis-hash操作的filed不能为空
     */
    REDIS_FIELD_IS_EMPTY(12004),
    /**
     * 递增或递减因子必须大于0
     */
    REDIS_INCR_OR_DECR_GT_0(12005),
    /**
     * 获取两个地址的距离失败
     */
    REDIS_GET_DISTANCE_FAIL(12006),

    ;
    private final int code;

    RedisErrorCode(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {

        return LocaleUtils.getMessage(CommonConstant.BIZCODEPREFIX+code);
    }

    public String getMessage(final Object... args) {

        return LocaleUtils.getMessage(CommonConstant.BIZCODEPREFIX+code, args);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }
}
