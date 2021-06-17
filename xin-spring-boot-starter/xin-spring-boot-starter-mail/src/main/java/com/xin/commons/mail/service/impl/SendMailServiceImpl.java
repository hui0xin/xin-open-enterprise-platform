package com.xin.commons.mail.service.impl;

import com.xin.commons.mail.bean.InlineResource;
import com.xin.commons.mail.errorcode.MailErrorCode;
import com.xin.commons.mail.exception.MailException;
import com.xin.commons.mail.service.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送普通文本邮件
     * @param subject 主标题 必传
     * @param content 内容  必传
     * @param to      发送给谁 邮箱 必传
     * @param cc      抄送给谁 邮箱 没有可以传 null
     * @param bcc     秘送给谁 邮箱 没有可以传 null
     */
    @Async("mailTaskExecutor")
    @Override
    public void sendSimpleMail(String subject, String content, String[] to, String[] cc,String[] bcc) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置邮件主标题
        simpleMailMessage.setSubject(subject);
        //设置内容
        simpleMailMessage.setText(content);
        //设置邮件发送日期
        simpleMailMessage.setSentDate(new Date());
        //设置邮件发送者
        simpleMailMessage.setFrom(from);
        //设置邮件接收者 可以有多个
        simpleMailMessage.setTo(to);
        if(!Objects.isNull(cc)){
            //设置邮件抄送人，可以有多个抄送人
            simpleMailMessage.setCc(cc);
        }
        if(!Objects.isNull(bcc)){
            //设置隐秘抄送人，可以有多个
            simpleMailMessage.setBcc(bcc);
        }
        try {
            javaMailSender.send(simpleMailMessage);
            log.info("发送给" + to + "的简单邮件已经发送。 subject：" + subject);
        } catch (Exception e) {
            log.error("发送给" + to + "的简单邮件失败:{}", ExceptionUtils.getStackTrace(e));
            throw new MailException(MailErrorCode.MAIL_SEND_FAIL);
        }
    }

    /**
     * 发送html邮件
     * @param subject 主标题 必传
     * @param content 内容  必传
     * @param to      发送给谁 邮箱 必传
     * @param cc      抄送给谁 邮箱 没有可以传 null
     * @param bcc     秘送给谁 邮箱 没有可以传 null
     */
    @Async("mailTaskExecutor")
    @Override
    public void sendHtmlMail(String subject, String content, String[] to, String[] cc,String[] bcc) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSentDate(new Date());
            if(!Objects.isNull(cc)){
                mimeMessageHelper.setCc(cc);
            }
            if(!Objects.isNull(bcc)){
                mimeMessageHelper.setBcc(bcc);
            }
            javaMailSender.send(mimeMessage);
            log.info("发送给" + to + "的html邮件已经发送。 subject：" + subject);
        } catch (MessagingException e) {
            log.error("发送给" + to + "的html邮件失败:{}", ExceptionUtils.getStackTrace(e));
            throw new MailException(MailErrorCode.MAIL_SEND_FAIL);
        }
    }

    /**
     * 发送静态资源（一般是图片）的邮件
     *
     * @param subject 主标题 必传
     * @param content     邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:image\" >
     * @param to      发送给谁 邮箱 必传
     * @param cc      抄送给谁 邮箱 没有可以传 null
     * @param bcc     秘送给谁 邮箱 没有可以传 null
     * @param resourceist 静态资源list
     */
    @Async("mailTaskExecutor")
    @Override
    public void sendInlineResourceMail(String subject, String content,List<InlineResource> resourceist, String[] to, String[] cc,String[] bcc) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSentDate(new Date());
            for (InlineResource inlineResource : resourceist) {
                FileSystemResource res = new FileSystemResource(new File(inlineResource.getPath()));
                mimeMessageHelper.addInline(inlineResource.getCid(), res);
            }
            if(!Objects.isNull(cc)){
                mimeMessageHelper.setCc(cc);
            }
            if(!Objects.isNull(bcc)){
                mimeMessageHelper.setBcc(bcc);
            }
            javaMailSender.send(message);
            log.info("发送给" + to + "含有静态资源的邮件成功。 subject：" + subject);
        } catch (MessagingException e) {
            log.error("发送给" + to + "含有静态资源的邮件异常 :{}", ExceptionUtils.getStackTrace(e));
            throw new MailException(MailErrorCode.MAIL_SEND_FAIL);
        }
    }

    /**
     * 发送带附件的邮件
     * @param subject 主标题 必传
     * @param content 内容  必传
     * @param to      发送给谁 邮箱 必传
     * @param cc      抄送给谁 邮箱 没有可以传 null
     * @param bcc     秘送给谁 邮箱 没有可以传 null
     * @param filePath 附件的url
     */
    @Async("mailTaskExecutor")
    @Override
    public void sendAttachmentsMail(String subject, String content, String filePath, String[] to, String[] cc, String[] bcc) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSentDate(new Date());
            if(!Objects.isNull(cc)){
                mimeMessageHelper.setCc(cc);
            }
            if(!Objects.isNull(bcc)){
                mimeMessageHelper.setBcc(bcc);
            }
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf("/"));
            mimeMessageHelper.addAttachment(fileName, file);
            javaMailSender.send(message);
            log.info("发送给" + to + "带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送给" + to + "带附件的邮件时发生异常！: {}", ExceptionUtils.getStackTrace(e));
            throw new MailException(MailErrorCode.MAIL_SEND_FAIL);
        }
    }

    /**
     * 发送模版邮件
     * @param subject 主标题 必传
     * @param to      发送给谁 邮箱 必传
     * @param cc      抄送给谁 邮箱 没有可以传 null
     * @param bcc     秘送给谁 邮箱 没有可以传 null
     */
    @Async("mailTaskExecutor")
    @Override
    public void sendThymeleafMail(String subject, String[] to, String[] cc, String[] bcc) {

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSentDate(new Date());
            if(!Objects.isNull(cc)){
                mimeMessageHelper.setCc(cc);
            }
            if(!Objects.isNull(bcc)){
                mimeMessageHelper.setBcc(bcc);
            }
            /**
             * 填充内容
             */
            Context context = new Context();
            context.setVariable("username", "javaboy");
            context.setVariable("num","000001");
            context.setVariable("salary", "99999");

            String process = templateEngine.process("mail.html", context);
            mimeMessageHelper.setText(process,true);
            javaMailSender.send(message);
            log.info("发送给" + to + "模版邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送给" + to + "模版邮件时发生异常！: {}", ExceptionUtils.getStackTrace(e));
            throw new MailException(MailErrorCode.MAIL_SEND_FAIL);
        }
    }

}
