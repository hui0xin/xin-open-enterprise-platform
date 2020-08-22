package com.xin.file.upload.common.exception;

import com.xin.commons.support.exception.BizException;
import com.xin.file.upload.common.errorcode.FileUploadErrorCode;
import lombok.Getter;

/**
 * 业务层异常类
 */
@Getter
public class FileUploadException extends BizException {

    private int code;

    private String message;

    public FileUploadException() {
        super();
    }

    public FileUploadException(final FileUploadErrorCode errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }

    public FileUploadException(final FileUploadErrorCode errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
