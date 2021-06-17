package com.xin.commons.mail.config;

import com.xin.commons.mail.bean.MailAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import javax.mail.Session;
import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Autowired
    private MailConfigProp mailConfigProp;

    public Properties getMailProperties() {
        Properties properties = new Properties();
        //需要验证登录名和密码
        properties.setProperty("mail.smtp.auth", mailConfigProp.getSmtpAuth());
        //需要TLS认证 保证发送邮件安全验证
        properties.setProperty("mail.smtp.starttls.enable", mailConfigProp.getStarttlsEnable());
        //
        properties.setProperty("mail.smtp.starttls.required", mailConfigProp.getStarttlsRequired());
        //表示开启 DEBUG 模式，这样，邮件发送过程的日志会在控制台打印出来，方便排查错误
        properties.setProperty("mail.debug", mailConfigProp.getMailDebug());
        //开启ssl
        properties.setProperty("mail.smtp.ssl.enable", mailConfigProp.getSslEnable());
        //配饰 SSL 加密工厂
        properties.setProperty("mail.smtp.ssl.socketFactory", mailConfigProp.getSocketFactory());
        return properties;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //发送邮件的协议
        javaMailSender.setProtocol("smtp");
        //邮箱服务器 smtp.exmail.qq.com
        javaMailSender.setHost(mailConfigProp.getHost());
        //端口
        javaMailSender.setPort(mailConfigProp.getSmtpPort());
        //#发送者的邮箱账号,不要加@xx.com
        javaMailSender.setUsername(mailConfigProp.getUsername());
        //密码
        javaMailSender.setPassword(mailConfigProp.getPassword());
        //UTF-8
        javaMailSender.setDefaultEncoding(mailConfigProp.getDefaultEncoding());
        //根据Properties，Authenticator创建Session
        javaMailSender.setSession(Session.getDefaultInstance(getMailProperties(),
                new MailAuthenticator(mailConfigProp.getUsername(),mailConfigProp.getPassword())));
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;

    }

}