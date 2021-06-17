package com.xin.commons.mail.errorcode;


import com.xin.commons.support.code.ErrorCode;
import com.xin.commons.support.constant.CommonConstant;
import com.xin.commons.support.il8n.LocaleUtils;

/**
 * 业务错误码
 */
public enum MailErrorCode implements ErrorCode {

    /**
     * 邮件发送失败
     */
    MAIL_SEND_FAIL(13000);

    private final int code;

    MailErrorCode(final int code) {
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
