package com.xin.commons.mail.bean;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 服务器邮箱登录验证
 */
public class MailAuthenticator extends Authenticator {

    private String user;
    private String pwd;

    public MailAuthenticator(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(user, pwd);
    }
}
