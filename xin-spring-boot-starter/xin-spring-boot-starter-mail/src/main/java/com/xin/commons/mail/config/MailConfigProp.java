package com.xin.commons.mail.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class MailConfigProp {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;
    @Value("${spring.mail.debug}")
    private String mailDebug;
    @Value("${spring.mail.smtp.auth}")
    private String smtpAuth;
    @Value("${spring.mail.smtp.port}")
    private Integer smtpPort;
    @Value("${spring.mail.smtp.ssl.enable}")
    private String sslEnable;
    @Value("${spring.mail.smtp.ssl.socketFactory}")
    private String socketFactory;
    @Value("${spring.mail.smtp.starttls.enable}")
    private String starttlsEnable;
    @Value("${spring.mail.smtp.starttls.required}")
    private String starttlsRequired;
}
