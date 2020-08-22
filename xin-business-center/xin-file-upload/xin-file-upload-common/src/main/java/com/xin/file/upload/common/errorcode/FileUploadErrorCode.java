package com.xin.file.upload.common.errorcode;

import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.CommonConstant;
import com.xin.commons.support.il8n.LocaleUtils;

/**
 * 业务错误码
 */
public enum FileUploadErrorCode implements ErrorCode {

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAIL(50000),
    /**
     * 上传文件为空
     */
    FILE_IS_NULL(50001),
    /**
     * 文件目录为空
     */
    FILE_PATH_IS_NULL(50002),
    /**
     * 文件下载地址为空
     */
    FILE_DOWN_URL_IS_NULL(50003),
    /**
     * 文件删除失败
     */
    FILE_DELETE_FAIL(50004),
    /**
     * 文件下载失败
     */
    FILE_DOWN_FAIL(50005),
    /**
     * 上传的不是图片
     */
    FILE_IS_NOT_IMAGES(50006);

    private final int code;


    FileUploadErrorCode(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(CommonConstant.BIZCODEPREFIX+ this.code);
    }

    public String getMessage(final Object... args) {
        return LocaleUtils.getMessage(CommonConstant.BIZCODEPREFIX+ this.code, args);
    }

}
