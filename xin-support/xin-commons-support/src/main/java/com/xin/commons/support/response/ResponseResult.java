package com.xin.commons.support.response;

import java.io.Serializable;
import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.AppEnvConsts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 统一返回实体，使用swagger做文档
 * @NoArgsConstructor ： 生成一个无参数的构造方法
 * @AllArgsContructor 会生成一个包含所有变量
 * @author: xin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(value="统一返回实体",description="统一返回实体")
public class ResponseResult<T> implements Serializable {

    @ApiModelProperty(name = "code", value ="返回code ，0表示成功，>0表示失败,<0系统保留",required = true, example = "0")
    private int code = 0;

    @ApiModelProperty(name = "msg",value ="提示信息",required = true, example = "成功")
    private String msg = "";

    @ApiModelProperty(name = "detailMsg",value ="详细提示信息",required = true, example = "成功详情")
    private String detailMsg = "";

    @ApiModelProperty(name = "result",value ="返回结果数据",required = true, example = "结果")
    private T data;

    /** 成功 */
    public static ResponseResult success() {
        return success(null);
    }

    /** 成功 返回数据 */
    public static <T> ResponseResult success(final T data) {
        return build(0, "", "", data);
    }

    /** 失败 返回code和msg */
    public static ResponseResult failure(final int code, final String msg) {
        return failure(code, msg, "");
    }

    /** 失败 返回 ErrorCode */
    public static ResponseResult failure(final ErrorCode errorCode) {
        return failure(errorCode, "");
    }

    /**
     * 失败 返回 ErrorCode
     * 如果不是线上环境，可以返回错误信息
     */
    public static ResponseResult failure(final ErrorCode errorCode, final Throwable ex) {
        return failure(errorCode, AppEnvConsts.isProductionMode() ? "" : ExceptionUtils.getStackTrace(ex));
    }

    /**
     * 失败 返回 ErrorCode 和 错误详情
     */
    public static ResponseResult failure(final ErrorCode errorCode, final String detailMsg) {
        return failure(errorCode.getCode(), errorCode.getMessage(), detailMsg);
    }

    /**
     * 失败 返回 ErrorCode 和 具体数据
     */
    public static <T> ResponseResult failure(final ErrorCode errorCode, final T data) {
        return build(errorCode.getCode(), errorCode.getMessage(), "", data);
    }

    /**
     * 失败 返回 msg
     */
    public static ResponseResult failure(final String msg) {
        return failure(-1, msg, "");
    }

    /**
     * 失败 返回 msg 和 错误详情
     */
    public static ResponseResult failure(final String msg, final String detailMsg) {
        return failure(-1, msg, detailMsg);
    }

    /**
     * 失败 返回 code，msg 和 detailMsg
     */
    public static ResponseResult failure(final int code, final String msg, final String detailMsg) {
        return build(code, msg, detailMsg, null);
    }

    /**
     * 失败 返回 code，msg 和 错误详情
     * 如果不是线上环境，返回具体错误
     */
    public static ResponseResult failure(final int code, final String msg, final Throwable ex) {
        return build(code, msg, AppEnvConsts.isProductionMode() ? "" : ExceptionUtils.getStackTrace(ex), null);
    }

    /**
     * 生成返回结果
     */
    public static <T> ResponseResult build(final int code, final String msg, final String detailMsg, final T data) {
        return new ResponseResult<>(code, msg, detailMsg, data);
    }
}