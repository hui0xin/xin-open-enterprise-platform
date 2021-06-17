package com.xin.commons.mail.service;

import com.xin.commons.mail.bean.InlineResource;
import java.util.List;

public interface SendMailService {

    void sendSimpleMail(String subject, String content, String[] to, String[] cc,String[] bcc);

    void sendHtmlMail(String subject, String content, String[] to, String[] cc,String[] bcc);

    void sendInlineResourceMail(String subject, String content,List<InlineResource> resourceist, String[] to, String[] cc,String[] bcc);

    void sendAttachmentsMail(String subject, String content, String filePath, String[] to, String[] cc, String[] bcc);

    void sendThymeleafMail(String subject, String[] to, String[] cc, String[] bcc);
}
