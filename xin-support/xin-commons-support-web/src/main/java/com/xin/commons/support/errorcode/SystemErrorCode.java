package com.xin.commons.support.errorcode;

import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.CommonConstant;
import com.xin.commons.support.il8n.LocaleUtils;
import java.util.Arrays;

/**
 * 系统异常 错误码
 * @author: xin
 */
public enum SystemErrorCode implements ErrorCode {
    /**
     * 成功
     */
    SUCCESS(0),
    /**
     * 系统内部错误
     */
    SYSTEM_ERROR(1),
    /**
     * 未定义的错误
     */
    UNKNOWN_ERROR(-1),
    /**
     * api 已禁用
     */
    API_DISABLED(-2),
    /**
     * HttpMessageConverter转换失败
     */
    HTTP_MESSAGE_NOT_READABLE(900),
    /**
     * 参数验证失败
     */
    DATA_VALIDATION_FAILURE(901),
    /**
     * 绑定的参数验证失败
     */
    DATA_BIND_VALIDATION_FAILURE(902),
    /**
     * 方法参数验证失败
     */
    METHOD_ARGUMENT_NOT_VALID(903),
    /**
     * SQL语句执行出错
     */
    SQL_EXECUTE_FAILURE(904),
    /**
     * 对象为空
     */
    OBJECT_IS_EMPTY(905),
    /**
     * 查询结果为空
     */
    QUERY_RESULT_IS_EMPTY(906),
    /**
     * 保存对象失败
     */
    ADD_OBJECT_FAIL(907),
    /**
     * 修改对象失败
     */
    UPDATE_OBJECT_FAIL(908),
    /**
     * 删除对象失败
     */
    DELETE_OBJECT_FAIL(909),
    /**
     * 查询对象失败
     */
    QUERY_OBJECT_FAIL(910),
    /**
     * 查询失败
     */
    QUERY_FAIL(911),
    ;

    private final int code;

    SystemErrorCode(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(CommonConstant.SYSCODEPREFIX+code);
    }

    public String getMessage(final Object... args) {
        return LocaleUtils.getMessage(CommonConstant.SYSCODEPREFIX+code, args);
    }

    /**
     * 通过code获取错误，如果找不到，就是位置异常
     * @param code
     * @return
     */
    public static SystemErrorCode valueOf(final int code) {
        return Arrays.stream(values())
                .filter(x -> x.getCode() == code)
                .findFirst()
                .orElse(SystemErrorCode.UNKNOWN_ERROR);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }

}
